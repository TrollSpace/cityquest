package com.cpsmi;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DataController {

    private static final Logger LOG = Logger.getLogger(DataController.class);


    @RequestMapping(value = "/persist", method = RequestMethod.GET)
    public
    @ResponseBody
    String persist() {

        return "Hello world";

    }


}