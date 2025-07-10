package hust.hoangson.auth.domain.enums;

import lombok.*;

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("1"),
    USER("2"),
    SELLER("3");

    private final String code;

    public static Role fromCode(String code) {
        for (Role r : values()) {
            if (r.getCode().equalsIgnoreCase(code)) {
                return r;
            }
        }
        return null;
    }
}
