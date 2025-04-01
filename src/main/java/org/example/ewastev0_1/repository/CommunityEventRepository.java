package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entities.CommunityEvent;
import org.example.ewastev0_1.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommunityEventRepository extends JpaRepository<CommunityEvent, Integer> {
    
    @Query("SELECT e FROM CommunityEvent e WHERE e.startDate > :now ORDER BY e.startDate ASC")
    Page<CommunityEvent> findUpcomingEvents(LocalDateTime now, Pageable pageable);
    
    @Query("SELECT e FROM CommunityEvent e WHERE e.startDate < :now AND e.endDate > :now ORDER BY e.startDate ASC")
    Page<CommunityEvent> findOngoingEvents(LocalDateTime now, Pageable pageable);
    
    @Query("SELECT e FROM CommunityEvent e WHERE e.endDate < :now ORDER BY e.endDate DESC")
    Page<CommunityEvent> findPastEvents(LocalDateTime now, Pageable pageable);
    
    Page<CommunityEvent> findByOrganizerOrderByStartDateDesc(User organizer, Pageable pageable);
    
    @Query("SELECT e FROM CommunityEvent e JOIN e.attendees a WHERE a = :user ORDER BY e.startDate ASC")
    Page<CommunityEvent> findEventsByAttendee(User user, Pageable pageable);
}

