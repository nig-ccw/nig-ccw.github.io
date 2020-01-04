package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author czh
 * @date 2019-11-27
 */
@RestController
public class HelloController {
    @GetMapping("/")
    public String hi(String id,String bb,Integer o){
        return "hi";
    }
}
