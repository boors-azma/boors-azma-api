package com.ernoxin.boorsazmaapi.security;

import com.ernoxin.boorsazmaapi.model.User;
import com.ernoxin.boorsazmaapi.model.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class AppUserPrincipal implements UserDetails {

    private final Long id;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final UserRole role;

    private AppUserPrincipal(Long id, String email, String phoneNumber, String password, UserRole role) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    public static AppUserPrincipal from(User user) {
        return new AppUserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getRole()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email != null ? email : phoneNumber;
    }
}
