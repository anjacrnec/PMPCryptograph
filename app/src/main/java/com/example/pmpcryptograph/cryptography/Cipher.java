package com.example.pmpcryptograph.cryptography;

//abstract class for Cipher
public abstract class Cipher {

    //all ciphers have plaintext and ciphertext of type String
    protected String plainText;
    protected String cipherText;

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }


}
