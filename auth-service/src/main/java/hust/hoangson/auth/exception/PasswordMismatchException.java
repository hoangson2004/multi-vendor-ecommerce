package hust.hoangson.auth.exception;

import hust.hoangson.auth.domain.constant.AccountError;

public class PasswordMismatchException extends BaseException {
    public PasswordMismatchException() {
        super(AccountError.Message.PASSWORD_MISMATCH, AccountError.Code.PASSWORD_MISMATCH);
    }
}
