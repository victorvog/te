package com.biopark.cepex.repository;

import com.biopark.cepex.model.CadastroEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroEventoRepository extends JpaRepository<CadastroEvento, Long> { }
