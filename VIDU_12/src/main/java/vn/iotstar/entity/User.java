package vn.iotstar.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data @AllArgsConstructor @NoArgsConstructor @Builder
@Entity @Table(name="users", uniqueConstraints={@UniqueConstraint(name="uk_users_username",columnNames="username"),@UniqueConstraint(name="uk_users_email",columnNames="email")})
public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=50) private String username;
 @Column(nullable=false,unique=true,length=150) private String email;
 @Column(nullable=false,length=150) private String password;
 @Column(name="full_name",length=150,columnDefinition="nvarchar(200)") private String fullName;
 @Column(length=500) private String images;
 @Column(nullable=false) @Builder.Default private boolean enabled=true;
 @Column(nullable=false) @Builder.Default private LocalDateTime createdAt=LocalDateTime.now();
 @ManyToOne(fetch=FetchType.EAGER,optional=false) @JoinColumn(name="role_id",nullable=false) private Role role;
 @OneToMany(mappedBy="user",cascade=CascadeType.ALL,orphanRemoval=true) @ToString.Exclude @EqualsAndHashCode.Exclude @Builder.Default private List<Product> products=new ArrayList<>();
}
