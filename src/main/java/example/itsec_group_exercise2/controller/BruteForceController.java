package example.itsec_group_exercise2.controller;

import example.itsec_group_exercise2.service.ConnecterToLogin;
import example.itsec_group_exercise2.service.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        PasswordGenerator generator = new PasswordGenerator(connecterToLogin);

        return generator.get();
    }

    @GetMapping("/dictionary")
    public String getPassword2() {
        return "password";
    }

}
