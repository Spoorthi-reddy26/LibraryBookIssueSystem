package com.library.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private BookIssue bookIssue;

    private Integer daysOverdue;

    private Double amount;

    private String paidStatus;

    private LocalDateTime createdAt;

    public Fine() {
    }

    public Fine(Long id, BookIssue bookIssue, Integer daysOverdue, Double amount,
                String paidStatus, LocalDateTime createdAt) {
        this.id = id;
        this.bookIssue = bookIssue;
        this.daysOverdue = daysOverdue;
        this.amount = amount;
        this.paidStatus = paidStatus;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public BookIssue getBookIssue() {
        return bookIssue;
    }

    public Integer getDaysOverdue() {
        return daysOverdue;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookIssue(BookIssue bookIssue) {
        this.bookIssue = bookIssue;
    }

    public void setDaysOverdue(Integer daysOverdue) {
        this.daysOverdue = daysOverdue;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}