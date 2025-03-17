package com.uttkarsh.mvc.WebServices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

//DTO(data Transfer Object) is a design pattern to transfer data b/w controller(presentation layer) and service layer
//it enables encapsulation and security
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDate joiningDate;

    @JsonProperty("isActive")
    private boolean isActive;
}