package hust.hoangson.product.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OwnerType {

    CATEGORY("CATEGORY"),
    CATALOG("CATALOG"),
    VENDOR_PRODUCT("VENDOR_PRODUCT"),
    VARIANT("VARIANT");

    private final String type;

}
