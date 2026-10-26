package com.example.controller;

import com.example.model.Employee;
import com.example.model.Role;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    // List all employees with search & department filter (Extra feature)
    @GetMapping
    public String listEmployees(@RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "departmentId", required = false) Long departmentId,
                                Model model) {
        List<Employee> employees;
        if ((keyword != null && !keyword.trim().isEmpty()) || (departmentId != null && departmentId > 0)) {
            employees = employeeService.searchEmployees(keyword, departmentId);
        } else {
            employees = employeeService.getAllEmployees();
        }

        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedDepartmentId", departmentId);
        model.addAttribute("activeNav", "employees");
        return "employees";
    }

    // Show form to add new employee
    @GetMapping("/new")
    public String showNewEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("roles", Role.values());
        model.addAttribute("isEdit", false);
        model.addAttribute("activeNav", "employees");
        return "employee-form";
    }

    // Save or update employee
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee,
                               RedirectAttributes redirectAttributes) {
        boolean isNew = (employee.getId() == null);
        employeeService.saveEmployee(employee);

        if (isNew) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Employee '" + employee.getFullName() + "' created successfully!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Employee '" + employee.getFullName() + "' updated successfully!");
        }

        return "redirect:/employees";
    }

    // Show form to edit existing employee
    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable("id") Long id,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        return employeeService.getEmployeeById(id)
                .map(emp -> {
                    model.addAttribute("employee", emp);
                    model.addAttribute("departments", departmentService.getAllDepartments());
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("isEdit", true);
                    model.addAttribute("activeNav", "employees");
                    return "employee-form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Employee not found with ID: " + id);
                    return "redirect:/employees";
                });
    }

    // Delete employee
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id,
                                 RedirectAttributes redirectAttributes) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Employee ID " + id + " deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not delete employee ID " + id + ".");
        }
        return "redirect:/employees";
    }
}
