package com.rns2706.athenaex.controller;

import com.rns2706.athenaex.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class   HomeController {

    @GetMapping("")
    public ResponseEntity<ApiResponse> homeController(){


        ApiResponse res=new ApiResponse("Welcome to AthenaEx - Your one stop solution for crypto trading", true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
    }

}
