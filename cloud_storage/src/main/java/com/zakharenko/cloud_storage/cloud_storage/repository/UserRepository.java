package com.zakharenko.cloud_storage.cloud_storage.repository;

import com.zakharenko.cloud_storage.cloud_storage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long id);

    Optional<User> findByLogin(String login);

    Optional<User> findByLoginAndPassword(String login, String password);

    boolean existsByLogin(String login);

    @Override
    List<User> findAll();


}
