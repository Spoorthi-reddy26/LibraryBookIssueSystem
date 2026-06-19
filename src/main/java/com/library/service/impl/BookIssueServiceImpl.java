package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.BookIssue;
import com.library.entity.Student;
import com.library.repository.BookIssueRepository;
import com.library.repository.BookRepository;
import com.library.repository.StudentRepository;
import com.library.service.BookIssueService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookIssueServiceImpl implements BookIssueService {

    private final BookIssueRepository bookIssueRepository;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public BookIssueServiceImpl(BookIssueRepository bookIssueRepository,
                                BookRepository bookRepository,
                                StudentRepository studentRepository) {
        this.bookIssueRepository = bookIssueRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public BookIssue issueBook(Long bookId, Long studentId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book not available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BookIssue issue = new BookIssue();
        issue.setBook(book);
        issue.setStudent(student);
        issue.setIssueDate(LocalDate.now());
        issue.setDueDate(LocalDate.now().plusDays(7));
        issue.setStatus("ISSUED");
        issue.setIssuedBy("ADMIN");

        return bookIssueRepository.save(issue);
    }

    @Override
    public BookIssue returnBook(Long issueId) {

        BookIssue issue = bookIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue record not found"));

        if ("RETURNED".equals(issue.getStatus())) {
            throw new RuntimeException("Book already returned");
        }

        issue.setReturnDate(LocalDate.now());
        issue.setStatus("RETURNED");

        Book book = issue.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return bookIssueRepository.save(issue);
    }

    @Override
    public List<BookIssue> getAllBookIssues() {
        return bookIssueRepository.findAll();
    }

    @Override
    public BookIssue getIssueById(Long id) {
        return bookIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue record not found"));
    }
    @Override
    public List<BookIssue> getIssuesByStudentEmail(String email) {
        return bookIssueRepository.findByStudent_Email(email);
    }
}