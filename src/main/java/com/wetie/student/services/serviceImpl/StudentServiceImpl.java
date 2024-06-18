package com.wetie.student.services.serviceImpl;

import com.wetie.student.access.dataaccess.entities.Student;
import com.wetie.student.access.dataaccess.repositories.StudentRepository;
import com.wetie.student.services.DtoEntityMapper;
import com.wetie.student.services.StudentService;
import com.wetie.student.services.dto.StudentDto;
import com.wetie.student.services.exception.TechnicalException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DtoEntityMapper dtoEntityMapper;

    @Override
    public StudentDto saveStudent(@Valid StudentDto studentDto) {

        validSaveStudent(studentDto);

        Student student = dtoEntityMapper.mapToStudent(studentDto);
        student.setId(null);
        student = studentRepository.save(student);
        StudentDto saveStudentDto = dtoEntityMapper.mapToStudentDto(student);

        return saveStudentDto;
    }

    @Override
    public StudentDto updateStudent(@Valid StudentDto studentDto) {

        validUpdateStudent(studentDto);

        Student student = dtoEntityMapper.mapToStudent(studentDto);
        studentRepository.save(student);

        return dtoEntityMapper.mapToStudentDto(student);
    }

    @Override
    public StudentDto findStudentById(UUID studentId) {

        Optional<Student> optionalStudent = studentRepository.findById(String.valueOf(studentId));

        if (optionalStudent.isEmpty()) {
            String message = String.format("Student with id %s does not exist", studentId);
            throw  new TechnicalException(TechnicalException.NOT_FOUND_TYP, message);
        }

        return dtoEntityMapper.mapToStudentDto(optionalStudent.get());
    }

    @Override
    public List<StudentDto> findAllStudents() {

        /*List<StudentDto> studentDtoList = studentRepository.findAll().stream()
                .map(student -> dtoEntityMapper.mapToStudentDto(student)).toList();*/

        return studentRepository.findAll()
                .stream()
                .map(student -> dtoEntityMapper.mapToStudentDto(student))
                .toList();
    }

    private void validSaveStudent(StudentDto studentDto) {

        Optional<Student> optionalStudent = studentRepository.findStudents(studentDto.getFirstName(),
                        studentDto.getLastName(),
                        studentDto.getPhoneNumber())
                .stream().findFirst();

        if (optionalStudent.isPresent()) {
            String message = String.format(
                    String.format("Student with phone number %s already exists", studentDto.getPhoneNumber())
                            + " or " +
                    "Student with firstname %s and lastname %s already exists ", studentDto.getFirstName(), studentDto.getLastName());

            throw new TechnicalException(TechnicalException.NOT_FOUND_TYP, message);
        }
    }

    private void validUpdateStudent(StudentDto studentDto) {

        String studentId = String.valueOf(studentDto.getStudentId());
        Optional<Student> optionalStudent = studentRepository.findById(String.valueOf(studentDto.getStudentId()));

        if (optionalStudent.isEmpty()) {
            String message = String.format("Student with id %s does not exist", studentDto.getStudentId());
            throw new TechnicalException(TechnicalException.NOT_FOUND_TYP, message);
        }

        optionalStudent = studentRepository.findStudents(studentDto.getFirstName(),
                        studentDto.getLastName(),
                        studentDto.getPhoneNumber())
                .stream().findFirst();

        if (optionalStudent.isPresent() && !optionalStudent.get().getId().equalsIgnoreCase(studentId)) {

            String message = String.format(
                    String.format("Student with phone number %s already exists", studentDto.getPhoneNumber())
                            + " or " +
                            "Student with firstname %s and lastname %s already exists ", studentDto.getFirstName(), studentDto.getLastName());

            throw new TechnicalException(TechnicalException.CASHIER_ALREADY_EXISTS, message);
        }
    }
}


// Starting with Spring version 4.3, the single bean constructor does not need to be annotated with the @Autowired annotation.
// This makes it possible to use the @RequiredArgsConstructor and @AllArgsConstructor annotations for dependency injection:
// MethodArgumentNotValidException it gardered all exception messages