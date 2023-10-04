package com.cqueltech.restapi.utils;

/*
 * A class to generate an RSA key pair to allow us to generate JWT tokens.
 * The RSA keys are used to encoode and decode JWT tokens.
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGeneratorUtility {
  
  // Generate a one time keypair.
  public static KeyPair generateRsaKey() {

    KeyPair keyPair;

    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (Exception e) {
      throw new IllegalStateException();
    }

    return keyPair;
  }
}
