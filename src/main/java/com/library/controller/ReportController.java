package com.library.controller;

import com.library.service.BookIssueService;
import com.library.service.BookService;
import com.library.service.FineService;
import com.library.service.StudentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    private final BookService bookService;
    private final StudentService studentService;
    private final BookIssueService bookIssueService;
    private final FineService fineService;

    public ReportController(BookService bookService,
                            StudentService studentService,
                            BookIssueService bookIssueService,
                            FineService fineService) {

        this.bookService = bookService;
        this.studentService = studentService;
        this.bookIssueService = bookIssueService;
        this.fineService = fineService;
    }

    @GetMapping("/reports")
    public String reports(Model model) {

        model.addAttribute("books",
                bookService.getAllBooks());

        model.addAttribute("students",
                studentService.getAllStudents());

        model.addAttribute("issues",
                bookIssueService.getAllBookIssues());

        model.addAttribute("fines",
                fineService.getAllFines());

        return "reports/report-dashboard";
    }
}