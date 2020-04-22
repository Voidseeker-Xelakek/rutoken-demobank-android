/*
 * Copyright (c) 2018, JSC Aktiv-Soft. See https://download.rutoken.ru/License_Agreement.pdf
 * All Rights Reserved.
 */

package ru.rutoken.demobank;

import androidx.annotation.Nullable;

import ru.rutoken.demobank.nfc.NfcDetectCardFragment;
import ru.rutoken.pkcs11caller.Pkcs11Callback;
import ru.rutoken.pkcs11caller.Token;
import ru.rutoken.pkcs11caller.exception.Pkcs11CallerException;
import ru.rutoken.pkcs11caller.exception.Pkcs11Exception;

abstract public class Pkcs11CallerActivity extends ManagedActivity {
    @Nullable
    private static Pkcs11Exception pkcs11exceptionFromCallerException(Pkcs11CallerException exception) {
        Pkcs11Exception exception1 = null;
        if (Pkcs11Exception.class.isInstance(exception)) {
            exception1 = ((Pkcs11Exception) exception);
        }
        return exception1;
    }

    class LoginCallback extends Pkcs11Callback {
        public void execute(Pkcs11CallerException exception) {
            manageLoginError(pkcs11exceptionFromCallerException(exception));
        }

        public void execute(Object... arguments) {
            Pkcs11CallerActivity.this.manageLoginSucceed();
        }
    }

    class SignCallback extends Pkcs11Callback {
        public void execute(Pkcs11CallerException exception) {
            manageSignError(pkcs11exceptionFromCallerException(exception));
        }

        public void execute(Object... arguments) {
            manageSignSucceed((byte[]) arguments[0]);
        }
    }

    final LoginCallback mLoginCallback = new LoginCallback();
    final SignCallback mSignCallback = new SignCallback();
    
    protected void sign(Token token, String certificate, byte[] signData) {
        token.sign(certificate, signData, mSignCallback, getNfcCardFragment().new Control(getSupportFragmentManager()));
    }

    protected void login(Token token, String pin) {
        token.login(pin, mLoginCallback, getNfcCardFragment().new Control(getSupportFragmentManager()));
    }

    abstract protected NfcDetectCardFragment getNfcCardFragment();

    abstract protected void manageLoginError(@Nullable Pkcs11Exception exception);

    abstract protected void manageLoginSucceed();

    abstract protected void manageSignError(@Nullable Pkcs11Exception exception);

    abstract protected void manageSignSucceed(byte[] data);
}
