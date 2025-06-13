package com.inventario.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventario.dto.InventarioDTO;
import com.inventario.servicies.InventarioServicies;

@RestController
@RequestMapping("/api/inventario")
public class InventarioControllers
 {
    @Autowired
    private InventarioServicies inventarioService;

    @PostMapping
    public ResponseEntity<InventarioDTO> crear(@RequestBody InventarioDTO inventarioDTO) {
        InventarioDTO creado = inventarioService.crear(inventarioDTO);
        return ResponseEntity.ok(creado);
    }

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> listar() {
        List<InventarioDTO> inventarios = inventarioService.listar();
        return ResponseEntity.ok(inventarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> obtenerPorId(@PathVariable Integer id) {
        InventarioDTO inventario = inventarioService.obtenerPorId(id);
        return ResponseEntity.ok(inventario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> actualizar(@PathVariable Integer id, @RequestBody InventarioDTO inventarioDTO) {
        InventarioDTO actualizado = inventarioService.actualizar(id, inventarioDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
