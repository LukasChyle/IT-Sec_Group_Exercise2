package example.itsec_group_exercise2.controller;

        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(" ")
public class UserDataController {

    @GetMapping
    public String getPassword() {
        return "You got in, here is all user data: \n Stina Svensson \n 740522-0345 \n Södergården 54, 293 74 Kyrkhult";
    }
}