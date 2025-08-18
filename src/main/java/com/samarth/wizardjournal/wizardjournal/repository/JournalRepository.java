package com.samarth.wizardjournal.wizardjournal.repository;

import com.samarth.wizardjournal.wizardjournal.models.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Integer> {
    @Query("SELECT j FROM Journal j WHERE j.user.id = ?1")
    List<Journal> findAllForUserId(Integer userId);
}
