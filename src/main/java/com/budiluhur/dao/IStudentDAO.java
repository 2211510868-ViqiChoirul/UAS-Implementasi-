package com.budiluhur.dao;

import com.budiluhur.model.Student;
import java.util.List;

public interface IStudentDAO {
    List<Student> getAllStudents();
    boolean addStudent(Student s);
    boolean updateStudent(Student s);
    boolean deleteStudent(int studentId);
    boolean swapStudentEmails(int idA, int idB);
}