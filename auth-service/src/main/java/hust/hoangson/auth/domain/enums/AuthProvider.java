package hust.hoangson.auth.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthProvider {
    LOCAL("1"),
    GOOGLE("2"),
    FACEBOOK("3");

    private final String code;

    public static AuthProvider fromCode(String code) {
        for (AuthProvider r : values()) {
            if (r.getCode().equalsIgnoreCase(code)) {
                return r;
            }
        }
        return null;
    }
}