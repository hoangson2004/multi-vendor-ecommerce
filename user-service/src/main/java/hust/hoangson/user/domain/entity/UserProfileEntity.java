package hust.hoangson.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileEntity {
    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "user_id", unique = true,  nullable = false)
    private String userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email",  unique = true, nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "userProfile")
    private List<UserRoleEntity> roles;

    @OneToMany(mappedBy = "userProfile")
    private List<UserAddressEntity> addresses;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
