package com.library.controller;

import com.library.entity.LibrarySettings;
import com.library.repository.LibrarySettingsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SettingsController {

    private final LibrarySettingsRepository librarySettingsRepository;

    public SettingsController(LibrarySettingsRepository librarySettingsRepository) {
        this.librarySettingsRepository = librarySettingsRepository;
    }

    @GetMapping("/settings")
    public String settingsPage(Model model) {

        LibrarySettings settings = librarySettingsRepository.findById(1L)
                .orElseGet(() -> {
                    LibrarySettings defaultSettings = new LibrarySettings();
                    defaultSettings.setFinePerDay(5.0);
                    defaultSettings.setMaxBooksPerStudent(3);
                    defaultSettings.setMaxIssueDays(14);
                    return librarySettingsRepository.save(defaultSettings);
                });

        model.addAttribute("settings", settings);

        return "settings";
    }

    @PostMapping("/settings/save")
    public String saveSettings(@ModelAttribute LibrarySettings settings) {

        settings.setId(1L);

        librarySettingsRepository.save(settings);

        return "redirect:/settings?success";
    }
}