package com.samarth.wizardjournal.wizardjournal.models;

import com.samarth.wizardjournal.wizardjournal.dto.BackgroundInfo;
import com.samarth.wizardjournal.wizardjournal.dto.JournalTheme;
import com.samarth.wizardjournal.wizardjournal.models.converters.BackgroundInfoConverter;
import com.samarth.wizardjournal.wizardjournal.models.converters.JournalThemeConverter;
import com.samarth.wizardjournal.wizardjournal.utils.StringifyConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "journals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Journal {
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long date;


    private Long modifiedTimestamp;

    @Convert(converter = JournalThemeConverter.class)
    @Column(columnDefinition = "TEXT")
    private JournalTheme theme;

    @Convert(converter = BackgroundInfoConverter.class)
    @Column(columnDefinition = "TEXT")
    private BackgroundInfo backgroundInfo;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        this.modifiedTimestamp = System.currentTimeMillis();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedTimestamp = System.currentTimeMillis();
    }

    public Journal(String title, String content, Long date, JournalTheme theme, BackgroundInfo backgroundInfo, User user) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.theme = theme;
        this.backgroundInfo = backgroundInfo;
        this.user = user;
    }
}
