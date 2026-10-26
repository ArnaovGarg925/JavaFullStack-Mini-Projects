package com.example.controller;

import com.example.model.Department;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listDepartments(Model model) {
        List<Department> departments = departmentService.getAllDepartments();

        Map<Long, Long> staffCounts = new HashMap<>();
        for (Department dept : departments) {
            staffCounts.put(dept.getId(), employeeService.countByDepartment(dept.getId()));
        }

        model.addAttribute("departments", departments);
        model.addAttribute("staffCounts", staffCounts);
        model.addAttribute("newDepartment", new Department());
        model.addAttribute("activeNav", "departments");
        return "department";
    }

    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute("newDepartment") Department department,
                                 RedirectAttributes redirectAttributes) {
        if (department.getName() == null || department.getName().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Department name cannot be blank.");
            return "redirect:/departments";
        }

        departmentService.saveDepartment(department);
        redirectAttributes.addFlashAttribute("successMessage",
                "Department '" + department.getName() + "' saved successfully!");
        return "redirect:/departments";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id,
                                   RedirectAttributes redirectAttributes) {
        long assignedEmployees = employeeService.countByDepartment(id);
        if (assignedEmployees > 0) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Cannot delete department. There are still " + assignedEmployees +
                            " employee(s) assigned to it.");
            return "redirect:/departments";
        }

        boolean deleted = departmentService.deleteDepartment(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Department deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Department not found with ID: " + id);
        }
        return "redirect:/departments";
    }
}
