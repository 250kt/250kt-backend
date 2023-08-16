package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "user_firstname")
    private String userFirstName;

    @Column(name = "user_lastname")
    private String userLastName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role userRole;
}
