package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entites.CommunityPost;
import org.example.ewastev0_1.domain.entites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Integer> {

    Page<CommunityPost> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<CommunityPost> findByAuthorOrderByCreatedAtDesc(User author, Pageable pageable);

    Page<CommunityPost> findAll(Pageable pageable);

    @Query("SELECT p FROM CommunityPost p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    Page<CommunityPost> searchPosts(String keyword, Pageable pageable);


    @Query("SELECT p FROM CommunityPost p JOIN p.tags t WHERE t = :tag")
    Page<CommunityPost> findByTag(String tag, Pageable pageable);

    @Query("SELECT p FROM CommunityPost p ORDER BY p.likesCount DESC")
    Page<CommunityPost> findMostLikedPosts(Pageable pageable);

    @Query("SELECT p FROM CommunityPost p ORDER BY p.commentsCount DESC")
    Page<CommunityPost> findMostCommentedPosts(Pageable pageable);
}

