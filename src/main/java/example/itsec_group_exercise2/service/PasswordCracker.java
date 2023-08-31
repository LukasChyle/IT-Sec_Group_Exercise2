package example.itsec_group_exercise2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PasswordCracker {
    private final ConnecterToLogin connecterToLogin;
    public String result;
    private volatile boolean passwordFound = false;
    private long startTime;

    public String password;


    @Autowired
    public PasswordCracker(ConnecterToLogin connecterToLogin) {
        this.connecterToLogin = connecterToLogin;
    }

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public void bruteForce(int length) {
        startTime = System.currentTimeMillis();
        try {
            StringBuilder current = new StringBuilder();
            generateCombinations(ALPHABET, length, current, 0);
        } catch (Exception e) {
            log.info("An exception occurred: " + e.getMessage());
        }
    }

    public void generateCombinations(String alphabet, int length, StringBuilder current, int pos) {
        if (passwordFound) {
            return;
        }

        if (length == 0) {
            result = connecterToLogin.tryPasswords(current.toString());
            if ("http://localhost:9050/".equals(result)) {
                long endTime = System.currentTimeMillis();
                long elapsed = endTime - startTime;
                passwordFound = true;
                this.password = String.valueOf(current);
                log.info("Password found: " + current);
                log.info("Password found in: " + elapsed + " ms");
            }
            return;
        }

        int currentLength = current.length();
        for (int i = pos; i < alphabet.length() && !passwordFound; i++) {
            current.append(alphabet.charAt(i));
            generateCombinations(alphabet, length - 1, current, 0);
            current.setLength(currentLength);
        }
    }
}




