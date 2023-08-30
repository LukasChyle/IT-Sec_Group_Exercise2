package example.itsec_group_exercise2.controller;

import example.itsec_group_exercise2.service.ConnecterToLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;


@RestController
@RequestMapping("/bruteforce")
@Slf4j
public class BruteForceController {

    ConnecterToLogin connecterToLogin;

    @Autowired
    public BruteForceController(ConnecterToLogin connecterToLogin) {
        this.connecterToLogin = connecterToLogin;
    }

    @GetMapping
    public String getPassword() {
        log.info("Get password called");
        String response = null;
        while (!Objects.equals(response, "http://localhost:9050/")) {
            for (int i = 150; i < 160; i++) {
                response = connecterToLogin.tryPasswords(String.valueOf(i));
                if (Objects.equals(response,"http://localhost:9050/")){
                    log.warn("Password cracked with password = " + i);
                }
            }
        }
        return response;
    }

    @GetMapping("/dictionary")
    public String getPassword2() {
        return "password";
    }
}
