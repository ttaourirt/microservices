package com.example.webservice1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebRestService {
    @GetMapping(value="/")
    public String hello() {
        return "hello to you friend !";
    }

}