// ITelephony.aidl
package com.example.admin.scall.telephony;

// Declare any non-default types here with import statements

interface ITelephony {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
             boolean endCall();

                    void answerRingingCall();

                    void silenceRinger();
}
