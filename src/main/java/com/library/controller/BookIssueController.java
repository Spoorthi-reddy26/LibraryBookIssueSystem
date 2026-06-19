package com.library.controller;

import com.library.service.BookIssueService;
import com.library.service.BookService;
import com.library.service.StudentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/issues")
public class BookIssueController {

    private final BookIssueService bookIssueService;
    private final BookService bookService;
    private final StudentService studentService;

    public BookIssueController(BookIssueService bookIssueService,
                               BookService bookService,
                               StudentService studentService) {

        this.bookIssueService = bookIssueService;
        this.bookService = bookService;
        this.studentService = studentService;
    }

    @GetMapping
    public String getAllIssues(Model model) {

        model.addAttribute("issues",
                bookIssueService.getAllBookIssues());

        return "issues/issued-books";
    }

    @GetMapping("/add")
    public String showIssueForm(Model model) {

        model.addAttribute("books",
                bookService.getAllBooks());

        model.addAttribute("students",
                studentService.getAllStudents());

        return "issues/issue-book";
    }

    @PostMapping("/save")
    public String issueBook(@RequestParam Long bookId,
                            @RequestParam Long studentId) {

        bookIssueService.issueBook(bookId, studentId);

        return "redirect:/issues";
    }

    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable Long id) {

        bookIssueService.returnBook(id);

        return "redirect:/issues";
    }
}