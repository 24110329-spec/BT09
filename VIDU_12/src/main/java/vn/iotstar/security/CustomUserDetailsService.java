package vn.iotstar.security;
import lombok.RequiredArgsConstructor; import org.springframework.security.core.userdetails.*; import org.springframework.stereotype.Service; import vn.iotstar.entity.User; import vn.iotstar.repository.UserRepository;
@Service @RequiredArgsConstructor public class CustomUserDetailsService implements UserDetailsService{
 private final UserRepository userRepository;
 public UserDetails loadUserByUsername(String login){ User u=userRepository.findByUsernameOrEmailWithRole(login).orElseThrow(()->new UsernameNotFoundException("Không tìm thấy username/email: "+login)); return new CustomUserDetails(u); }
}
