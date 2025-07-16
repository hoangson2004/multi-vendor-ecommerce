package hust.hoangson.auth.domain.constant;

public class AccountError {
    public static class Message {
        public static final String USER_NOT_FOUND = "Tài khoản không tồn tại";
        public static final String PASSWORD_MISMATCH = "Sai mật khẩu";
        public static final String USER_ALREADY_EXISTS = "Tên đăng nhập hoặc email đã tồn tại";
        public static final String ACCOUNT_LOCKED = "Tài khoản đang bị khóa tạm thời";
        public static final String ACCOUNT_BANNED = "Tài khoản đã bị cấm";
    }

    public static class Code {
        public static final int USER_NOT_FOUND = 404;
        public static final int PASSWORD_MISMATCH = 400;
        public static final int USER_ALREADY_EXISTS = 400;
        public static final int ACCOUNT_LOCKED = 403;
        public static final int ACCOUNT_BANNED = 403;
    }
}
