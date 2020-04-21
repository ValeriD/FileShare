package org.elsysbg.fileshare.controllers;


import org.elsysbg.fileshare.dto.UserCreateDto;
import org.elsysbg.fileshare.dto.VerifyCodeDto;
import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("sign-up")
    public String signUp(UserCreateDto userCreateDto, Model model) {
        return "sign-up";
    }

    @PostMapping("sign-up")
    public String signUp(@Valid UserCreateDto accountCreateDto, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return "sign-up";
        }

        User user = userService.createMember(accountCreateDto);
        accountCreateDto.setId(user.getId());

        return "redirect:/verify-code";
    }

    @GetMapping("verify-code")
    public String verifyCode(Model model, VerifyCodeDto verifyCodeDto) {
        return "verify-code";
    }

    @PostMapping("verify-code")
    public String verifyCodeAction(Model model, @Valid VerifyCodeDto verifyCodeDto, BindingResult result) {
        if(result.hasErrors()) {
            return "verify-code";
        }
        userService.verifyCode(verifyCodeDto);
        return "redirect:/login";
    }
}
