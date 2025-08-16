package kr.gravy.gravy.common.utils;

import lombok.experimental.UtilityClass;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

@UtilityClass
public class GeneratorUtil {

    private final int VERIFICATION_CODE_LENGTH = 6;

    public static String generateVerificationCode() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            StringBuilder code = new StringBuilder();

            for (int i = 0; i < VERIFICATION_CODE_LENGTH; i++) {
                code.append(secureRandom.nextInt(10));
            }

            return code.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static UUID generatePublicId() {
        return UUID.randomUUID();
    }
}
