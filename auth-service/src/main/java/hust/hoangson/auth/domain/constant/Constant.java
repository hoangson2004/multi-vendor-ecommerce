package hust.hoangson.auth.domain.constant;

public class Constant {
    public static class Error {
        public static final String USER_NOT_FOUND = "User Not Found";
        public static final String PASSWORD_MISMATCH = "Password Mismatch";
        public static final String USER_ALREADY_EXISTS = "User Already Exists";
    }

    public static class ErrorCode {
        public static final int NOT_FOUND = 404;
        public static final int PASSWORD_MISMATCH = 400;
        public static final int USER_ALREADY_EXISTS = 400;
    }
}
