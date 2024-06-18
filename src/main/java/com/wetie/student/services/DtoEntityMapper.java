package com.wetie.student.services;

import com.wetie.student.access.dataaccess.entities.Student;
import com.wetie.student.services.dto.StudentDto;

public interface DtoEntityMapper {
    Student mapToStudent(StudentDto studentDto);
    StudentDto mapToStudentDto(Student student);
}
