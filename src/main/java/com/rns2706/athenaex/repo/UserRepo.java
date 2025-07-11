package com.rns2706.athenaex.repo;

import com.rns2706.athenaex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
