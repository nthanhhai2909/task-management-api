package io.github.nthanhhai2909.taskmanagement.interfaces.restful.health;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthAPI {
    @RequestMapping("/up")
    public String up() {
        return "OK";
    }
}
