package com.library.repository;

import com.library.entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {

    List<BookIssue> findByStudentId(Long studentId);

    List<BookIssue> findByStudent_Email(String email);

    @Query("SELECT b FROM BookIssue b WHERE b.returnDate IS NULL AND b.dueDate < CURRENT_DATE")
    List<BookIssue> findOverdueBooks();
}