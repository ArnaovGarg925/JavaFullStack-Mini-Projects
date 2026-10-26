package com.example.service;

import com.example.model.Department;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DepartmentService {

    // Compulsory requirement from board: Data stored in ArrayList
    private final List<Department> departmentList = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(100);

    public DepartmentService() {
        seedInitialDepartments();
    }

    private void seedInitialDepartments() {
        departmentList.add(new Department(1L, "Engineering", "Building A - 4th Floor", "Alex Morgan"));
        departmentList.add(new Department(2L, "Human Resources", "Building B - 2nd Floor", "Sarah Jenkins"));
        departmentList.add(new Department(3L, "Finance", "Building A - 3rd Floor", "David Chen"));
        departmentList.add(new Department(4L, "Marketing", "Building C - 1st Floor", "Emma Watson"));
        departmentList.add(new Department(5L, "Operations", "Building B - 1st Floor", "Vikram Patel"));
    }

    public synchronized List<Department> getAllDepartments() {
        return Collections.unmodifiableList(new ArrayList<>(departmentList));
    }

    public synchronized Optional<Department> getDepartmentById(Long id) {
        if (id == null) return Optional.empty();
        return departmentList.stream()
                .filter(dept -> dept.getId().equals(id))
                .findFirst();
    }

    public synchronized Department saveDepartment(Department department) {
        if (department.getId() == null) {
            department.setId(idSequence.incrementAndGet());
            departmentList.add(department);
            return department;
        } else {
            for (int i = 0; i < departmentList.size(); i++) {
                if (departmentList.get(i).getId().equals(department.getId())) {
                    departmentList.set(i, department);
                    return department;
                }
            }
            departmentList.add(department);
            return department;
        }
    }

    public synchronized boolean deleteDepartment(Long id) {
        return departmentList.removeIf(dept -> dept.getId().equals(id));
    }

    public synchronized int getTotalCount() {
        return departmentList.size();
    }
}
