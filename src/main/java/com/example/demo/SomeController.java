package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

    private final SomeService someService;

    public SomeController(SomeService someService) {
        this.someService = someService;
    }

    @GetMapping("/")
    public ResponseEntity someEndpoint() {
        someService.fails();
        return ResponseEntity.ok("nothing to see");
    }
}
