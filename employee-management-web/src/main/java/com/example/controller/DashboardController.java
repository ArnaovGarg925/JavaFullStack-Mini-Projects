package com.example.controller;

import com.example.model.Department;
import com.example.model.Employee;
import com.example.security.JwtService;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final JwtService jwtService;

    public DashboardController(EmployeeService employeeService,
                               DepartmentService departmentService,
                               JwtService jwtService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.jwtService = jwtService;
    }

    @GetMapping({"/", "/dashboard"})
    public String showDashboard(Authentication authentication, Model model) {
        String username = (authentication != null) ? authentication.getName() : "Guest";
        String primaryRole = (authentication != null && !authentication.getAuthorities().isEmpty())
                ? authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "))
                : "ROLE_USER";

        // Aggregate statistics for dashboard cards
        int totalEmployees = employeeService.getTotalCount();
        int totalDepartments = departmentService.getTotalCount();
        double totalPayroll = employeeService.getTotalPayroll();
        double averageSalary = employeeService.getAverageSalary();

        // Recent additions
        List<Employee> recentEmployees = employeeService.getRecentEmployees(5);
        List<Department> departments = departmentService.getAllDepartments();

        // Department breakdown counts
        Map<Long, Long> departmentEmployeeCounts = new HashMap<>();
        for (Department dept : departments) {
            departmentEmployeeCounts.put(dept.getId(), employeeService.countByDepartment(dept.getId()));
        }

        // Generate demonstration JWT token for active session (Board requirement: Spring JWT)
        String activeJwtToken = jwtService.generateToken(username, primaryRole);

        model.addAttribute("username", username);
        model.addAttribute("primaryRole", primaryRole);
        model.addAttribute("totalEmployees", totalEmployees);
        model.addAttribute("totalDepartments", totalDepartments);
        model.addAttribute("totalPayroll", totalPayroll);
        model.addAttribute("averageSalary", averageSalary);
        model.addAttribute("recentEmployees", recentEmployees);
        model.addAttribute("departments", departments);
        model.addAttribute("departmentCounts", departmentEmployeeCounts);
        model.addAttribute("jwtToken", activeJwtToken);
        model.addAttribute("activeNav", "dashboard");

        return "dashboard";
    }
}
