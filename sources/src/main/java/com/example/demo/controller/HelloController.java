package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author czh
 * @date 2019-11-27
 */
@RestController
public class HelloController {
    @GetMapping("/hi")
    public String hi(){
        return "hi";
    }
}
