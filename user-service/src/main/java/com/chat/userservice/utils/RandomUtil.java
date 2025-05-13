package com.chat.userservice.utils;

import java.util.random.RandomGenerator;

public class RandomUtil {
    public static String getCode() {
        return String.valueOf(RandomGenerator.getDefault().nextInt(100_000, 1_000_000));
    }
}
