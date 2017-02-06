package com.cpsmi.service;

import com.cpsmi.dao.HintDAO;
import com.cpsmi.dao.QuestDAO;
import com.cpsmi.dao.UserDAO;
import com.cpsmi.dto.*;
import com.cpsmi.model.Hint;
import com.cpsmi.model.PointInQuest;
import com.cpsmi.model.Progress;
import com.cpsmi.model.Quest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class QuestService {

    @Autowired
    private QuestDAO questDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HintDAO hintDAO;

    public List<QuestDTO> list() {
        return convert(questDAO.list());

    }

    private List<QuestDTO> convert(List<Quest> source) { //конвертируем лист квестов в лист квестов ДТО
        ArrayList<QuestDTO> target = new ArrayList<>();
        for (Quest quest : source) {
            target.add(convert(quest));
        }
        return target;
    }

    private QuestDTO convert(Quest source) {  //Квест конвертим в Квест ДТО
        QuestDTO target = new QuestDTO();
        target.setName(source.getName());
        target.setId(source.getId());
        target.setDescription(source.getDescription());
        target.setNumberOfQuestions(questDAO.getNumberOfQuestions(source.getId()));
        return target;
    }

    protected void addProgress(PointInQuest newQuestion, String email) {
        if (newQuestion != null) {  //в КвестДАО добавляем прогресс(юзера, поинт, таймстэмп) надо еще добавить в этот метод добавлять координаты окончания предыдущего вопроса.
            if (!questDAO.userHasProgress(email, newQuestion)) { //only if there was no progress for this question
                questDAO.addProgress(userDAO.getByEmail(email), newQuestion, new Date());
            }
        }
    }

    protected void addHintToProgress(Progress progress) {
        hintDAO.addHintToProgress(progress);
    }

    public boolean answer(AnswerDTO answer, String email) {
        //verify answer
        PointInQuest pointInQuest = questDAO.getCurrentQuestion(email, answer.getQuestId());
        if (pointInQuest.getPoint().getAnswer().contains(answer.getAnswer())) {
            //record progress
            questDAO.progress(pointInQuest, answer.getLongitude(), answer.getLatitude(), email, new Date());
            return true;
        }
        return false;
    }

    public boolean hasNextQuestion(String email, int questId) {
        PointInQuest previousQuestion = questDAO.getPreviousQuestion(email, questId);
        if (previousQuestion == null) {
            return false;
        }
        PointInQuest nextQuestion = questDAO.getNextQuestion(previousQuestion);

        return nextQuestion != null;
    }

    /**
     * If we have unanswered question, we return this answer.
     * If we don't have answered question(quest start) - we return first question.
     * If quest started and we have answered question get next question.
     * If quest is over we send null. If you want to check is quest is over, use {@link #hasNextQuestion(String, int)}
     *
     * @param email
     * @param questId
     * @return
     */
    public QuestionDTO getNextQuestion(String email, int questId) { // КвевстДАО преобразуем в Вопрос(либо первыйдля первой регистрации, либо последний неотвеченный).

        PointInQuest currentQuestion = questDAO.getCurrentQuestion(email, questId);
        if (currentQuestion != null) {
            return convert(currentQuestion);

        }

        PointInQuest previousQuestion = questDAO.getPreviousQuestion(email, questId);
        if (previousQuestion == null) {
            PointInQuest firstQuestion = questDAO.getFirstQuestionInQuest(questId);
            addProgress(firstQuestion, email);
            return convert(firstQuestion);
        } else {
            PointInQuest nextQuestion = questDAO.getNextQuestion(previousQuestion);
            if (nextQuestion == null) {
                return null;
            }
            addProgress(nextQuestion, email);
            return convert(nextQuestion);
        }

    }

    private QuestionDTO convert(PointInQuest source) { // конвертируем поинт в ДТО
        QuestionDTO target = new QuestionDTO();
        if (source == null) {
            return target;
        }
        target.setQuestionText(source.getPoint().getQuestion());
        target.setLatitude(source.getPoint().getLatitude());
        target.setLongitude(source.getPoint().getLongitude());
        return target;
    }

    public HintDTO getNewHint(String email, int questId) {

        PointInQuest pointInQuest = questDAO.getCurrentQuestion(email, questId);
        List<Hint> hints = pointInQuest.getPoint().getHints();
        Progress progress = hintDAO.getNewHint(email, pointInQuest);
        int usedHintId = progress.getLastUsedHintId();

        int nextHint = 1;
        for (Hint hint : hints) {
            if (hint.getId() == usedHintId) {
                nextHint = hint.getHintOrder() + 1;
                break;
            }
        }
        if (nextHint <= hints.size()) {
            Hint hint = hints.get(nextHint - 1);
            HintDTO target = new HintDTO(pointInQuest.getId(), hint.getHintText(), hint.getHintOrder());
            progress.setLastUsedHintId(hint.getId());
            addHintToProgress(progress);
            return target;

        } else {
            throw new RuntimeException("No hint available.");
        }

    }

    public StatisticsDTO getStatistics(String email, int questId) {
        StatisticsDTO stat = new StatisticsDTO();
        List<Progress> progress = questDAO.getProgressList(email, questId);
        double startLat = 10.000;
        double startLong = 11.333;

        for (Progress elementOfProgress : progress) {

            try {

                stat.setTime(stat.getTime() + (elementOfProgress.getEnd().getTime() - elementOfProgress.getStart().getTime()));
                stat.setAnsweredQuestions(questDAO.getPreviousQuestion(email, questId).getPointOrder());
                stat.setDistance(stat.getDistance() + getDistanceFromCoordinate(startLat, startLong,
                        elementOfProgress.getEndLatitude(), elementOfProgress.getEndLongitude()));
                startLat = elementOfProgress.getEndLatitude();
                startLong = elementOfProgress.getEndLongitude();
            } catch (NullPointerException exp)
            {
                System.out.println(exp);

            }
        }

        return stat;

    }

    private double getDistanceFromCoordinate(double startLat, double startLong, double endLat, double endLong) {

        double earthRadius = 6371.0;
        double dLat = Math.toRadians(endLat - startLat);
        double dLng = Math.toRadians(endLong - startLong);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist;
    }
}
