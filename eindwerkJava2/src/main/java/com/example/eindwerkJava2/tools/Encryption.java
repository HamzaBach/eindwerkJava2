package com.example.eindwerkJava2.tools;

public interface Encryption {
void setKey(String myKey);
String encrypt(String strToEncrypt, String secret);
String decrypt(String strToDecrypt, String secret);
}
