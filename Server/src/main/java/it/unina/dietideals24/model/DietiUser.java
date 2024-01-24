package it.unina.dietideals24.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "unique_email", columnNames = "email"))
public class DietiUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    private String password;
    private String biography;
    private List<String> links;
    private String geographicalArea;
    private String profilePictureUrl;

    public DietiUser(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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

    @Override
    public boolean equals(Object o){
        if (o instanceof DietiUser dietiUser) {
            if (
                    this.name.equals(dietiUser.name) &&
                    this.surname.equals(dietiUser.surname) &&
                    this.email.equals(dietiUser.email) &&
                    this.geographicalArea.equals(dietiUser.geographicalArea) &&
                    this.biography.equals(dietiUser.biography) &&
                    this.links.equals(dietiUser.links)
            )
                return true;
            else
                return false;
        } else
            return false;

    }

    public DietiUser(String name, String surname, String email, String biography, String geographicalArea, List<String> links) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.biography = biography;
        this.geographicalArea = geographicalArea;
        if (links == null)
            this.links = new ArrayList<>();
        else
            this.links = links;
    }
}
