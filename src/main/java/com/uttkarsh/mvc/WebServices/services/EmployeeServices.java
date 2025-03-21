package com.uttkarsh.mvc.WebServices.services;

import com.uttkarsh.mvc.WebServices.dto.EmployeeDTO;
import com.uttkarsh.mvc.WebServices.entities.EmployeeEntity;
import com.uttkarsh.mvc.WebServices.exceptions.ResourceNotFoundException;
import com.uttkarsh.mvc.WebServices.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServices {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    public EmployeeServices(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }


    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        isExistsByEmployeeId(id);

        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(
                entity->mapper.map(entity, EmployeeDTO.class));

        //OR

//      return employeeRepository.findById(id)
//        .map(employeeEntity -> mapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(entity->
                        mapper.map(entity, EmployeeDTO.class)
                )
                .collect(Collectors.toList());

    }

    public EmployeeDTO createEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity = mapper.map(inputEmployee, EmployeeEntity.class); //firstly map DTO to Entity, to save it using repository
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(toSaveEntity);
        return  mapper.map(savedEmployeeEntity, EmployeeDTO.class);  //the map it back(entity to DTO) to return it
    }

    public EmployeeDTO updateEmployeeById(Long id, EmployeeDTO employeeDTO) {
        isExistsByEmployeeId(id);

        EmployeeEntity employeeEntity = mapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(id);

        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return mapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long id) {
        isExistsByEmployeeId(id);

        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long id, Map<String, Object> updates) {
        isExistsByEmployeeId(id);

        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();  //get the entity(by id) of the employee to update
        updates.forEach((field, value) -> {
            Field filedToUpdate = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);  //use reflection to update the required fields
            filedToUpdate.setAccessible(true);
            ReflectionUtils.setField(filedToUpdate, employeeEntity, value);
        });
        EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);  //save the updated entity to the database(its like put mapping)
        return mapper.map(savedEntity, EmployeeDTO.class);  //return the DTO using mapper
    }

    public void isExistsByEmployeeId(Long employeeId) {
        boolean exist = employeeRepository.existsById(employeeId);
        if(!exist) throw new ResourceNotFoundException("Employee doesn't Exist with id: "+employeeId);
    }
}
