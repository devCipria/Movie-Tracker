package com.example.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController
 */
/*@RequestMapping("")*/
@Controller
public class HomeController {

    /**
     * @return string
     */
    @GetMapping("")
    public String homeLogin(){
        return "redirect:index.html";
    }


    /**
     * @return string
     */
    @GetMapping("/login")
    public String home3(){
        return "This is user Home Page";
    }


    /**
     * @return string
     */
    @GetMapping("/admin")
    public String admin(){
        return "This is Admin Page";
    }

    /**
     * @return string
     */
    @GetMapping("/home")
    public String home2(){
        return "This is logout works";
    }
}
