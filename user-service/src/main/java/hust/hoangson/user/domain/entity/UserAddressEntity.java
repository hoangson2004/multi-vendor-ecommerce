package hust.hoangson.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserProfileEntity userProfile;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @Column(name = "ward_code")
    private String wardCode;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "province_code")
    private String provinceCode;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "is_active")
    private Boolean isActive;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
