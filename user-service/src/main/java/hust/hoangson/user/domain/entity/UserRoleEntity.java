package hust.hoangson.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserProfileEntity userProfile;

    @Column(name = "role", nullable = false)
    private String role;

    @CreationTimestamp
    @Column(name = "assigned_at", updatable = false)
    private LocalDateTime assignedAt;
}
