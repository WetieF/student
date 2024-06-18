package com.wetie.student.services.serviceImpl;

import com.wetie.student.access.dataaccess.entities.Student;
import com.wetie.student.services.DtoEntityMapper;
import com.wetie.student.services.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class DtoEntityMapperImpl implements DtoEntityMapper {

    @Override
    public Student mapToStudent(StudentDto studentDto) {
        Student student = Student.builder()
                .id(String.valueOf(studentDto.getStudentId()))
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .phoneNumber(studentDto.getPhoneNumber()).build();

        if (Objects.nonNull(studentDto.getStudentId())) {
            student.setId(studentDto.getStudentId().toString());
        }

        return student;
    }

    @Override
    public StudentDto mapToStudentDto(Student student) {
        StudentDto studentDto = StudentDto.builder()
                .studentId(UUID.fromString(student.getId()))
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .phoneNumber(student.getPhoneNumber())
                .build();
        return studentDto;
    }
}
