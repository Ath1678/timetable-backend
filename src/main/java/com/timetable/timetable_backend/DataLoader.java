package com.timetable.timetable_backend;

import com.timetable.timetable_backend.entity.Role;
import com.timetable.timetable_backend.entity.User;
import com.timetable.timetable_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadAdmin(UserRepository repo,
                                PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
            	User user = new User();
            	user.setUsername("admin");
            	user.setPassword(encoder.encode("admin123"));
            	user.setRole(Role.ROLE_ADMIN);
            	user.setActive(true);    // ✅ (if you have active column)


                repo.save(user);
                System.out.println("✅ Admin user created");
            }
        };
    }
}
