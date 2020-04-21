package org.elsysbg.fileshare.controllers;
import javax.servlet.http.HttpServletRequest;

import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class IndexController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String home(Model model, Pageable pageable) {
        model.addAttribute("page", userRepository.findAll(pageable));
        return "layout";
    }

    @GetMapping("/pagination")
    @ResponseBody
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @GetMapping("login")
    public String login(Model model, HttpServletRequest request) {
        return "login";
    }


}