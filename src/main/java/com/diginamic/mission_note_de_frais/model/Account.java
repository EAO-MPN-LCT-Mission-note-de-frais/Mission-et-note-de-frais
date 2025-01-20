package com.diginamic.mission_note_de_frais.model;

import com.diginamic.mission_note_de_frais.model.entity.User.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * A custom implementation of UserDetails.
 * This class represents an account with an email and a password.
 * With our own object, we can easily create a mock account for testing purposes.
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Account implements UserDetails {
    /**
     * The email address serves as the username.
     */
    private String email;

    /**
     * The password used to create the user account.
     * Use the {@link #getUsername()}  to access this email field.
     */
    private String password;

    /**
     * The list of roles assigned to the user.
     */
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.name())
        ).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
