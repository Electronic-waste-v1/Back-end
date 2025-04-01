package org.example.ewastev0_1.service;


import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.domain.entites.UserPoints;
import org.example.ewastev0_1.repository.UserPointsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserPointsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserPointsRepository userPointsRepository;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {

        user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password");
        user1.setRole("USER");
        entityManager.persist(user1);

        user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password");
        user2.setRole("USER");
        entityManager.persist(user2);

        user3 = new User();
        user3.setUsername("user3");
        user3.setEmail("user3@example.com");
        user3.setPassword("password");
        user3.setRole("USER");
        entityManager.persist(user3);


        UserPoints userPoints1 = new UserPoints();
        userPoints1.setUser(user1);
        userPoints1.setPointsTotal(100);
        userPoints1.setPointsUtilises(20);
        entityManager.persist(userPoints1);

        UserPoints userPoints2 = new UserPoints();
        userPoints2.setUser(user2);
        userPoints2.setPointsTotal(50);
        userPoints2.setPointsUtilises(10);
        entityManager.persist(userPoints2);

        UserPoints userPoints3 = new UserPoints();
        userPoints3.setUser(user3);
        userPoints3.setPointsTotal(150);
        userPoints3.setPointsUtilises(30);
        entityManager.persist(userPoints3);

        entityManager.flush();
    }

    @Test
    @DisplayName("findByUserId should return UserPoints for a given userId")
    void findByUserId_ShouldReturnUserPoints_ForGivenUserId() {

        Optional<UserPoints> result = userPointsRepository.findByUserId(user1.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getUser().getId()).isEqualTo(user1.getId());
        assertThat(result.get().getPointsTotal()).isEqualTo(100);
        assertThat(result.get().getPointsUtilises()).isEqualTo(20);
    }

    @Test
    @DisplayName("findByUserId should return empty optional for non-existent userId")
    void findByUserId_ShouldReturnEmptyOptional_ForNonExistentUserId() {

        Optional<UserPoints> result = userPointsRepository.findByUserId(999);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("countByPointsTotalGreaterThan should return correct count")
    void countByPointsTotalGreaterThan_ShouldReturnCorrectCount() {

        long count = userPointsRepository.countByPointsTotalGreaterThan(75);


        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("findTop10ByOrderByPointsTotalDesc should return users ordered by points")
    void findTop10ByOrderByPointsTotalDesc_ShouldReturnUsersOrderedByPoints() {

        List<UserPoints> topUsers = userPointsRepository.findTop10ByOrderByPointsTotalDesc();


        assertThat(topUsers).hasSize(3);
        assertThat(topUsers.get(0).getPointsTotal()).isEqualTo(150); // user3
        assertThat(topUsers.get(1).getPointsTotal()).isEqualTo(100); // user1
        assertThat(topUsers.get(2).getPointsTotal()).isEqualTo(50);  // user2
    }
}