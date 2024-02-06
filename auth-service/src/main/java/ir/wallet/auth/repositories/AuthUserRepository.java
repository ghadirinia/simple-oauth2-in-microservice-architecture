package ir.wallet.auth.repositories;

import ir.wallet.auth.model.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUserEntity,Long> {
    Optional<AuthUserEntity> findByUsername(String username);
}
