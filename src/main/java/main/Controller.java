package main;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class Controller {
    private Service service = new Service();
    private Zad3 zad3 = new Zad3();

    @RequestMapping("/rev/{string}")
    public String rev(@PathVariable String string) {
        return service.reverse(string);
    }

    @RequestMapping("/string/{string}")
    public Map string(@PathVariable String string) {
        return service.stringInfo(string);
    }

    @RequestMapping("/ics")
    public String ics() {
        return zad3.ics();
    }
}
