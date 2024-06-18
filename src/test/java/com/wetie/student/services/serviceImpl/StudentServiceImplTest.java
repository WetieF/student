package com.wetie.student.services.serviceImpl;

import com.wetie.student.access.dataaccess.entities.Student;
import com.wetie.student.access.dataaccess.repositories.StudentRepository;
import com.wetie.student.services.DtoEntityMapper;
import com.wetie.student.services.dto.StudentDto;
import com.wetie.student.services.exception.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DtoEntityMapper dtoEntityMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private StudentDto studentDto;

    private Student student;

    UUID studentId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        studentDto = StudentDto.builder()
                .studentId(studentId)
                .firstName("Nana")
                .lastName("Wetie")
                .phoneNumber("00237698784568")
                .build();

        student = Student.builder()
                .id(studentId.toString())
                .firstName("Nana")
                .lastName("Wetie")
                .phoneNumber("00237698784568")
                .build();
    }

    @Test
    void saveStudent_when_student_not_exists() {

        // given -precondition or setup
        BDDMockito.given(studentRepository.findStudents(student.getFirstName(), student.getLastName(), student.getPhoneNumber()))
                .willReturn(new ArrayList<>());

        BDDMockito.given(dtoEntityMapper.mapToStudent(studentDto)).willReturn(student);
        BDDMockito.given(studentRepository.save(BDDMockito.any(Student.class))).willReturn(student);
        BDDMockito.given(dtoEntityMapper.mapToStudentDto(student)).willReturn(studentDto);

        // when - action or behaviour that we are going to test
        StudentDto result = studentService.saveStudent(this.studentDto);

        // then - verify the output
        Assertions.assertThat(result).isNotNull();
        BDDMockito.verify(studentRepository, Mockito.times(1))
                .findStudents(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Assertions.assertThat(result.getStudentId()).isEqualTo(studentId);
    }

    @Test
    void saveStudent_when_student_exists() {

        // given -precondition or setup
        String errorMessage = String.format(
                String.format("Student with phone number %s already exists", studentDto.getPhoneNumber())
                        + " or " +
                        "Student with firstname %s and lastname %s already exists ", studentDto.getFirstName(), studentDto.getLastName());

        List<Student> students = new ArrayList<>();
        students.add(student);

        BDDMockito.given(studentRepository.findStudents(student.getFirstName(), student.getLastName(), student.getPhoneNumber()))
                .willReturn(students);

        // when - action or behaviour that we are going to test
        Assertions.assertThatThrownBy(() -> studentService.saveStudent(studentDto))
                .isInstanceOf(TechnicalException.class)
                .hasMessage(errorMessage);

        // then - verify the output
        BDDMockito.verify(studentRepository, BDDMockito.times(1))
                .findStudents(BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.anyString());
        Mockito.verify(studentRepository, Mockito.never()).save(Mockito.any(Student.class));
    }

    @Test
    void updateStudent_when_student_not_exists() {

        // given -precondition or setup
        String errorMessage = String.format("Student with id %s does not exist", studentId);
        BDDMockito.given(studentRepository.findById(studentId.toString())).willReturn(Optional.empty());

        // when - action or behaviour that we are going to test
       Assertions.assertThatExceptionOfType(TechnicalException.class)
                .isThrownBy(() -> {
                    studentService.updateStudent(this.studentDto);
                }).withMessage(errorMessage);

        /*Assertions.assertThatThrownBy(() -> studentService.updateStudent(studentDto))
                .isInstanceOf(TechnicalException.class)
                .hasMessage(errorMessage);*/

        // then - verify the output
        Mockito.verify(studentRepository).findById(studentId.toString());
        Mockito.verify(studentRepository, Mockito.never()).save(Mockito.any(Student.class));
    }

    @Test
    void updateStudent_when_another_client_exists() {  // method under Test

        // given -precondition or setup
        String errorMessage = String.format(
                String.format("Student with phone number %s already exists", studentDto.getPhoneNumber())
                        + " or " +
                        "Student with firstname %s and lastname %s already exists ", studentDto.getFirstName(), studentDto.getLastName());

        List<Student> students = new ArrayList<>();
        students.add(student);

        BDDMockito.given(studentRepository.findById(BDDMockito.anyString())).willReturn(Optional.of(student));
        BDDMockito.given(studentRepository.findStudents(student.getFirstName(), student.getLastName(), student.getPhoneNumber()))
                .willReturn(students);

        studentDto.setStudentId(UUID.randomUUID());

        // when - action or behaviour that we are going to test
        Assertions.assertThatThrownBy(() -> studentService.updateStudent(studentDto))
                .isInstanceOf(TechnicalException.class)
                .hasMessage(errorMessage);

        // then - verify the output
        BDDMockito.verify(studentRepository, BDDMockito.times(1))
                .findStudents(BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.anyString());

        Mockito.verify(studentRepository, Mockito.never()).save(Mockito.any(Student.class));

    }

    @Test
    void updateStudent_when_client_exists() {

        // given -precondition or setup
        List<Student> students = new ArrayList<>();
        students.add(student);

        BDDMockito.given(studentRepository.findById(BDDMockito.anyString())).willReturn(Optional.of(student));
        BDDMockito.given(studentRepository.findStudents(student.getFirstName(), student.getLastName(), student.getPhoneNumber()))
                .willReturn(students);

        BDDMockito.given(dtoEntityMapper.mapToStudent(studentDto)).willReturn(student);
        BDDMockito.given(studentRepository.save(BDDMockito.any(Student.class))).willReturn(student);
        BDDMockito.given(dtoEntityMapper.mapToStudentDto(student)).willReturn(studentDto);

        // when - action or behaviour that we are going to test
        StudentDto result = studentService.updateStudent(this.studentDto);

        // then - verify the output
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void findStudentById_when_student_exists() {

        // given -precondition or setup
        BDDMockito.given(studentRepository.findById(studentId.toString()))
                .willReturn(Optional.of(student));
        BDDMockito.given(dtoEntityMapper.mapToStudentDto(student))
                .willReturn(studentDto);

        // when - action or behaviour that we are going to test
        StudentDto result = studentService.findStudentById(studentId);

        // then - verify the output
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getStudentId()).isEqualTo(studentId);
    }

    @Test
    void findStudentById_when_student_not_exists() {

        // given -precondition or setup
        String errorMessage = String.format("Student with id %s does not exist", studentId);
        BDDMockito.given(studentRepository.findById(studentId.toString()))
                .willReturn(Optional.empty());

        // when - action or behaviour that we are going to test
        Assertions.assertThatThrownBy(() -> studentService.findStudentById(studentId))
                .isInstanceOf(TechnicalException.class)
                .hasMessage(errorMessage);

        // then - verify the output
        BDDMockito.verify(studentRepository, Mockito.times(1))
                .findById(studentId.toString());
    }

    @Test
    void findAllStudents_when_students_exists() {

        // given -precondition or setup
        Student student1 = Student.builder()
                .id(studentId.toString())
                .firstName("Francis")
                .lastName("Lewat")
                .phoneNumber("00237688784568")
                .build();

        BDDMockito.given(studentRepository.findAll()).willReturn(List.of(student, student1));

        // when - action or behaviour that we are going to test
        List<StudentDto> results = studentService.findAllStudents();

        // then - verify the output
        Assertions.assertThat(results.size()).isEqualTo(2);
        Assertions.assertThat(results).isNotNull();
    }

    @Test
    void findAllStudents_when_students_not_exists() {

        // given -precondition or setup
        BDDMockito.given(studentRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or behaviour that we are going to test
        List<StudentDto> results = studentService.findAllStudents();

        // then - verify the output;
        Assertions.assertThat(results).isEmpty();
        Assertions.assertThat(results.size()).isEqualTo(0);
    }
}


// https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html#assertDoesNotThrow(org.junit.jupiter.api.function.Executable)


// by Integration Test no mocking is involved.


