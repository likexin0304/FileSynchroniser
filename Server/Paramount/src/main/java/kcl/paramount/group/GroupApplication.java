package kcl.paramount.group;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

// the main entrance of the application
@SpringBootApplication
public class GroupApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GroupApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GroupApplication.class, args);
    }

}

