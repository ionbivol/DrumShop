package org.online.shop.controller;

import org.online.shop.entity.BrandEntity;
import org.online.shop.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(path = "/web")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/brand/list")
    public ModelAndView brandList(){
        ModelAndView modelAndView = new ModelAndView("/brand/brands");
        modelAndView.addObject("brandList",brandRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/brand/add")
    public ModelAndView addNewBrand(){
        ModelAndView modelAndView = new ModelAndView("/brand/brands-form");
        modelAndView.addObject("brandObject",new BrandEntity());
        return modelAndView;
    }

   @PostMapping("/brand/save")
    public ModelAndView saveProduct(@ModelAttribute("brandObject")BrandEntity brand){
    ModelAndView modelAndView = new ModelAndView("redirect:/web/brand/list");
    brandRepository.save(brand);
    return modelAndView;
    }

    @GetMapping("/brand/edit/{brandId}")
    public ModelAndView editBrand(@PathVariable Integer brandId){
        ModelAndView modelAndView = new ModelAndView("/brand/brands-form");
       modelAndView.addObject("brandObject",brandRepository.findById(brandId).get());
       return modelAndView;
    }

    @GetMapping("/brand/delete/{brandId}")
    public ModelAndView deleteBrandById(@PathVariable Integer brandId){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/brand/list");
        brandRepository.deleteById(brandId);
        return modelAndView;
    }
}
