package com.unifecaf.estoque.repository;

import com.unifecaf.estoque.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
