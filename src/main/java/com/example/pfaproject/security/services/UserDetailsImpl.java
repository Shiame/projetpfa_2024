package com.example.pfaproject.security.services;

import com.example.pfaproject.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String nom;
    private Long id;
    private String email;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id,String nom, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {

        this.id = id;
        this.nom=nom;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    public static UserDetailsImpl build(User user) {
        String role = String.valueOf(user.getRole()); // extracts the user's role from the User object and converts it to a string.

        //Create a List of GrantedAuthority objects based on the user's role:
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getMotDePasse(),
                user.getNom(),
                authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
            return authorities;
        }
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername(){ return email;}
    @Override
    public String getPassword(){ return password;}
    @Override
    public boolean isAccountNonExpired(){return true;}
    public boolean isAccountNonLocked(){return true;}
    public boolean isCredentialsNonExpired(){return true;}
    public boolean isEnabled(){return true;}

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) obj;
        return Objects.equals(id, user.id);
    }
}

