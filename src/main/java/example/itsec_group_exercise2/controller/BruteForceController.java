package example.itsec_group_exercise2.controller;

import example.itsec_group_exercise2.service.ConnecterToLogin;
import example.itsec_group_exercise2.service.PasswordCracker;
import example.itsec_group_exercise2.service.PasswordMostCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RestController
@RequestMapping("/bruteforce")
@Slf4j
public class BruteForceController {


    ConnecterToLogin connecterToLogin;
    PasswordCracker passwordCracker;
    PasswordMostCommon passwordMostCommon;

    @Autowired
    public BruteForceController(ConnecterToLogin connecterToLogin, PasswordCracker passwordCracker,
                                PasswordMostCommon passwordMostCommon) {
        this.connecterToLogin = connecterToLogin;
        this.passwordCracker = passwordCracker;
        this.passwordMostCommon = passwordMostCommon;
    }

    @GetMapping
    public String getPassword() throws IOException {
        String response = null;
        log.info("Get password called");


        int length = 1;
        while (!Objects.equals(response, "http://localhost:9050/")) {
            passwordCracker.bruteForce(length);
            response = passwordCracker.result;
            length++;
        }

        return response + " Password = " + passwordCracker.password;
    }

    @GetMapping("/dictionary")
    public String getPassword2() throws IOException {

        passwordMostCommon.checkCommonPasswords(
                passwordMostCommon.processTxtFile(
                        passwordMostCommon.passwordsTxt));

        return passwordMostCommon.response + " Password = " + passwordMostCommon.password;
    }
}
