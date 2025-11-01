package fcc.sportsstore.utils;

import java.security.SecureRandom;

public class RandomUtil {

    public String randString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }

    public String randToken(String name) {
        TimeUtil time = new TimeUtil();

        return String.format("TOKEN-%s-%d-%s",
                name.toUpperCase(),
                time.getCurrentTimestamp(),
                randString(500));
    }

    public String randCode(String name) {
        TimeUtil time = new TimeUtil();

        return String.format("CODE-%s-%d-%s",
                name.toUpperCase(),
                time.getCurrentTimestamp(),
                randString(100));
    }
}
