package com.toursandtravel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.toursandtravel.entity.User;
import com.toursandtravel.service.UserService;
import com.toursandtravel.utility.Constants.ActiveStatus;
import com.toursandtravel.utility.Constants.UserRole;

@SpringBootApplication
public class ToursAndTravelsBackendApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ToursAndTravelsBackendApplication.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ToursAndTravelsBackendApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ToursAndTravelsBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        User admin = this.userService.getUserByEmailIdAndRoleAndStatus(
                "demo.admin@demo.com",
                UserRole.ROLE_ADMIN.value(),
                ActiveStatus.ACTIVE.value());

        if (admin == null) {
            LOG.info("Admin not found in system, so adding default admin");

            User user = new User();
            user.setEmailId("demo.admin@demo.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(UserRole.ROLE_ADMIN.value());
            user.setStatus(ActiveStatus.ACTIVE.value());

            this.userService.addUser(user);
        }
    }
}
