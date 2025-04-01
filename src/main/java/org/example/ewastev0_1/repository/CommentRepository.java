package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entites.Comment;
import org.example.ewastev0_1.domain.entites.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    
    Page<Comment> findByPostOrderByCreatedAtDesc(CommunityPost post, Pageable pageable);
    
    long countByPost(CommunityPost post);
}

