package com.exam.service;

import com.exam.entity.Student;

import java.util.List;


public interface StudentService {

    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudentById(Long id);

    Student updateStudent(Student student);

    void deleteStudent(Long id);

}
