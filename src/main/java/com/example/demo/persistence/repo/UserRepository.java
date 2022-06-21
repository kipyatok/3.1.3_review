package com.example.demo.persistence.repo;

import com.example.demo.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u join fetch u.roles where u.username = (:username)")
    UserEntity getUserByUsername(String username);

    UserEntity findUserById(Long id);

    // to do example use Optional
    Optional<UserEntity> findById(long id);

    Optional<UserEntity> findByUsername(String username);
}
