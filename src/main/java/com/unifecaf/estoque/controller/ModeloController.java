package com.unifecaf.estoque.controller;

import com.unifecaf.estoque.model.Marca;
import com.unifecaf.estoque.model.Modelo;
import com.unifecaf.estoque.repository.MarcaRepository;
import com.unifecaf.estoque.repository.ModeloRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@CrossOrigin
public class ModeloController {

    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;

    public ModeloController(ModeloRepository modeloRepository, MarcaRepository marcaRepository) {
        this.modeloRepository = modeloRepository;
        this.marcaRepository = marcaRepository;
    }

    // /api/modelos ou /api/modelos?marcaId=1
    @GetMapping
    public List<Modelo> listar(@RequestParam(required = false) Long marcaId) {
        return marcaId == null ? modeloRepository.findAll() : modeloRepository.findByMarcaId(marcaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Modelo criar(@Valid @RequestBody Modelo modelo) {
        modelo.setId(null);
        modelo.setMarca(buscarMarca(modelo));
        return modeloRepository.save(modelo);
    }

    @PutMapping("/{id}")
    public Modelo atualizar(@PathVariable Long id, @Valid @RequestBody Modelo dados) {
        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Modelo não encontrado"));
        modelo.setNome(dados.getNome());
        modelo.setMarca(buscarMarca(dados));
        return modeloRepository.save(modelo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        if (!modeloRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Modelo não encontrado");
        }
        try {
            modeloRepository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Modelo possui veículos vinculados");
        }
    }

    private Marca buscarMarca(Modelo modelo) {
        if (modelo.getMarca() == null || modelo.getMarca().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe a marca (marca.id)");
        }
        return marcaRepository.findById(modelo.getMarca().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca não encontrada"));
    }
}
