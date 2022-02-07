package tools.wesley.wpscanner.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tools.wesley.wpscanner.repositories.AdministratorRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginService {
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private PasswordHasher passwordHasher;
    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public void login(HttpServletRequest httpServletRequest) {
        var auth = httpServletRequest.getHeader("Authorization");
        if (auth == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        var authSplit = auth.split("-");

        if (authSplit.length != 2)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        var username = authSplit[0];
        var password = authSplit[1];

        login(username, password);
    }

    public void login(String username, String password) {
        if (username == null || password == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        var administrator = administratorRepository.find(username, passwordHasher.hash(password));

        if (administrator.isEmpty()) {
            logger.info("Incorrect login attempt for user: {}.", username);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public void changePassword(String username, String newPassword) {
        var administrator = administratorRepository.findByUsername(username);

        if (administrator.isPresent()) {
            administrator.get().setPassword(passwordHasher.hash(newPassword));
            administratorRepository.save(administrator.get());
        } else {
            logger.info("Incorrect username/password entered when changing password for user: {}.", username);
        }
    }
}
