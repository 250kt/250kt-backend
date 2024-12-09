package fr.gofly.model;

import fr.gofly.model.airfield.Airfield;
import fr.gofly.model.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private String id;

    @Column(name = "user_name",
            nullable = false,
            unique=true)
    private String username;

    @Column(name = "user_password",
            nullable = false)
    private String password;

    @Column(name = "user_email",
            nullable = false,
            unique=true)
    private String email;

    @Column(name = "user_email_confirmed",
            nullable = false)
    private Boolean isEmailConfirmed = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_authorities",
            nullable = false)
    private List<Authority> authorities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private Set<Aircraft> aircraft;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Transient
    private List<Token> tokens;

    @Column(name = "user_last_connection", nullable = false)
    private LocalDateTime lastConnection;

    @ManyToOne
    @JoinColumn(name = "user_favorite_airfield")
    private Airfield favoriteAirfield;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Authority authority : this.authorities) {
            authorityList.add(new SimpleGrantedAuthority(authority.name()));
        }
        return authorityList;
    }

    @Column(name = "user_avatar")
    @Enumerated(EnumType.STRING)
    private PilotAvatar avatar;

    @Column(name = "user_verification_code")
    private String verificationCode;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
