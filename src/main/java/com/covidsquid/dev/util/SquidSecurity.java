package com.covidsquid.dev.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SquidSecurity {

  private static int STRENGTH = 10;

  public static String getHashedAndSaltedPassword(String password) {
    BCryptPasswordEncoder bCryptPasswordEncoder =
      new BCryptPasswordEncoder(STRENGTH, new SecureRandom());
    return bCryptPasswordEncoder.encode(password);
  }

  public static boolean checkPW(String plaintext, String hashed) {
    return BCrypt.checkpw(plaintext, hashed);
  }

  @Deprecated
  public static byte[] getHashedAndSaltedPasswordOld(String password, byte[] salt) {
    try {
      return createFactory().generateSecret(createKeySpec(password, salt)).getEncoded();
    } catch (Exception e) {
      return null;
    }
  }

  public static byte[] salt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }

  private static PBEKeySpec createKeySpec(String password, byte[] salt) {
    return new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
  }

  private static SecretKeyFactory createFactory() throws NoSuchAlgorithmException {
    return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
  }
}
