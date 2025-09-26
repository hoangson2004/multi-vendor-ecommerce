package hust.hoangson.order.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING(0), CONFIRMED(1), SHIPPING(2), COMPLETED(3), CANCELLED(4);

    private final int code;

    public static OrderStatus fromCode(int code) {
        for (OrderStatus s : values()) {
            if (s.code == code) return s;
        }
        return null;
    }
}
