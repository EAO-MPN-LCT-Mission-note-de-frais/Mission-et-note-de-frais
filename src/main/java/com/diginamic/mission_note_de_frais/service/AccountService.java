package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.Account;
import com.diginamic.mission_note_de_frais.model.repository.CredentialRepository;
import com.diginamic.mission_note_de_frais.model.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Service class for managing accounts. We actually use this service
 * to provide our own implementation of the UserDetailsService interface.
 */
@Service
public class AccountService implements UserDetailsService {
  private final UserRepository userRepository;
  private final CredentialRepository credentialRepository;

  public AccountService(UserRepository userRepository, CredentialRepository credentialRepository) {
    this.userRepository = userRepository;
    this.credentialRepository = credentialRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return getAccount(username);
  }

  /**
   * Retrieves the user account by email.
   *
   * @param email the email of the user
   * @return Account the account details
   * @throws UsernameNotFoundException if the account or credential is not found
   */
  public Account getAccount(String email) {
    var user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Account with username '" + email + "' not found"));

    var credential = credentialRepository.findByUserId(user.getId())
        .orElseThrow(() -> new UsernameNotFoundException("Credential for username '" + email + "' not found"));

    return new Account()
        .setEmail(user.getEmail())
        .setRoles(user.getRoles())
        .setPassword(credential.getPassword());
  }
}