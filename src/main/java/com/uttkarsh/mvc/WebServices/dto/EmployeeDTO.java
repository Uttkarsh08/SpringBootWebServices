package com.uttkarsh.mvc.WebServices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uttkarsh.mvc.WebServices.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
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

//    @NotNull(message = " Name Can't ne Null")  //it accepts empty filed
//    @NotEmpty(message = "Name Can't be Empty") //it allow string which is nothing but spaces(length > 0, not empty), which we dont want
    @NotBlank(message = "Name Can't be Empty")
    @Size(max = 20, message = "Name Length too Long")
    private String name;

    @Email(message = "Email Should be Valid")
    private String email;

    @Max(value = 80, message = "Age Can't be Greater then 80")
    @Min(value = 18, message = "Minimum age is 18")
    private Integer age;

//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Employee Can be user or admin only")
    @EmployeeRoleValidation   //Custom Annotation for Role Validation
    @NotBlank(message = "Role Can't be Blank")
    private String role; //ADMIN or USER

    @NotNull(message = "Salary Can't be Null")
    @Positive(message = "Salary Can't be Negative")
    private Integer salary;

    @PastOrPresent(message = "Date Can't be in Future") //ensures date is in past only
    private LocalDate joiningDate;

    @JsonProperty("isActive")
    @AssertTrue(message = "isActive has to be true") // ensures the filed is always true only
    private boolean isActive;
}