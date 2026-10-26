package com.example.service;

import com.example.model.Department;
import com.example.model.Employee;
import com.example.model.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    // Compulsory requirement from board: Data stored in ArrayList
    private final List<Employee> employeeList = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1000);
    private final DepartmentService departmentService;

    public EmployeeService(DepartmentService departmentService) {
        this.departmentService = departmentService;
        seedInitialEmployees();
    }

    private void seedInitialEmployees() {
        saveEmployee(new Employee(null, "Arnaov", "Garg", "arnaov.garg@example.com", "+91-9876543210",
                "Lead Full Stack Developer", 1L, null, 95000.0, Role.ROLE_ADMIN, "Active"));

        saveEmployee(new Employee(null, "Rohan", "Sharma", "rohan.sharma@example.com", "+91-9811122233",
                "Senior Backend Engineer", 1L, null, 82000.0, Role.ROLE_MANAGER, "Active"));

        saveEmployee(new Employee(null, "Priya", "Nair", "priya.nair@example.com", "+91-9822233344",
                "Talent Acquisition Specialist", 2L, null, 62000.0, Role.ROLE_USER, "Active"));

        saveEmployee(new Employee(null, "Ananya", "Iyer", "ananya.iyer@example.com", "+91-9833344455",
                "Financial Analyst", 3L, null, 71000.0, Role.ROLE_USER, "Active"));

        saveEmployee(new Employee(null, "Karthik", "Rao", "karthik.rao@example.com", "+91-9844455566",
                "Growth Marketing Manager", 4L, null, 68000.0, Role.ROLE_MANAGER, "Active"));

        saveEmployee(new Employee(null, "Sneha", "Reddy", "sneha.reddy@example.com", "+91-9855566677",
                "Operations Coordinator", 5L, null, 54000.0, Role.ROLE_USER, "On Leave"));
    }

    // --- CRUD Operations ---

    public synchronized List<Employee> getAllEmployees() {
        return Collections.unmodifiableList(new ArrayList<>(employeeList));
    }

    public synchronized Optional<Employee> getEmployeeById(Long id) {
        if (id == null) return Optional.empty();
        return employeeList.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();
    }

    public synchronized Employee saveEmployee(Employee employee) {
        // Resolve Department Relationship
        if (employee.getDepartmentId() != null) {
            departmentService.getDepartmentById(employee.getDepartmentId())
                    .ifPresent(dept -> employee.setDepartmentName(dept.getName()));
        }

        if (employee.getId() == null) {
            employee.setId(idSequence.incrementAndGet());
            employeeList.add(employee);
            return employee;
        } else {
            for (int i = 0; i < employeeList.size(); i++) {
                if (employeeList.get(i).getId().equals(employee.getId())) {
                    employeeList.set(i, employee);
                    return employee;
                }
            }
            employeeList.add(employee);
            return employee;
        }
    }

    public synchronized boolean deleteEmployee(Long id) {
        return employeeList.removeIf(emp -> emp.getId().equals(id));
    }

    // --- Additional Features: Search, Filter, and Analytics ---

    public synchronized List<Employee> searchEmployees(String keyword, Long departmentId) {
        return employeeList.stream()
                .filter(emp -> {
                    boolean matchesKeyword = true;
                    if (keyword != null && !keyword.trim().isEmpty()) {
                        String lower = keyword.trim().toLowerCase();
                        matchesKeyword = emp.getFullName().toLowerCase().contains(lower) ||
                                (emp.getEmail() != null && emp.getEmail().toLowerCase().contains(lower)) ||
                                (emp.getJobTitle() != null && emp.getJobTitle().toLowerCase().contains(lower));
                    }

                    boolean matchesDepartment = true;
                    if (departmentId != null && departmentId > 0) {
                        matchesDepartment = departmentId.equals(emp.getDepartmentId());
                    }

                    return matchesKeyword && matchesDepartment;
                })
                .collect(Collectors.toList());
    }

    public synchronized int getTotalCount() {
        return employeeList.size();
    }

    public synchronized double getTotalPayroll() {
        return employeeList.stream()
                .mapToDouble(emp -> emp.getSalary() != null ? emp.getSalary() : 0.0)
                .sum();
    }

    public synchronized double getAverageSalary() {
        if (employeeList.isEmpty()) return 0.0;
        return getTotalPayroll() / employeeList.size();
    }

    public synchronized List<Employee> getRecentEmployees(int limit) {
        int start = Math.max(0, employeeList.size() - limit);
        List<Employee> sublist = new ArrayList<>(employeeList.subList(start, employeeList.size()));
        Collections.reverse(sublist);
        return sublist;
    }

    public synchronized long countByDepartment(Long departmentId) {
        if (departmentId == null) return 0;
        return employeeList.stream()
                .filter(emp -> departmentId.equals(emp.getDepartmentId()))
                .count();
    }
}
