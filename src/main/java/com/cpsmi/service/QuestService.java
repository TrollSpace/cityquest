package com.cpsmi.service;

import com.cpsmi.dao.QuestDAO;
import com.cpsmi.dao.UserDAO;
import com.cpsmi.dto.QuestDTO;
import com.cpsmi.dto.QuestionDTO;
import com.cpsmi.model.Point;
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

        Point nextQuestion = questDAO.getNextQuestion(email, questId); //Преобразуем лист в вопрос
        if (nextQuestion == null) { //если пусто дергаем Первый вопрос.
            nextQuestion = questDAO.getFirstQuestion(questId);
        }
        if (nextQuestion != null) {  //в КвестДАО добавляем прогресс(юзера, поинт, таймстэмп) надо еще добавить в этот метод добавлять координаты окончания предидущего вопроса.
            questDAO.addProgress(userDAO.getByEmail(email), nextQuestion, new Date());
            return convert(nextQuestion);
        }
        return null;
    }

    private QuestionDTO convert(Point source) { // конвертируем поинт в ДТО
        QuestionDTO target = new QuestionDTO();
        target.setQuestionText(source.getQuestion());
        target.setLatitude(source.getLatitude());
        target.setLongitude(source.getLongitude());
        return target;
    }
}
