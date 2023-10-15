package fr.gofly.model.token;

import fr.gofly.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue
    @Column(name = "token_id",
            nullable = false)
    private Integer id;

    @Column(name = "token_hex",
            nullable = false)
    private String hex;

    @Column(name = "token_type",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType type;

    @Column(name = "token_expired",
            nullable = false)
    private boolean isExpired;

    @Column(name = "token_revoked",
            nullable = false)
    private boolean isRevoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
