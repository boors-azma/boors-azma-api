package com.ernoxin.boorsazmaapi.security;

import com.ernoxin.boorsazmaapi.model.User;
import com.ernoxin.boorsazmaapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrPhoneNumber(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return AppUserPrincipal.from(user);
    }

    public AppUserPrincipal loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return AppUserPrincipal.from(user);
    }
}
