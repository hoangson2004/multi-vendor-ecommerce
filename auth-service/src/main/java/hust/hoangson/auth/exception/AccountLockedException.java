package hust.hoangson.auth.exception;

import hust.hoangson.auth.domain.constant.AccountError;

public class AccountLockedException extends BaseException {
    public AccountLockedException() {
        super(AccountError.Message.ACCOUNT_LOCKED, AccountError.Code.ACCOUNT_LOCKED);
    }
}
