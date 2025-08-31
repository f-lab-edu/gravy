package kr.gravy.gravy.common.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class GeneratorUtil {

    public UUID generatePublicId() {
        return UUID.randomUUID();
    }
}
