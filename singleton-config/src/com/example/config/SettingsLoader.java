package com.example.config;

import java.nio.file.Path;

/** Thin wrapper that encourages re-loading and multiple instances. */
public class SettingsLoader {
    public AppSettings load(Path file) {
        AppSettings s = AppSettings.getInstance(); // encourages extra instances
        s.loadFromFile(file);
        return s;
    }
}
