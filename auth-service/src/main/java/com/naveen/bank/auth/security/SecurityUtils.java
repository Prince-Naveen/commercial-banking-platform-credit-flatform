package com.naveen.bank.auth.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

    /**
     * Get Current Authentication
     */
    public static Authentication getAuthentication() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    /**
     * Get Logged In Username
     */
    public static String getCurrentUsername() {

        Authentication authentication = getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            return null;
        }

        return authentication.getName();
    }

    /**
     * Check User Authentication
     */
    public static boolean isAuthenticated() {

        Authentication authentication = getAuthentication();

        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    /**
     * Get User Authorities
     */
    public static Collection<? extends GrantedAuthority> getAuthorities() {

        Authentication authentication = getAuthentication();

        if (authentication == null) {
            return null;
        }

        return authentication.getAuthorities();
    }

    /**
     * Check Role
     */
    public static boolean hasRole(String role) {

        Collection<? extends GrantedAuthority> authorities = getAuthorities();

        if (authorities == null) {
            return false;
        }

        return authorities.stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_" + role));
    }

    /**
     * Check Permission
     */
    public static boolean hasAuthority(String authority) {

        Collection<? extends GrantedAuthority> authorities = getAuthorities();

        if (authorities == null) {
            return false;
        }

        return authorities.stream()
                .anyMatch(grantedAuthority ->
                        grantedAuthority.getAuthority().equals(authority));
    }

}