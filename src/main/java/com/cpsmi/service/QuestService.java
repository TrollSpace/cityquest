package com.cpsmi.service;

import com.cpsmi.dao.HintDAO;
import com.cpsmi.dao.QuestDAO;
import com.cpsmi.dao.UserDAO;
import com.cpsmi.dto.AnswerDTO;
import com.cpsmi.dto.HintDTO;
import com.cpsmi.dto.QuestDTO;
import com.cpsmi.dto.QuestionDTO;
import com.cpsmi.model.Hint;
import com.cpsmi.model.PointInQuest;
import com.cpsmi.model.Progress;
import com.cpsmi.model.Quest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Misha on 13.11.2016.
 */
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
        PointInQuest pointInQuest = questDAO.getLastUnAnsweredQuestion(email, answer.getQuestId());
        if (pointInQuest.getPoint().getAnswer().contains(answer.getAnswer())) {
            //record progress
            questDAO.progress(pointInQuest, answer.getLongitude(), answer.getLatitude(), email, new Date());
            return true;
        }
        return false;
    }

    public QuestionDTO getNextQuestion(String email, int questId) { // КвевстДАО преобразуем в Вопрос(либо первыйдля первой регистрации, либо последний неотвеченный).

        PointInQuest lastUnAnsweredQuestion = questDAO.getLastUnAnsweredQuestion(email, questId);
        if (lastUnAnsweredQuestion != null) {
            return convert(lastUnAnsweredQuestion);

        }

        PointInQuest lastAnsweredQuestion = questDAO.getLastAnsweredQuestion(email, questId);
        if (lastAnsweredQuestion != null) {
            PointInQuest nextQuestion = questDAO.getNextQuestion(lastAnsweredQuestion);
            addProgress(nextQuestion, email);
            return convert(nextQuestion);
        }

        PointInQuest firstQuestion = questDAO.getFirstQuestion(questId);
        addProgress(firstQuestion, email);
        return convert(firstQuestion);
    }

    private QuestionDTO convert(PointInQuest source) { // конвертируем поинт в ДТО
        QuestionDTO target = new QuestionDTO();
        //todo add same check in all converters
        if (source == null) {
            return target;
        }
        target.setQuestionText(source.getPoint().getQuestion());
        target.setLatitude(source.getPoint().getLatitude());
        target.setLongitude(source.getPoint().getLongitude());
        return target;
    }

    public HintDTO getNewHint(String email, int questId) {
        PointInQuest pointInQuest = questDAO.getLastUnAnsweredQuestion(email, questId);
        List<Hint> hints = pointInQuest.getPoint().getHints();  //todo make sort by order
        Progress progress = hintDAO.getNewHint(email, pointInQuest);
        HintDTO target = new HintDTO();
        for (Hint hint : hints) {
            progress.setLastUsedHintId(hint.getId());
            addHintToProgress(progress);
            target.setOrder(hint.getHintOrder());
            target.setText(hint.getHintText());
            target.setPointId(hint.getPoint().getId());
        }

        return target;
    }
}

