package com.cpsmi.service;

import com.cpsmi.dao.UserDAO;
import com.cpsmi.dto.RegisterResult;
import com.cpsmi.dto.UserDTO;
import com.cpsmi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Misha on 13.11.2016.
 */
@Component
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public UserDTO getProfile(String email) {

        User user = userDAO.getByEmail(email);
        return convert(user);
    }

    private UserDTO convert(User source) {
        UserDTO target = new UserDTO();
        target.setEmail(source.getEmail());
        target.setName(source.getName());
        target.setLanguage(source.getLanguage());
        target.setBirthDate(source.getBirthDate());
        return target;
    }

    private User convert(UserDTO source) {
        User target = new User();
        target.setEmail(source.getEmail());
        target.setName(source.getName());
        target.setPassword(source.getPassword());
        target.setLanguage(source.getLanguage());
        target.setBirthDate(source.getBirthDate());
        return target;
    }

    public RegisterResult create(UserDTO dto) {
        if (userDAO.getByEmail(dto.getEmail()) != null) {
            return new RegisterResult(false, "Already in use");
        }
        User user = convert(dto);
        userDAO.create(user);
        return new RegisterResult(true, "Done");
    }
}
