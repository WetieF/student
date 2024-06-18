package com.wetie.student.services.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private UUID studentId;

    @Size(max = 50, message = "firstname must contain maximum 50 characters")
    @NotBlank(message = "firstname is mandatory")
    private String firstName;

    @Size(max = 50, message = "lastname must contain maximum 50 characters")
    @NotBlank(message = "lastname is mandatory")
    private String lastName;

    @Size(max = 15, message = "Phone-number must contain maximum 15 characters")
    @NotBlank(message = "Phone-number is mandatory")
    //@Pattern(regexp = "^\\d{10}$",message = "invalid mobile number entered ")
    private String phoneNumber;
}
