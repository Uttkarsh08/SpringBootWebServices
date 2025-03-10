package com.uttkarsh.mvc.WebServices.controllers;


import com.uttkarsh.mvc.WebServices.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")  // This is Path of the parent, every mapping under it , will have it in URL
public class EmployeeController {


    @GetMapping("/{employeeID}") //GetMapping for get Request
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeID) { //PathVariable so, that the parameter is treated as a path for GetMapping.
        //also, PathVariable is used when the path in URL is fixed(Mandatory to pass). Otherwise we use @RequestParam
        //localhost:8080/employees/1
        return new EmployeeDTO(
                employeeID,
                "Uttkarsh",
                "ut@gmail.com",
                20,
                LocalDate.of(2025, 3, 11),
                true
        );
    }


    @GetMapping  //we removed path from her
    public String getAllEmployees(@RequestParam(required = false) Integer age,
                                  @RequestParam(required = false) String sortBy
                                  ){
        //RequestParam is used when path have optional parameters, which can be passed
        //localhost:8080/employees?age=22&sortBy=name
        return "employee of age "+age+" and sort by " +  sortBy;
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO inputEmployee) {  //RequestBody maps HTTPS POST requests to parameters
        inputEmployee.setId(100L);
        return inputEmployee;
    }


}
