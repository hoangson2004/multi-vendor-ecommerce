package hust.hoangson.auth.exception;

import hust.hoangson.auth.domain.constant.AccountError;

import javax.security.auth.login.AccountException;

public class AccountBannedException extends BaseException {
    public AccountBannedException() {
        super(AccountError.Message.ACCOUNT_BANNED, AccountError.Code.ACCOUNT_BANNED);
    }
}
