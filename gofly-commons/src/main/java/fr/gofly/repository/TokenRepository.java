package fr.gofly.repository;

import fr.gofly.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
        SELECT t FROM Token t INNER JOIN User u ON t.user.userId = u.userId
        WHERE u.userId = :userId AND (t.tokenExpired = false OR t.tokenRevoked = false)
    """)
    List<Token> findAllValidTokensByUser(String userId);
    Optional<Token> findByTokenHex(String token);
}
