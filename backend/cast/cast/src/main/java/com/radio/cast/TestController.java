package com.radio.cast;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@CrossOrigin("http://localhost:5173")
public class TestController {
    
    @GetMapping
    public String test(){
        return "Spring boot, React API 통신 테스트";
    }
}
