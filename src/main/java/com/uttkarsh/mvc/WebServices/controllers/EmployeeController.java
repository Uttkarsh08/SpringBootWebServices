package com.uttkarsh.mvc.WebServices.controllers;


import com.uttkarsh.mvc.WebServices.dto.EmployeeDTO;
import com.uttkarsh.mvc.WebServices.entities.EmployeeEntity;
import com.uttkarsh.mvc.WebServices.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")  // This is Path of the parent, every mapping under it , will have it in URL
public class EmployeeController {

    //Controller should not access repository directly, in between there should be service layer
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

//similarly, employeeEntity should not be used directly inside the controllers, in between there should be a service layer
    @GetMapping("/{employeeID}") //GetMapping for get Request
    public EmployeeEntity getEmployeeById(@PathVariable(name = "employeeID") Long id) { //PathVariable so, that the parameter is treated as a path for GetMapping.
        //also, PathVariable is used when the path in URL is fixed(Mandatory to pass). Otherwise we use @RequestParam
        //localhost:8080/employees/1
        return  employeeRepository.findById(id).orElse(null);
    }


    @GetMapping  //we removed path from here, because we are using request Mapping in parent function
    public List<EmployeeEntity> getAllEmployees(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) String sortBy
                                  ){
        //RequestParam is used when path have optional parameters, which can be passed
        //localhost:8080/employees?age=22&sortBy=name
        return employeeRepository.findAll();
    }

    @PostMapping
    public EmployeeEntity createEmployee(@RequestBody EmployeeEntity inputEmployee) {  //RequestBody maps HTTPS POST requests to parameters
//        inputEmployee.setId(100L);  //this was used when we access DTO instead of Entity(Entity should not be used , which we are using here, for learning purpose only)
        return employeeRepository.save(inputEmployee);
    }


}
