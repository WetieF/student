package com.wetie.student.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wetie.student.access.dataaccess.entities.Student;
import com.wetie.student.access.dataaccess.repositories.StudentRepository;
import com.wetie.student.services.dto.StudentDto;
import com.wetie.student.services.serviceImpl.DtoEntityMapperImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DtoEntityMapperImpl dtoEntityMapper;

    private StudentDto studentDto;
    UUID studentId = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        studentDto = StudentDto.builder()
                .studentId(studentId)
                .firstName("Mensih")
                .lastName("Wetie")
                .phoneNumber("0237678956425")
                .build();

        studentRepository.deleteAll();
    }

    @Test
    void saveStudent_when_student_not_exists() throws Exception {

        // given -precondition or setup

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(studentDto.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(studentDto.getLastName())));
    }

    @Test
    void findAllStudents_when_employees_exists() throws Exception {

        // given -precondition or setup
        List<StudentDto> studentDtos = new ArrayList<>();
        studentDtos.add(studentDto);
        studentDtos.add(StudentDto.builder()
                .firstName("Franck")
                .lastName("Nana")
                .phoneNumber("00237625465342")
                .build());

        studentRepository.saveAll(studentDtos.stream().map((studentDto)->
                dtoEntityMapper.mapToStudent(studentDto)).collect(Collectors.toList()));

        // when - action or behaviour that we are going to test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/students"));


        // then - verify the output
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(studentDtos.size())));
    }

    @Test
    void getStudentById_when_student_exists() throws Exception {

        // given -precondition or setup
        Student student = dtoEntityMapper.mapToStudent(studentDto);
        student = studentRepository.save(student);
        String url =  "/students/" + student.getId();

        System.out.println("####################" + url);

        // when - action or behaviour that we are going to test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url));

        // then - verify the output
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(student.getLastName())));

    }

    @Test
    void updateStudent() throws Exception {

        // given -precondition or setup
        Student student = Student.builder()
                .firstName("Nana")
                .lastName("Nana")
                .phoneNumber("0237678956425")
                .build();

        student = studentRepository.save(student);

        StudentDto updatedstudentDto = dtoEntityMapper.mapToStudentDto(student);

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedstudentDto)));

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(updatedstudentDto.getLastName())));
    }
}
