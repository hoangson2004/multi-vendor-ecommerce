package hust.hoangson.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userRef;

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

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @CreationTimestamp
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updatedAt;
}
