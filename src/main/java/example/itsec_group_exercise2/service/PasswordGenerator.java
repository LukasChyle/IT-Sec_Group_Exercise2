package example.itsec_group_exercise2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PasswordGenerator {

    ConnecterToLogin connecterToLogin;
    private long startTime;

    @Autowired
    public PasswordGenerator(ConnecterToLogin connecterToLogin) {
        this.connecterToLogin = connecterToLogin;
    }

    private int logCounter;
    private int tries;
    private String password;
    private final int min = 3, max = 5; // Length of password to bruteforce
//    private final char[] charset = "0123456789".toCharArray();
    private final char[] charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
//    private final char[] charset = ("abcdefghijklmnopqrstuvwxyzAEIOU0123456789!@#$%^&*()-_+=~`[]{}|:;<>,.?/BCDFGHJKLMNPQRSTVWXYZ").toCharArray();

    public String get() {
        tries = 0;
        startTime = System.currentTimeMillis();
        logCounter = 0;
        password = null;
        for (int length = min; length < max; length++){
            generate("", 0, length);
            if (password != null) {
                long endTime = System.currentTimeMillis();
                double elapsed = (double) (endTime - startTime) /1000;
                log.info("Password is [ " + password + " ] Tried " + tries + " combinations. on time: " + elapsed + " sec");
                return "Password is [ " + password + " ] Tried " + tries + " combinations. on time: " + elapsed + " sec";
            }
        }
        log.info("Password was not found. Tried " + tries + " combinations.");
        return  "Password was not found. Tried " + tries + " combinations.";
    }

    private void generate(String str, int pos, int length) {
        if (length == 0) {
            tries++;
            logCounter++;
            if (logCounter > 9999){
                log.info("Current tries: " + tries);
                logCounter = 0;
            }

            String response = connecterToLogin.tryPasswords(str);
            if (response.equals("http://localhost:9050/")) {
                password = str;
            }
        } else {
            if (pos != 0) {
                pos = 0;
            }
            for (int i = pos; i < charset.length; i++) {
                generate(str + charset[i], i, length - 1);
                if (password != null) {
                    return;
                }
            }
        }
    }
}
