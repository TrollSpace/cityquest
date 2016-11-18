package com.cpsmi.controller;

import com.cpsmi.dto.QuestDTO;
import com.cpsmi.dto.QuestionDTO;
import com.cpsmi.service.QuestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping(value = "/quest")
public class QuestController {

    private static final Logger LOG = Logger.getLogger(QuestController.class);

    @Autowired
    private QuestService questService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestDTO> getList() {
        return questService.list();
    }

    //    От нажатия пользователем на выбранные квест
    @RequestMapping(value = "/nextQuestion", method = RequestMethod.GET)
    @ResponseBody
    public QuestionDTO getNextQuestion(@RequestParam(value = "email") String email,
                                       @RequestParam(value = "questId") int questId) {

        return questService.getNextQuestion(email, questId);
    }
}