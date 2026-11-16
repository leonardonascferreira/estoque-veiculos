package com.unifecaf.estoque.controller;

import com.unifecaf.estoque.model.StatusVeiculo;
import com.unifecaf.estoque.model.Veiculo;
import com.unifecaf.estoque.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    // READ: lista com filtros opcionais, ex.: /api/veiculos?marcaId=1&ano=2020&precoMax=80000
    @GetMapping
    public List<Veiculo> listar(@RequestParam(required = false) Long marcaId,
                                @RequestParam(required = false) Long modeloId,
                                @RequestParam(required = false) Integer ano,
                                @RequestParam(required = false) BigDecimal precoMin,
                                @RequestParam(required = false) BigDecimal precoMax,
                                @RequestParam(required = false) StatusVeiculo status) {
        return service.filtrar(marcaId, modeloId, ano, precoMin, precoMax, status);
    }

    @GetMapping("/{id}")
    public Veiculo buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Veiculo criar(@Valid @RequestBody Veiculo veiculo) {
        return service.criar(veiculo);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Veiculo atualizar(@PathVariable Long id, @Valid @RequestBody Veiculo veiculo) {
        return service.atualizar(id, veiculo);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
