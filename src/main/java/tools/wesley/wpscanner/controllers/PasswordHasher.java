package tools.wesley.wpscanner.controllers;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class PasswordHasher {
    private final SecretKeyFactory factory;
    private final byte[] salt = new byte[16];

    public PasswordHasher() {
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such hashing algorithm!", e);
        }
    }

    public String hash(String password) {
        try {
            var keySpec = new PBEKeySpec(password.toCharArray(), this.salt, 200000, 256);

            var hash = factory.generateSecret(keySpec).getEncoded();

            var hashHex = new StringBuilder();
            for (byte hashByte: hash) {
                hashHex.append(String.format("%02x", hashByte));
            }

            return hashHex.toString();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password!", e);
        }
    }
}