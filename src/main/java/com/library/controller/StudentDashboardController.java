package com.library.controller;

import com.library.entity.BookIssue;
import com.library.entity.Student;
import com.library.service.BookIssueService;
import com.library.service.BookService;
import com.library.service.StudentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class StudentDashboardController {

    private final BookService bookService;
    private final BookIssueService bookIssueService;
    private final StudentService studentService;

    public StudentDashboardController(BookService bookService,
                                      BookIssueService bookIssueService,
                                      StudentService studentService) {
        this.bookService = bookService;
        this.bookIssueService = bookIssueService;
        this.studentService = studentService;
    }

    @GetMapping("/student/dashboard")
    public String studentDashboard(Model model, Principal principal) {

        String email = principal.getName();

        Student student = studentService.findByEmail(email);

        if (student == null) {
            return "redirect:/login?error=true";
        }

        List<BookIssue> studentIssues =
                bookIssueService.getIssuesByStudentEmail(email);

        List<BookIssue> issuedOnly = studentIssues.stream()
                .filter(issue -> issue.getReturnDate() == null)
                .toList();

        long issuedBooks = issuedOnly.size();

        long returnedBooks = studentIssues.stream()
                .filter(issue -> issue.getReturnDate() != null)
                .count();

        double pendingFine = issuedOnly.stream()
                .filter(issue -> issue.getDueDate() != null)
                .filter(issue -> issue.getDueDate().isBefore(java.time.LocalDate.now()))
                .mapToDouble(issue -> {
                    long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(
                            issue.getDueDate(),
                            java.time.LocalDate.now()
                    );
                    return overdueDays * 10;
                })
                .sum();

        model.addAttribute("studentName", student.getName());
        model.addAttribute("studentId", student.getStudentCode());
        model.addAttribute("totalBooks", bookService.getAllBooks().size());
        model.addAttribute("issuedBooks", issuedBooks);
        model.addAttribute("returnedBooks", returnedBooks);
        model.addAttribute("pendingFine", pendingFine);
        model.addAttribute("dueBooks", issuedOnly);

        return "student-dashboard";
    }

    @GetMapping("/student/books")
    public String studentBooks(@RequestParam(required = false) String keyword,
                               Model model) {

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("books", bookService.searchBooks(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("books", bookService.getAllBooks());
        }

        return "student-books";
    }

    @GetMapping("/student/history")
    public String studentHistory(Model model, Principal principal) {

        String email = principal.getName();

        List<BookIssue> studentIssues =
                bookIssueService.getIssuesByStudentEmail(email);

        model.addAttribute("issues", studentIssues);

        return "student-history";
    }
}