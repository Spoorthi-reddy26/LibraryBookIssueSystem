package com.library.service.impl;

import com.library.entity.BookIssue;
import com.library.entity.Fine;
import com.library.entity.LibrarySettings;
import com.library.repository.BookIssueRepository;
import com.library.repository.FineRepository;
import com.library.repository.LibrarySettingsRepository;
import com.library.service.FineService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final BookIssueRepository bookIssueRepository;
    private final LibrarySettingsRepository librarySettingsRepository;

    public FineServiceImpl(FineRepository fineRepository,
                           BookIssueRepository bookIssueRepository,
                           LibrarySettingsRepository librarySettingsRepository) {
        this.fineRepository = fineRepository;
        this.bookIssueRepository = bookIssueRepository;
        this.librarySettingsRepository = librarySettingsRepository;
    }

    @Override
    public Fine calculateFine(Long issueId) {

        BookIssue issue = bookIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        LocalDate today = LocalDate.now();

        long overdueDays = ChronoUnit.DAYS.between(issue.getDueDate(), today);

        if (overdueDays <= 0) {
            overdueDays = 0;
        }

        LibrarySettings settings = librarySettingsRepository.findById(1L)
                .orElseGet(() -> {
                    LibrarySettings defaultSettings = new LibrarySettings();
                    defaultSettings.setFinePerDay(5.0);
                    defaultSettings.setMaxBooksPerStudent(3);
                    defaultSettings.setMaxIssueDays(14);
                    return librarySettingsRepository.save(defaultSettings);
                });

        double finePerDay = settings.getFinePerDay();

        double fineAmount = overdueDays * finePerDay;

        Fine fine = new Fine();
        fine.setBookIssue(issue);
        fine.setDaysOverdue((int) overdueDays);
        fine.setAmount(fineAmount);
        fine.setPaidStatus("UNPAID");
        fine.setCreatedAt(LocalDateTime.now());

        return fineRepository.save(fine);
    }

    @Override
    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }

    @Override
    public Fine markFineAsPaid(Long fineId) {

        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new RuntimeException("Fine not found"));

        fine.setPaidStatus("PAID");

        return fineRepository.save(fine);
    }
}