package main;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class HtmlController {
    private Zad4 zad4 = new Zad4();

    @RequestMapping("/vcard")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/vcard/{search}", method = RequestMethod.GET)
    public String vcard(@PathVariable String search,
                        @RequestParam String login,
                        @RequestParam String password) {
        return zad4.vcard(search, login, password);
    }

    @RequestMapping(value = "/download/{file_name}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("file_name") String fileName,
            HttpServletResponse response) {
        try {
            // get your file as InputStream
            InputStream is = new FileInputStream(new File("vcard/" + fileName));
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
