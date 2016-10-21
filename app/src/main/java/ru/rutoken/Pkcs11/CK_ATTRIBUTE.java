/*
 * Copyright (c) 2016, CJSC Aktiv-Soft. See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
/* Copyright  (c) 2002 Graz University of Technology. All rights reserved.
 *
 * Redistribution and use in  source and binary forms, with or without
 * modification, are permitted  provided that the following conditions are met:
 *
 * 1. Redistributions of  source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in  binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. The end-user documentation included with the redistribution, if any, must
 *    include the following acknowledgment:
 *
 *    "This product includes software developed by IAIK of Graz University of
 *     Technology."
 *
 *    Alternately, this acknowledgment may appear in the software itself, if
 *    and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Graz University of Technology" and "IAIK of Graz University of
 *    Technology" must not be used to endorse or promote products derived from
 *    this software without prior written permission.
 *
 * 5. Products derived from this software may not be called
 *    "IAIK PKCS Wrapper", nor may "IAIK" appear in their name, without prior
 *    written permission of Graz University of Technology.
 *
 *  THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE LICENSOR BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 *  OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *  OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 *  OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY  OF SUCH DAMAGE.
 */

package ru.rutoken.Pkcs11;

/**
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schlaeffer <schlaeff@sbox.tugraz.at>
 * @author Aktiv Co. <hotline@rutoken.ru>
 */

import com.sun.jna.*;

import java.util.Arrays;
import java.util.List;

public class CK_ATTRIBUTE extends Pkcs11Structure {

    public CK_ATTRIBUTE() {}

    public CK_ATTRIBUTE(NativeLong type, Pointer pVal, NativeLong ulValLen) {
        setAttr(type, pVal, ulValLen);
    }

    public CK_ATTRIBUTE(NativeLong type, NativeLong value) {
        setAttr(type, value);
    }

    public CK_ATTRIBUTE(NativeLong type, byte[] value) {
        setAttr(type, value);
    }

    public CK_ATTRIBUTE(NativeLong type, boolean value) {
        setAttr(type, value);
    }

    public CK_ATTRIBUTE(NativeLong type, String value) {
        setAttr(type, value);
    }

    public void setAttr(NativeLong type, Pointer pVal, NativeLong ulValLen) {
        this.type = type;
        this.pValue = pVal;
        this.ulValueLen = ulValLen;
    }

    public void setAttr(NativeLong type, NativeLong value) {
        this.type = type;
        pValue = new Memory(NativeLong.SIZE);
        pValue.setNativeLong(0, value);
        ulValueLen = new NativeLong(NativeLong.SIZE);
    }

    public void setAttr(NativeLong type, byte[] value) {
        this.type = type;
        pValue = new Memory(value.length);
        pValue.write(0, value, 0, value.length);
        ulValueLen = new NativeLong(value.length);
    }

    public void setAttr(NativeLong type, boolean value) {
        this.type = type;
        pValue = new Memory(Native.getNativeSize(Byte.TYPE));
        pValue.setByte(0, (byte)(value ? 1 : 0));
        ulValueLen = new NativeLong(Native.getNativeSize(Byte.TYPE));
    }

    public void setAttr(NativeLong type, String value) {
        this.type = type;
        pValue = new Memory(value.length() + 1);
        pValue.setString(0, value);
        ulValueLen = new NativeLong(value.length() + 1);
    }

    public NativeLong type;

    public Pointer pValue;

    /* ulValueLen went from CK_USHORT to CK_ULONG for v2.0 */
    public NativeLong ulValueLen; /* in bytes */

    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[] {
                "type",
                "pValue",
                "ulValueLen"
        });
    }
}
