package com.uttkarsh.mvc.WebServices.controllers;


import com.uttkarsh.mvc.WebServices.dto.EmployeeDTO;
import com.uttkarsh.mvc.WebServices.exceptions.ResourceNotFoundException;
import com.uttkarsh.mvc.WebServices.services.EmployeeServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")  // This is Path of the parent, every mapping under it , will have it in URL
public class EmployeeController {

    private final EmployeeServices employeeService;

    public EmployeeController(EmployeeServices employeeService) {
        this.employeeService = employeeService;
    }


//employeeEntity should not be used directly inside the controllers, in between there should be a service layer
    @GetMapping("/{employeeID}") //GetMapping for get Request
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeID") Long id) { //PathVariable so, that the parameter is treated as a path for GetMapping.
        //also, PathVariable is used when the path in URL is fixed(Mandatory to pass). Otherwise we use @RequestParam
        //localhost:8080/employees/1
        Optional<EmployeeDTO> employeeDTO =  employeeService.getEmployeeById(id);
        return employeeDTO.map(
                employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElseThrow(() -> new ResourceNotFoundException("Employee Doesn't Exist with id: "+id));
    }



    @GetMapping  //we removed path from here, because we are using request Mapping in parent function
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) String sortBy
                                  ){
        //RequestParam is used when path have optional parameters, which can be passed
        //localhost:8080/employees?age=22&sortBy=name
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid  EmployeeDTO inputEmployee) {  //RequestBody maps HTTPS POST requests to parameters, ?Valid to tell controller to validate the parameter
        EmployeeDTO createdEmployee = employeeService.createEmployee(inputEmployee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable(name = "employeeID") Long id){
        return ResponseEntity.ok(employeeService.updateEmployeeById(id, employeeDTO));
    }

    @DeleteMapping(path = "/{employeeID}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable(name = "employeeID") Long id){
        boolean isDeleted = employeeService.deleteEmployeeById(id);
        if(isDeleted) return ResponseEntity.ok(true);
        return  ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeID}")
    public EmployeeDTO udpatePartialEmployeeById(@RequestBody Map<String, Object> updates,
                                                 @PathVariable(name = "employeeID") Long id){
        return employeeService.updatePartialEmployeeById(id, updates);
    }

}
