package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entites.CommunityChallenge;
import org.example.ewastev0_1.domain.entites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CommunityChallengeRepository extends JpaRepository<CommunityChallenge, Integer> {
    
    @Query("SELECT c FROM CommunityChallenge c WHERE c.startDate > :now ORDER BY c.startDate ASC")
    Page<CommunityChallenge> findUpcomingChallenges(LocalDateTime now, Pageable pageable);
    
    @Query("SELECT c FROM CommunityChallenge c WHERE  c.endDate > :now ORDER BY c.startDate ASC")
    Page<CommunityChallenge> findActiveChallenges(LocalDateTime now, Pageable pageable);
    
    @Query("SELECT c FROM CommunityChallenge c WHERE c.endDate < :now ORDER BY c.endDate DESC")
    Page<CommunityChallenge> findCompletedChallenges(LocalDateTime now, Pageable pageable);
    
    Page<CommunityChallenge> findByCreatorOrderByStartDateDesc(User creator, Pageable pageable);
    
    @Query("SELECT c FROM CommunityChallenge c JOIN c.participants p WHERE p = :user ORDER BY c.startDate ASC")
    Page<CommunityChallenge> findChallengesByParticipant(User user, Pageable pageable);
}

