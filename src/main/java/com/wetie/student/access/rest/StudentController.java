package com.wetie.student.access.rest;

import com.wetie.student.services.StudentService;
import com.wetie.student.services.dto.StudentDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> saveStudent(@RequestBody @Validated StudentDto studentDto) {
        //return ResponseEntity.ok().body(studentService.saveStudent(studentDto));
        return new ResponseEntity<>(studentService.saveStudent(studentDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> findAllStudents() {
        List<StudentDto> allStudents = studentService.findAllStudents();
        return ResponseEntity.ok(allStudents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable("id") UUID clientId) {
        StudentDto studentById = studentService.findStudentById(clientId);
        return ResponseEntity.ok(studentById);
    }

    @PutMapping
    public ResponseEntity<StudentDto> updateStudent(@Validated @RequestBody StudentDto studentDto) {
        studentDto = studentService.updateStudent(studentDto);
        return ResponseEntity.ok().body(studentDto);
    }
}


// @ResponseStatus(HttpStatus.CREATED) ceci au dessus de la fction, il faut retirer ResponseEntity