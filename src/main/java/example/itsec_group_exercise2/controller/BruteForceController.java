package example.itsec_group_exercise2.controller;

import example.itsec_group_exercise2.service.ConnecterToLogin;
import example.itsec_group_exercise2.service.PasswordCracker;
import example.itsec_group_exercise2.service.PasswordMostCommon;
import example.itsec_group_exercise2.service.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/bruteforce")
@Slf4j
public class BruteForceController {

    ConnecterToLogin connecterToLogin;
    PasswordCracker passwordCracker;
    PasswordMostCommon passwordMostCommon;
    PasswordGenerator passwordGenerator;


    @Autowired
    public BruteForceController(ConnecterToLogin connecterToLogin, PasswordCracker passwordCracker,
                                PasswordMostCommon passwordMostCommon, PasswordGenerator passwordGenerator) {
        this.connecterToLogin = connecterToLogin;
        this.passwordCracker = passwordCracker;
        this.passwordMostCommon = passwordMostCommon;
        this.passwordGenerator = passwordGenerator;
    }

    @GetMapping
    public String getPassword() {
        return passwordGenerator.get();
    }

    @GetMapping("/dictionary")
    public String getPassword2() throws IOException {

        passwordMostCommon.checkCommonPasswords(
                passwordMostCommon.processTxtFile(
                        passwordMostCommon.passwordsTxt));

        return passwordMostCommon.response + " Password = " + passwordMostCommon.password;
    }
}
