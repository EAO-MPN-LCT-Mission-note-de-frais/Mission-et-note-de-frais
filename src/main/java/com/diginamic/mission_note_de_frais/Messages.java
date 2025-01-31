package com.diginamic.mission_note_de_frais;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Messages {
    private final MessageSource source;

    public String get(String key) {
        return source.getMessage(key, null, Locale.getDefault());
    }

    public String format(String key, Object... args) {
        return source.getMessage(key, args, Locale.getDefault());
    }
}