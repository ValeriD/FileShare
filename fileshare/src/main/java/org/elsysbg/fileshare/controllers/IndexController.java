package org.elsysbg.fileshare.controllers;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.elsysbg.fileshare.dto.UserCreateDto;
import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class IndexController {


    @Autowired
    private UserRepository userRepository;



}