package com.wetie.student.access.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wetie.student.services.StudentService;
import com.wetie.student.services.dto.StudentDto;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    // create a mock instance of studentService and add it to the appl context so that it's injected into studentController
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private StudentDto studentDto;

    UUID studentId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        studentDto = StudentDto.builder()
                .studentId(studentId)
                .firstName("Mensih")
                .lastName("Wetie")
                .phoneNumber("0237678956425")
                .build();
    }

    @Test
    void saveStudent_when_student_not_exists() throws Exception {

        // given -precondition or setup
        BDDMockito.given(studentService.saveStudent(ArgumentMatchers.any(StudentDto.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));  // parce que ma fction saveStudent a 1 seul argument.

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

        BDDMockito.given(studentService.findAllStudents())
                .willReturn(studentDtos);

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
        BDDMockito.given(studentService.findStudentById(studentId))
                .willReturn(studentDto);

        // when - action or behaviour that we are going to test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", studentId));

        // then - verify the output
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(studentDto.getLastName())));

    }

    /*@Test  // pas besoi deja traite dans service
    void getStudentById_when_student_not_exists() throws Exception {

        // given -precondition or setup
        BDDMockito.given(studentService.findStudentById(studentId))
                .willReturn(new StudentDto());

        // when - action or behaviour that we are going to test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", studentId));

        // then - verify the output
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }*/

    @Test
    void updateStudent() throws Exception {

        // given -precondition or setup
        //BDDMockito.given(studentService.updateStudent(studentDto)).willReturn(studentDto);
        BDDMockito.given(studentService.updateStudent(BDDMockito.any(StudentDto.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)));

        // then - verify the output
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(studentDto.getLastName())));
    }
}

//  https://springframework.guru/testing-spring-boot-restful-services/

// https://www.logicbig.com/tutorials/spring-framework/spring-core/validator-factory-methods.html