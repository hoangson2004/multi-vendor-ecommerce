package hust.hoangson.auth.exception;

import javax.security.auth.login.AccountException;

import static hust.hoangson.auth.domain.constant.AccountError.*;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(Message.USER_NOT_FOUND, Code.USER_NOT_FOUND);
    }
}