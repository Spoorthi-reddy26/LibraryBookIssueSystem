package com.library.entity;

import jakarta.persistence.*;

@Entity
public class LibrarySettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double finePerDay;

    private Integer maxBooksPerStudent;

    private Integer maxIssueDays;

    public LibrarySettings() {
    }

    public Long getId() {
        return id;
    }

    public Double getFinePerDay() {
        return finePerDay;
    }

    public Integer getMaxBooksPerStudent() {
        return maxBooksPerStudent;
    }

    public Integer getMaxIssueDays() {
        return maxIssueDays;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFinePerDay(Double finePerDay) {
        this.finePerDay = finePerDay;
    }

    public void setMaxBooksPerStudent(Integer maxBooksPerStudent) {
        this.maxBooksPerStudent = maxBooksPerStudent;
    }

    public void setMaxIssueDays(Integer maxIssueDays) {
        this.maxIssueDays = maxIssueDays;
    }
}