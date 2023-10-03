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
    private Integer tokenId;

    @Column(name = "token_hex",
            nullable = false)
    private String tokenHex;

    @Column(name = "token_type",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name = "token_expired",
            nullable = false)
    private boolean tokenExpired;

    @Column(name = "token_revoked",
            nullable = false)
    private boolean tokenRevoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
