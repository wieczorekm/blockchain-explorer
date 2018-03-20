package pl.edu.pw.elka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping(value = "/hello")
    public String sampleMethod() {
        return "Hello from springboot!";
    }
}
