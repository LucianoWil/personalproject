package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.DTOs.CategoryDTO;
import com.ecomerceproject.personalproject.Service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/categories/load")
public class loadCategoryViewController {

    private final CategoryService categoryService;

    public loadCategoryViewController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String loadCategory(){
        return "load-category";
    }
    @PostMapping
    public String loadCategoryProcess(@ModelAttribute CategoryDTO categoryDTO, Model model){

        try {
            categoryService.createCategory(categoryDTO);
            return "redirect:/view/categories/list";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "load-category";
        }
    }
}
