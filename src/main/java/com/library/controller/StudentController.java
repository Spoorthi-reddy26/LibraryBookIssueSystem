package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.entity.Student;
import com.library.repository.BookIssueRepository;
import com.library.service.StudentService;


@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final BookIssueRepository bookIssueRepository;
    
    public StudentController(StudentService studentService,
            BookIssueRepository bookIssueRepository) {

this.studentService = studentService;
this.bookIssueRepository = bookIssueRepository;
}

    @GetMapping
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students/student-list";
    }

    @GetMapping("/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/add-student";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "students/edit-student";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student) {
        studentService.updateStudent(id, student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
    
    @GetMapping("/search")
    public String searchStudents(@RequestParam String keyword,
                                 Model model) {

        model.addAttribute("students",
                studentService.searchStudents(keyword));

        return "students/student-list";
    }
    
    @GetMapping("/history/{id}")
    public String studentHistory(@PathVariable Long id, Model model) {

        model.addAttribute("student",
                studentService.getStudentById(id));

        model.addAttribute("issues",
                bookIssueRepository.findByStudentId(id));

        return "students/student-history";
    }
}