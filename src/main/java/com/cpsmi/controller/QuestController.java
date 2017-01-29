package com.cpsmi.controller;

import com.cpsmi.dto.*;
import com.cpsmi.service.QuestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public QuestionDTO getNextQuestion(@RequestParam(value = "questId") int questId,
                                       Principal principal) {
        return questService.getNextQuestion(principal.getName(), questId);
    }

    @RequestMapping(value = "/hasNextQuestion", method = RequestMethod.GET)
    @ResponseBody
    public boolean hasNextQuestion(@RequestParam(value = "questId") int questId,
                                   Principal principal) {
        return questService.hasNextQuestion(principal.getName(), questId);
    }

    @RequestMapping(value = "/getStatistic", method = RequestMethod.GET)
    @ResponseBody
    public StatisticDTO getStatistic(@RequestParam(value = "questId") int questId,
                                     Principal principal) {
        return questService.getStatistic(principal.getName(), questId);
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    @ResponseBody
    public boolean answer(@RequestBody AnswerDTO answer, Principal principal) {
        return questService.answer(answer, principal.getName());
    }

    @RequestMapping(value = "/hint", method = RequestMethod.GET)
    @ResponseBody
    public HintDTO getNewHint(@RequestParam(value = "questId") int questId, Principal principal) {
        return questService.getNewHint(principal.getName(), questId);
    }

}
