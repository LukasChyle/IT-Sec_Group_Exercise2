package example.itsec_group_exercise2.controller;

import example.itsec_group_exercise2.service.ConnecterToLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;


@RestController
@RequestMapping("/bruteforce")
public class BruteForceController {

    ConnecterToLogin connecterToLogin;

    @Autowired
    public BruteForceController(ConnecterToLogin connecterToLogin) {
        this.connecterToLogin = connecterToLogin;
    }

    @GetMapping
    public String getPassword() {

        String response = null;

        while (!Objects.equals(response, "http://localhost:9050/")) {

        System.out.println("connector called");
            for (int i = 0; i < 160; i++) {
                response = connecterToLogin.tryPasswords(String.valueOf(i));
                System.out.println("test nr " + i);
            }
        }
        return response;
    }

    @GetMapping("/dictionary")
    public String getPassword2() {
        return "password";
    }
}
