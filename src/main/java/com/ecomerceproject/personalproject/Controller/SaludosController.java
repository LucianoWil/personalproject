package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.Model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/apisaludos")
@Controller
public class SaludosController {

    @GetMapping("/hello")
    public String helloWorld(Model model) {
        Product product = new Product(1L,"Mayonesa", 100.0, "Hellmann's", 1L, 200);
        model.addAttribute("producto", product);
        return "hello_world";
    }}
