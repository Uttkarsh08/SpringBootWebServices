package com.uttkarsh.mvc.WebServices.advices;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Data
@Builder
public class ApiError {

    private HttpStatus status;
    private String message;
    List<String> subErrors;
}
