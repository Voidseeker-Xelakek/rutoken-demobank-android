package ru.rutoken.pkcs11caller.digest;

import java.security.InvalidParameterException;

import ru.rutoken.pkcs11caller.RtPkcs11Library;
import ru.rutoken.pkcs11caller.exception.Pkcs11Exception;

public interface Digest {
    enum Type {
        GOSTR3411_1994,
        GOSTR3411_2012_256,
        GOSTR3411_2012_512
    }

    static Digest getInstance(Digest.Type type, long sessionHandle) {
        switch (type) {
            case GOSTR3411_1994:
                return new GostR3411_1994Digest(RtPkcs11Library.getInstance(), sessionHandle);
            case GOSTR3411_2012_256:
                return new GostR3411_2012_256Digest(RtPkcs11Library.getInstance(), sessionHandle);
            case GOSTR3411_2012_512:
                return new GostR3411_2012_512Digest(RtPkcs11Library.getInstance(), sessionHandle);
            default:
                throw new InvalidParameterException();
        }
    }

    byte[] digest(final byte[] data) throws Pkcs11Exception;

    Type getType();
}
