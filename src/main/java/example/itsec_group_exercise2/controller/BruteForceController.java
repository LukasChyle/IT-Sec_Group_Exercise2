package example.itsec_group_exercise2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bruteforce")
public class BruteForceController {

    @GetMapping
    public String getPassword() {
        return "password";
    }

    @GetMapping("/dictionary")
    public String getPassword2() {
        return "password";
    }
}
