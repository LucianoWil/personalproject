package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.DTOs.ProductDTO;
import com.ecomerceproject.personalproject.Repository.UserRepository;
import com.ecomerceproject.personalproject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view/index")
public class IndexViewController {
    @Autowired
    private final ProductService productService;

    @Autowired
    private UserRepository userRepository;


    public IndexViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getIndex(Model model){
        List<ProductDTO> products;
        products = productService.getFeaturedProducts();
        model.addAttribute("products", products);
        return "index";
    }
}
