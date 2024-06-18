package com.wetie.student.services;

import com.wetie.student.services.dto.StudentDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    StudentDto saveStudent(@Valid StudentDto studentDto);
    StudentDto updateStudent(@Valid StudentDto studentDto);
    StudentDto findStudentById(UUID studentId);
    List<StudentDto> findAllStudents();
}
