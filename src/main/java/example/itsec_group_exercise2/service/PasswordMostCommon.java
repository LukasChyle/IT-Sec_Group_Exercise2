package example.itsec_group_exercise2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PasswordMostCommon {

    ConnecterToLogin connecterToLogin;

    public String response;
    public String password;

    @Autowired
    public PasswordMostCommon(ConnecterToLogin connecterToLogin) {
        this.connecterToLogin = connecterToLogin;
    }


    public String tryMostCommonPasswords() throws IOException {
        checkCommonPasswords(processTxtFile(passwordsTxt));
        return response + " Password = " + password;
    }


    public File passwordsTxt = new File("src/main/resources/passwords.txt");

    public List<String> processTxtFile(File file) throws IOException {

        List<String> passwords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                passwords.add(line);
            }
        }
        return passwords;
    }

    public void checkCommonPasswords(List<String> passwords) {

        for (String password : passwords) {
            response = connecterToLogin.tryPasswords(password);
            if (response.equals("http://localhost:9050/")) {
                log.info("Password found: " + password);
                this.password = password;
                break;
            }
        }
    }
}