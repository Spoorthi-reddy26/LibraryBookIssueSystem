package com.library.controller;

import com.library.entity.Book;
import com.library.entity.BookIssue;
import com.library.service.BookIssueService;
import com.library.service.BookService;
import com.library.service.StudentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    private final BookService bookService;
    private final StudentService studentService;
    private final BookIssueService bookIssueService;

    public DashboardController(BookService bookService,
                               StudentService studentService,
                               BookIssueService bookIssueService) {

        this.bookService = bookService;
        this.studentService = studentService;
        this.bookIssueService = bookIssueService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<Book> books = bookService.getAllBooks();
        List<BookIssue> issues = bookIssueService.getAllBookIssues();

        int totalBooks = books.size();

        int availableBooks = books.stream()
                .mapToInt(book ->
                        book.getAvailableCopies() == null
                                ? 0
                                : book.getAvailableCopies())
                .sum();

        long issuedBooks = issues.stream()
                .filter(issue ->
                        "ISSUED".equals(issue.getStatus()))
                .count();

        long overdueBooks = issues.stream()
                .filter(issue ->
                        "ISSUED".equals(issue.getStatus()))
                .filter(issue ->
                        issue.getDueDate() != null
                                && issue.getDueDate().isBefore(LocalDate.now()))
                .count();

        model.addAttribute("totalBooks", totalBooks);

        model.addAttribute("availableBooks", availableBooks);

        model.addAttribute("totalStudents",
                studentService.getAllStudents().size());

        model.addAttribute("totalIssues", issuedBooks);

        model.addAttribute("overdueBooks", overdueBooks);

        // Chart Data

        model.addAttribute("monthlyIssues",
                Arrays.asList(12, 19, 7, 14, 10, 18));

        model.addAttribute("monthlyFines",
                Arrays.asList(100, 250, 150, 300, 180, 400));
        
        model.addAttribute("categoryData",
                Arrays.asList(12, 8, 5, 15, 4));
        
        return "dashboard";
    }
}