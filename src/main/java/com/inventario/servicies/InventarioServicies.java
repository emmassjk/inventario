package com.inventario.servicies;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.dto.InventarioDTO;
import com.inventario.models.InventarioModels;
import com.inventario.repositories.InventarioRepositories;


@Service
public class InventarioServicies {

    @Autowired
    private InventarioRepositories inventarioRepository;

    private InventarioDTO toDTO(InventarioModels inventario) {
        return new InventarioDTO(
            inventario.getId(),
            inventario.getIdProducto(),
            inventario.getStockDisponible(),
            inventario.getUbicacionBodega()
        );
    }

    private InventarioModels toEntity(InventarioDTO dto) {
        InventarioModels inventario = new InventarioModels();
        inventario.setId(dto.getId());
        inventario.setIdProducto(dto.getIdProducto());
        inventario.setStockDisponible(dto.getStockDisponible());
        inventario.setUbicacionBodega(dto.getUbicacionBodega());
        return inventario;
    }

    public InventarioDTO crear(InventarioDTO dto) {
        InventarioModels inventario = toEntity(dto);
        return toDTO(inventarioRepository.save(inventario));
    }

    public List<InventarioDTO> listar() {
        return inventarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public InventarioDTO obtenerPorId(Integer id) {
        InventarioModels inventario = inventarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        return toDTO(inventario);
    }

    public InventarioDTO actualizar(Integer id, InventarioDTO dto) {
        InventarioModels existente = inventarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        existente.setIdProducto(dto.getIdProducto());
        existente.setStockDisponible(dto.getStockDisponible());
        existente.setUbicacionBodega(dto.getUbicacionBodega());

        return toDTO(inventarioRepository.save(existente));
    }

    public void eliminar(Integer id) {
        inventarioRepository.deleteById(id);
    }
}