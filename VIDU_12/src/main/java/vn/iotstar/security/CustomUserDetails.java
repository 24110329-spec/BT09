package vn.iotstar.security;
import lombok.Getter; import org.springframework.security.core.*; import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.userdetails.UserDetails; import vn.iotstar.entity.User; import java.util.List;
@Getter public class CustomUserDetails implements UserDetails{
 private final Long id; private final String username; private final String email; private final String password; private final String fullName; private final String images; private final String role; private final boolean enabled;
 public CustomUserDetails(User u){id=u.getId();username=u.getUsername();email=u.getEmail();password=u.getPassword();fullName=u.getFullName();images=u.getImages();role=u.getRole().getName();enabled=u.isEnabled();}
 public java.util.Collection<? extends GrantedAuthority> getAuthorities(){return List.of(new SimpleGrantedAuthority(role.startsWith("ROLE_")?role:"ROLE_"+role));}
 public String getPassword(){return password;} public String getUsername(){return username;} public boolean isAccountNonExpired(){return true;} public boolean isAccountNonLocked(){return true;} public boolean isCredentialsNonExpired(){return true;} public boolean isEnabled(){return enabled;}
}
