package com.ernoxin.boorsazmaapi.security;

import com.ernoxin.boorsazmaapi.model.UserRole;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public final class SecurityUtils {

    public static Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AppUserPrincipal principal)) {
            throw new InsufficientAuthenticationException("Authentication is required.");
        }
        return principal.getId();
    }

    public static UserRole currentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AppUserPrincipal principal)) {
            throw new InsufficientAuthenticationException("Authentication is required.");
        }
        return principal.getRole();
    }
}
