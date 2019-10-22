package main;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {
    private Service service = new Service();

    @RequestMapping("/rev/{string}")
    public String rev(@PathVariable String string) {
        return service.reverse(string);
    }

    @RequestMapping("/string/{string}")
    public Map string(@PathVariable String string) {
        return service.stringInfo(string);
    }
}
