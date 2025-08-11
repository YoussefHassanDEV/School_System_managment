package com.example.student_management_system.model;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@DiscriminatorValue("MANAGER")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Manager extends AppUser implements UserDetails {
    private Double salary;
    private String department;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.reportsTo == null) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_SUPER_MANAGER"));
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + getRole().name()));
    }


    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
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

    @ManyToOne
    @JoinColumn(name = "reports_to_id")
    private Manager reportsTo;
    @OneToMany(mappedBy = "reportsTo")
    private Set<Manager> subManagers;
}
