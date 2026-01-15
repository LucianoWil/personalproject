package com.ecomerceproject.personalproject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/register")
public class RegisterViewController {
    @GetMapping
    public String register() {
        return "register";
    }
}
