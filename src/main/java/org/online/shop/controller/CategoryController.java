package org.online.shop.controller;

import org.online.shop.entity.CategoryEntity;
import org.online.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(path = "/web/")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/category/list")
    public ModelAndView categoryList(){
        ModelAndView modelAndView = new ModelAndView("/category/categories");
        modelAndView.addObject("categoryList",categoryRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/category/add")
    public ModelAndView addCategory(){
        ModelAndView modelAndView = new ModelAndView("/category/categories-form");
//        Set<CategoryEntity> categories = getAllCategory();
//        modelAndView.addObject("categories",categories);
        modelAndView.addObject("categoryObject",new CategoryEntity());
        return modelAndView;
    }

    @PostMapping("/category/save")
    public ModelAndView saveCategory(@ModelAttribute("categoryObject")CategoryEntity category){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/category/list");
        categoryRepository.save(category);
        return modelAndView;
    }

    @GetMapping("/category/edit/{categoryId}")
    public ModelAndView editCategory(@PathVariable Integer categoryId){
        ModelAndView modelAndView = new ModelAndView(
                "/category/categories-form");
        modelAndView.addObject("categoryObject",categoryRepository.findById(categoryId).get());
        return modelAndView;
    }
    @GetMapping("/category/delete/{categoryId}")
    public ModelAndView deleteCategory(@PathVariable Integer categoryId){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/category/list");
        categoryRepository.deleteById(categoryId);
        return modelAndView;

    }



//
//    private Set<CategoryEntity> getAllCategory() {
//        Set<CategoryEntity> categories = new HashSet<>();
//        categories = (Set<CategoryEntity>) categoryRepository.findAll();
//       return categories;
//    }
}


