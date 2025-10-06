package hust.hoangson.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .code(0)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> error(int code, String message) {
        return BaseResponse.<T>builder()
                .code(code)
                .message(message)
                .build();
    }

    public boolean isSuccess() {
        return this.code == 0;
    }

}
