package kr.co.bestiansoft.ebillservicekg.common.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordUtill {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUM = "0123456789";
    private static final String SYMBOL = "!@#$%^&*";
    private static final String ALL =  LOWER + NUM + SYMBOL;

    //password reset : Special character + English + number 8seat generation
    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        List<Character> chars = new ArrayList<>();
        chars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        chars.add(NUM.charAt(random.nextInt(NUM.length())));
        chars.add(SYMBOL.charAt(random.nextInt(SYMBOL.length())));

        for(int i=0; i < 5; i++) {
            chars.add(ALL.charAt(random.nextInt(ALL.length())));
        }

        Collections.shuffle(chars, random);

        StringBuilder sb = new StringBuilder();
        for (char c : chars) sb.append(c);
        return sb.toString();
    }
}
