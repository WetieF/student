package com.wetie.student.access.dataaccess.repositories;

import com.wetie.student.access.dataaccess.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {

    @Query(value = "select distinct s.* from MT_STUDENT s where " +
            "(s.first_name=:firstName" +
            " and s.last_name=:lastName) or s.phone_number=:phoneNumber", nativeQuery = true)
    List<Student> findStudents(@Param("firstName") String firstName,
                               @Param("lastName") String lastName,
                               @Param("phoneNumber") String phoneNumber);
}
