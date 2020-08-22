package com.covidsquid.dev.apis;

import java.util.Optional;
import java.util.UUID;

import com.covidsquid.dev.model.RegisterRequest;
import com.covidsquid.dev.model.SignupRequest;
import com.covidsquid.dev.model.User;
import com.covidsquid.dev.repositories.UserRepository;
import com.covidsquid.dev.security.JwtAuthenticationController;
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

  @Autowired
  JwtAuthenticationController jwtAuthenticationController;

  @ResponseBody
  @RequestMapping(value="/ping", method=RequestMethod.GET, produces="application/json")
  public Boolean ping() {
    return true;
  }

  @RequestMapping(value="/signup", method=RequestMethod.POST, produces="application/json")
  public boolean signup(@RequestBody SignupRequest signupRequest) {
    String saltedPassword = SquidSecurity.getHashedAndSaltedPassword(signupRequest.getPassword()).toString();
    //For now registered is true. Adding email later
    User record = User.builder()
      .email(signupRequest.getEmail())
      .username(signupRequest.getUsername())
      .password(saltedPassword)
      .salt("undefined")
      .registered(true)
      .registerId(UUID.randomUUID().toString())
      .sessionId("expired")
      .build();
    userRepository.save(record);
    return true;
  }

  @RequestMapping(value="/register", method=RequestMethod.POST, produces="application/json")
  public boolean signup(@RequestBody RegisterRequest registerRequest) {
    Optional<User> record = userRepository.findById(registerRequest.getEmail());
    if (record.isPresent()) {
      if (!record.get().getRegistered()) {
        return registerRequest.getRegisterId().equals(record.get().getRegisterId());
      }
    }
    return false;
  }
}
