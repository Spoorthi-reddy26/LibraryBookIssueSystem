package com.library.service;

import com.library.entity.BookIssue;

import java.util.List;

public interface BookIssueService {

    BookIssue issueBook(Long bookId, Long studentId);

    BookIssue returnBook(Long issueId);

    List<BookIssue> getAllBookIssues();
    
    BookIssue getIssueById(Long id);
    
    List<BookIssue> getIssuesByStudentEmail(String email);
}