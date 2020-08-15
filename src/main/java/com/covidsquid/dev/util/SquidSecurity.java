package com.covidsquid.dev.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SquidSecurity {

  public static byte[] getHashedAndSaltedPassword(String password) {
    try {
      return createFactory().generateSecret(createKeySpec(password)).getEncoded();
    } catch (Exception e) {
      return null;
    }
  }

  private static byte[] salt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }

  private static PBEKeySpec createKeySpec(String password) {
    return new PBEKeySpec(password.toCharArray(), salt(), 65536, 128);
  }

  private static SecretKeyFactory createFactory() throws NoSuchAlgorithmException {
    return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
  }
}
