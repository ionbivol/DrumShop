package org.online.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/web/login-form")
    public ModelAndView showFormLogin(){
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }
}
