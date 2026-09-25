package vn.iotstar.repository;
import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import vn.iotstar.entity.User; import java.util.Optional;
@Repository public interface UserRepository extends JpaRepository<User,Long>{
 Optional<User> findByUsernameIgnoreCase(String username); Optional<User> findByEmailIgnoreCase(String email);
 Optional<User> findByUsernameOrEmail(String username,String email);
 boolean existsByUsernameIgnoreCase(String username); boolean existsByEmailIgnoreCase(String email);
 Page<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(String a,String b,String c,Pageable pageable);
 @Query("select u from User u left join fetch u.role where lower(u.username)=lower(:login) or lower(u.email)=lower(:login)") Optional<User> findByUsernameOrEmailWithRole(@Param("login") String login);
}
