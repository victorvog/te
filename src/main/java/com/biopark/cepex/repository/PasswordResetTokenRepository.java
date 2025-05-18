package com.biopark.cepex.repository;

import com.biopark.cepex.model.PasswordResetToken;
import com.biopark.cepex.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUsuario(Usuario usuario);
}