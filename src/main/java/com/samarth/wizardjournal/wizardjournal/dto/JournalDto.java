package com.samarth.wizardjournal.wizardjournal.dto;

import com.samarth.wizardjournal.wizardjournal.models.Journal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JournalDto {
    private String title;
    private String content;
    private Long date;
    private Long modifiedTimestamp;
    private JournalTheme theme;
    private BackgroundInfo backgroundInfo;
    private Integer id;

    public static JournalDto fromJournal(Journal journal) {
        return new JournalDto(
            journal.getTitle(),
            journal.getContent(),
            journal.getDate(),
            journal.getModifiedTimestamp(),
            journal.getTheme(),
            journal.getBackgroundInfo(),
            journal.getId()
        );
    }
}
