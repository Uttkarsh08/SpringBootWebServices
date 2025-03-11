package com.uttkarsh.mvc.WebServices.repositories;

import com.uttkarsh.mvc.WebServices.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//                                                        Which Entity     ID
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
