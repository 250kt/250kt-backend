package fr.gofly.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="member")
@Getter
@Setter
@RequiredArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(name = "member_firstname")
    private String memberFirstName;

    @Column(name = "member_lastname")
    private String memberLastName;

    @Column(name = "member_password")
    private String memberPassword;

    @Column(name = "member_email")
    private String memberEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private Role memberRole;
}
