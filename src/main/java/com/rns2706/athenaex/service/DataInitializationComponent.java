package com.rns2706.athenaex.service;

import com.rns2706.athenaex.domain.UserRole;
import com.rns2706.athenaex.model.User;
import com.rns2706.athenaex.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepo userRepo;


    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializationComponent(UserRepo userRepo,
                                       PasswordEncoder passwordEncoder
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder=passwordEncoder;

    }

    @Override
    public void run(String... args) {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        String adminUsername = "Admin Mail";

        if (userRepo.findByEmail(adminUsername)==null) {
            User adminUser = new User();

            adminUser.setPassword(passwordEncoder.encode("Admin Password"));
            adminUser.setName("AthenaEx Admin");
            adminUser.setEmail(adminUsername);
            adminUser.setRole(UserRole.ROLE_ADMIN);
            User admin=userRepo.save(adminUser);
        }
    }

}
