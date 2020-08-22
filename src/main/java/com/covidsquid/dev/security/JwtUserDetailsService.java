package com.covidsquid.dev.security;

import java.util.ArrayList;
import java.util.Optional;

import com.covidsquid.dev.repositories.UserRepository;
import com.covidsquid.dev.util.SquidSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  // TODO: Username actually equals email, fix this!
  @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<com.covidsquid.dev.model.User> user = userRepository.findById(username);
    if (user.isPresent()) {
      if (user.get().getRegistered()) {
        return new User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
      }
      log.info("User not registered!");
    }
    throw new UsernameNotFoundException("User not found with username: " + username);
	}

  public UserDetails loadUserByUsernameAndPassword(String username, String password) throws UsernameNotFoundException {
    log.info("Fetching user");
    Optional<com.covidsquid.dev.model.User> user = userRepository.findById(username);
    if (user.isPresent()) {
      log.info("User {} found", user.get().getEmail());
      String hash = user.get().getPassword();
      if (SquidSecurity.checkPW(password, hash)) {
        log.info("Password match!");
        return new User(user.get().getEmail(), hash, new ArrayList<>());
      } else {
        log.info("Password not matched!");
      }
    }
    throw new UsernameNotFoundException("User not found with username: " + username);
  }
}
