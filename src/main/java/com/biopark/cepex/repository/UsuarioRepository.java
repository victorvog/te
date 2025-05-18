package com.biopark.cepex.repository;

import com.biopark.cepex.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);

    boolean existsByEmail(String email);

    Usuario findByRa(String ra);

}
