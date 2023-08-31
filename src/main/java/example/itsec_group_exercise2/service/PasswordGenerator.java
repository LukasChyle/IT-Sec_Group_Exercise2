package example.itsec_group_exercise2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PasswordGenerator {

    ConnecterToLogin connecterToLogin;

    @Autowired
    public PasswordGenerator(ConnecterToLogin connecterToLogin) {
        this.connecterToLogin = connecterToLogin;
    }

    private int tries;
    private String password;
    private final int min = 1, max = 5; // Length of password to bruteforce
//    private final char[] charset = "0123456789".toCharArray();
//    private final char[] charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final char[] charset = ("abcdefghijklmnopqrstuvwxyzAEIOU0123456789!@#$%^&*()-_+=~`[]{}|:;<>,.?/BCDFGHJKLMNPQRSTVWXYZ").toCharArray();

    public String get() {
        tries = 0;
        for (int length = min; length < max; length++){
            generate("", 0, length);
            if (password != null) {
                System.out.println("Password is [ " + password + " ] Tried " + tries + " combinations.");
                return "Password is [ " + password + " ] Tried " + tries + " combinations.";
            }
        }
        System.out.println("Password was not found. Tried " + tries + " combinations.");
        return  "Password was not found. Tried " + tries + " combinations.";
    }

    private void generate(String str, int pos, int length) {
        if (length == 0) {
            tries++;
            System.out.println(str);
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
