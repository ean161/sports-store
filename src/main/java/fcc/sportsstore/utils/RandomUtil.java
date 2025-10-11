package fcc.sportsstore.utils;

import java.security.SecureRandom;

public class RandomUtil {

    /**
     * Random a string
     * @param length Length of result
     * @return Random string
     */
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

    /**
     * Random new id
     * @return New id
     */
    public String randId(String name) {
        TimeUtil time = new TimeUtil();

        return String.format("%d.ID{%s}.%s",
                time.getCurrentTimestamp(),
                name.toUpperCase(),
                randString(10));
    }

    /**
     * Random new user token
     * @return New user token
     */
    public String randToken(String name) {
        TimeUtil time = new TimeUtil();

        return String.format("%d.TOKEN{%s}.%s",
                time.getCurrentTimestamp(),
                name.toUpperCase(),
                randString(500));
    }

    /**
     * Random new code
     * @return New code
     */
    public String randCode(String name) {
        TimeUtil time = new TimeUtil();

        return String.format("%d.CODE{%s}.%s",
                time.getCurrentTimestamp(),
                name.toUpperCase(),
                randString(100));
    }
}
