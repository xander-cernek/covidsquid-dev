package com.covidsquid.dev.apis;

import java.util.Optional;

import com.covidsquid.dev.model.LoginRequest;
import com.covidsquid.dev.model.SignupRequest;
import com.covidsquid.dev.model.User;
import com.covidsquid.dev.repositories.UserRepository;
import com.covidsquid.dev.util.SquidSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

  @Autowired
  UserRepository userRepository;

  @ResponseBody
  @RequestMapping(value="/ping", method=RequestMethod.GET, produces="application/json")
  public Boolean ping() {
    return true;
  }

  @RequestMapping(value="/signup", method=RequestMethod.POST, produces="application/json")
  public void signup(@RequestBody SignupRequest signupRequest) {
    String saltedPassword = SquidSecurity.getHashedAndSaltedPassword(signupRequest.getPassword()).toString();
    User record = User.builder().username(signupRequest.getUsername()).password(saltedPassword).email(signupRequest.getEmail()).build();
    userRepository.save(record);
  }

  @RequestMapping(value="/login", method=RequestMethod.POST, produces="application/json")
  public boolean login(@RequestBody LoginRequest loginRequest) {
    Optional<User> user = userRepository.findById(loginRequest.getUsername());
    if (user.isPresent()) {
      if (loginRequest.getPassword().equals(user.get().getPassword())) {
        return true;
      }
    }
    return false;
  }
}
