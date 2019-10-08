package main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    Service service = new Service();

    @RequestMapping("/rev")
    public String greeting(@RequestParam String string) {
        return service.greeting(string);
    }
}
