package vn.iotstar.config;
import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import vn.iotstar.entity.*; import vn.iotstar.repository.*;
@Configuration public class DataInitializer{
 @Bean CommandLineRunner initData(RoleRepository roles,UserRepository users,PasswordEncoder encoder){return args->{Role ur=roles.findByNameIgnoreCase("ROLE_USER").orElseGet(()->roles.save(new Role(null,"ROLE_USER"))); Role ar=roles.findByNameIgnoreCase("ROLE_ADMIN").orElseGet(()->roles.save(new Role(null,"ROLE_ADMIN")));
 if(!users.existsByUsernameIgnoreCase("admin01")){users.save(User.builder().username("admin01").email("admin@hcmute.edu.vn").fullName("Administrator").password(encoder.encode("123456")).images("/images/avatar-default.png").role(ar).enabled(true).build());}
 if(!users.existsByUsernameIgnoreCase("user01")){users.save(User.builder().username("user01").email("user01@gmail.com").fullName("Nguyễn Hữu Trung").password(encoder.encode("123456")).images("/images/user.png").role(ur).enabled(true).build());}
 };}
}
