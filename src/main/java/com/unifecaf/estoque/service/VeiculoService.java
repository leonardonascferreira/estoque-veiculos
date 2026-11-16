package com.unifecaf.estoque.service;

import com.unifecaf.estoque.model.Modelo;
import com.unifecaf.estoque.model.StatusVeiculo;
import com.unifecaf.estoque.model.Veiculo;
import com.unifecaf.estoque.repository.ModeloRepository;
import com.unifecaf.estoque.repository.VeiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ModeloRepository modeloRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, ModeloRepository modeloRepository) {
        this.veiculoRepository = veiculoRepository;
        this.modeloRepository = modeloRepository;
    }

    public List<Veiculo> filtrar(Long marcaId, Long modeloId, Integer ano,
                                 BigDecimal precoMin, BigDecimal precoMax, StatusVeiculo status) {
        return veiculoRepository.filtrar(marcaId, modeloId, ano, precoMin, precoMax, status);
    }

    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));
    }

    public Veiculo criar(Veiculo veiculo) {
        veiculo.setId(null);
        veiculo.setModelo(buscarModelo(veiculo));
        return veiculoRepository.save(veiculo);
    }

    public Veiculo atualizar(Long id, Veiculo dados) {
        Veiculo existente = buscarPorId(id);
        existente.setModelo(buscarModelo(dados));
        existente.setAno(dados.getAno());
        existente.setCor(dados.getCor());
        existente.setPreco(dados.getPreco());
        existente.setQuilometragem(dados.getQuilometragem());
        existente.setStatus(dados.getStatus());
        return veiculoRepository.save(existente);
    }

    public void remover(Long id) {
        veiculoRepository.delete(buscarPorId(id));
    }

    private Modelo buscarModelo(Veiculo veiculo) {
        if (veiculo.getModelo() == null || veiculo.getModelo().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o modelo (modelo.id)");
        }
        return modeloRepository.findById(veiculo.getModelo().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modelo não encontrado"));
    }
}
