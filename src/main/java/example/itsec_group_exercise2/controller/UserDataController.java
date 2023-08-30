package example.itsec_group_exercise2.controller;

        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userdata")
public class UserDataController {

    @PostMapping()
    public String getPassword(@RequestBody String input) {
        return "You got in, here is all user data: \n Stina Svensson \n 740522-0345 \n Södergården 54, 293 74 Kyrkhult";
    }
}