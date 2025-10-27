package hust.hoangson.order.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {
    UNPAID(0), PAID(1), REFUNDED(2);

    private final int code;

    public static PaymentStatus fromCode(int code) {
        for (PaymentStatus s : values()) {
            if (s.code == code) return s;
        }
        return null;
    }
}