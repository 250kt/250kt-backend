package fr.gofly.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id",
            nullable = false)
    @UuidGenerator
    private String userId;

    @Column(name = "user_name",
            nullable = false,
            unique=true)
    private String userName;

    @Column(name = "user_password",
            nullable = false)
    private String userPassword;

    @Column(name = "user_email",
            nullable = false,
            unique=true)
    private String userEmail;

    @Column(name = "user_email_confirmed",
            nullable = false)
    private Boolean userEmailConfirmed = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role",
            nullable = false)
    private Role userRole;

    @OneToMany(mappedBy = "user")
    private Set<Aircraft> aircraft;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
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
