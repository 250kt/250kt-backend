package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {
    @Id
    @Column(name = "user_id",
            nullable = false)
    @UuidGenerator
    private String userId;

    @Column(name = "user_firstname",
            nullable = false)
    private String userFirstName;

    @Column(name = "user_lastname",
            nullable = false)
    private String userLastName;

    @Column(name = "user_password",
            nullable = false)
    private String userPassword;


    @Column(name = "user_email",
            nullable = false,
            unique=true)
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role",
            nullable = false)
    private Role userRole;

    @OneToMany(mappedBy = "user")
    private Set<Aircraft> aircraft;
}
