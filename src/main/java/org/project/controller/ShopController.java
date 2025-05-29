package org.project.controller;

import org.project.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShopController {

    @Autowired
    private PharmacyService pharmacyServiceImpl;

    @GetMapping("/shop")
    public ModelAndView shop() {
        ModelAndView mv = new ModelAndView("shop/index");
        mv.addObject("products", pharmacyServiceImpl.getAllPharmacies());
        return mv;
    }
}
