package com.cpsmi.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DataController {

    private static final Logger LOG = Logger.getLogger(DataController.class);

    //    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public String profile() {

        return "Hello world";

    }


}