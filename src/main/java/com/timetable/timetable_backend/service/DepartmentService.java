package com.timetable.timetable_backend.service;

import java.util.List;
import com.timetable.timetable_backend.entity.Department;

public interface DepartmentService {

    Department createDepartment(Department department);

    List<Department> getAllDepartments();
}
