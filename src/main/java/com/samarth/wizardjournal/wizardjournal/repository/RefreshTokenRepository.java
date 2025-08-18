package com.samarth.wizardjournal.wizardjournal.repository;

import com.samarth.wizardjournal.wizardjournal.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    List<RefreshToken> findByUserIdAndRevokedFalse(Integer userId);
    Optional<RefreshToken> findByUserIdAndTokenAndRevokedFalse(Integer userId, String token);
}
