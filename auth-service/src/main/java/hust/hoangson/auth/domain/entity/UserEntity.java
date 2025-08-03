package hust.hoangson.auth.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "locked_reason")
    private String lockedReason;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    @Column(name = "is_banned")
    private Boolean isBanned;
}