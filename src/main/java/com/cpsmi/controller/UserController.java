package com.cpsmi.controller;

import com.cpsmi.dto.RegisterResult;
import com.cpsmi.dto.UserDTO;
import com.cpsmi.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;


@Controller
public class UserController {

    private static final Logger LOG = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO getProfile(Principal principal) {
        return userService.getProfile(principal.getName());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public RegisterResult create(@RequestBody UserDTO user) {
        return userService.create(user);
    }


}