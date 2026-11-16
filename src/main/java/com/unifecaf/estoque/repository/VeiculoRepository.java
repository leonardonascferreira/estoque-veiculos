package com.unifecaf.estoque.repository;

import com.unifecaf.estoque.model.StatusVeiculo;
import com.unifecaf.estoque.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    // Parâmetros nulos são ignorados, então qualquer combinação de filtros funciona.
    @Query("""
            SELECT v FROM Veiculo v
            WHERE (:marcaId IS NULL OR v.modelo.marca.id = :marcaId)
              AND (:modeloId IS NULL OR v.modelo.id = :modeloId)
              AND (:ano IS NULL OR v.ano = :ano)
              AND (:precoMin IS NULL OR v.preco >= :precoMin)
              AND (:precoMax IS NULL OR v.preco <= :precoMax)
              AND (:status IS NULL OR v.status = :status)
            ORDER BY v.id
            """)
    List<Veiculo> filtrar(@Param("marcaId") Long marcaId,
                          @Param("modeloId") Long modeloId,
                          @Param("ano") Integer ano,
                          @Param("precoMin") BigDecimal precoMin,
                          @Param("precoMax") BigDecimal precoMax,
                          @Param("status") StatusVeiculo status);
}
