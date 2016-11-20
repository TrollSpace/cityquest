package com.cpsmi.service;

import com.cpsmi.dao.QuestDAO;
import com.cpsmi.dao.UserDAO;
import com.cpsmi.dto.AnswerDTO;
import com.cpsmi.dto.QuestDTO;
import com.cpsmi.dto.QuestionDTO;
import com.cpsmi.model.PointInQuest;
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

    public QuestionDTO getNextQuestion(String email, int questId) { // КвевстДАО преобразуем в Вопрос(либо первыйдля первой регистрации, либо последний неотвеченный).

        PointInQuest nextQuestion = questDAO.getNextQuestion(email, questId); //Преобразуем лист в вопрос
        if (nextQuestion == null) { //если пусто дергаем Первый вопрос.
            nextQuestion = questDAO.getFirstQuestion(questId);
        }
        if (nextQuestion != null) {  //в КвестДАО добавляем прогресс(юзера, поинт, таймстэмп) надо еще добавить в этот метод добавлять координаты окончания предыдущего вопроса.
            if (!questDAO.userHasProgress(email, nextQuestion)) { //only if there was no progress for this question
                questDAO.addProgress(userDAO.getByEmail(email), nextQuestion, new Date());
            }
        }
        return convert(nextQuestion);
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

    public boolean answer(AnswerDTO answer, String email) {
        //verify answer
        PointInQuest pointInQuest = questDAO.getNextQuestion(email, answer.getQuestId());
        if (pointInQuest.getPoint().getAnswer().contains(answer.getAnswer())) {
            //record progress
            questDAO.progress(pointInQuest, answer.getLongitude(), answer.getLatitude(), email, new Date());
            return true;
        }
        return false;
    }
}
