package vn.iotstar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roles,
            UserRepository users,
            PasswordEncoder encoder,
            @Value("${ADMIN_EMAIL:thanhthao@hcmute.edu.vn}") String adminEmail,
            @Value("${ADMIN_PASSWORD:123456}") String adminPassword) {
        return args -> {
            Role userRole = roles.findByNameIgnoreCase("USER")
                    .orElseGet(() -> roles.save(new Role("USER")));

            Role adminRole = roles.findByNameIgnoreCase("ADMIN")
                    .orElseGet(() -> roles.save(new Role("ADMIN")));

            if (!users.existsByEmailIgnoreCase(adminEmail)) {
                User admin = new User();
                admin.setEmail(adminEmail.toLowerCase());
                admin.setFullName("Trần Thanh Thảo");
                admin.setPassword(encoder.encode(adminPassword));
                admin.setRole(adminRole);
                admin.setEnabled(true);
                users.save(admin);
            }

            String userEmail = "user@hcmute.edu.vn";
            if (!users.existsByEmailIgnoreCase(userEmail)) {
                User user = new User();
                user.setEmail(userEmail.toLowerCase());
                user.setFullName("Sinh Viên Demo");
                user.setPassword(encoder.encode("123456"));
                user.setRole(userRole);
                user.setEnabled(true);
                users.save(user);
            }
        };
    }
}
