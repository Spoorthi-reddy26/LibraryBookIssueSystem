package com.library.service;

import com.library.entity.LibrarySettings;

public interface LibrarySettingsService {

    LibrarySettings saveSettings(LibrarySettings settings);

    LibrarySettings getSettings();
}