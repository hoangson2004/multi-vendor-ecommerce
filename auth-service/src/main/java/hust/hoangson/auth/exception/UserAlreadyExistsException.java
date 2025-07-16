package hust.hoangson.auth.exception;

import hust.hoangson.auth.domain.constant.AccountError;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException() {
        super(AccountError.Message.USER_ALREADY_EXISTS, AccountError.Code.USER_ALREADY_EXISTS);
    }
}
