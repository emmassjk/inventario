package com.inventario.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    // MÉTODO HATEOAS para buscar por ID
    @GetMapping("/hateoas/{id}")
    public InventarioDTO obtenerHATEOAS(@PathVariable Integer id) {
        InventarioDTO dto = inventarioService.obtenerPorId(id);

        // Links de la misma API
        dto.add(linkTo(methodOn(InventarioControllers.class).obtenerHATEOAS(id)).withSelfRel());
        dto.add(linkTo(methodOn(InventarioControllers.class).obtenerTodosHATEOAS()).withRel("todos"));
        dto.add(linkTo(methodOn(InventarioControllers.class).eliminar(id)).withRel("eliminar"));

        // Links HATEOAS para API Gateway "A mano"
        dto.add(org.springframework.hateoas.Link.of("http://localhost:8888/api/proxy/inventario/" + dto.getId()).withSelfRel());
        dto.add(org.springframework.hateoas.Link.of("http://localhost:8888/api/proxy/inventario/" + dto.getId()).withRel("Modificar HATEOAS").withType("PUT"));
        dto.add(org.springframework.hateoas.Link.of("http://localhost:8888/api/proxy/inventario/" + dto.getId()).withRel("Eliminar HATEOAS").withType("DELETE"));

        return dto;
    }

    // MÉTODO HATEOAS para listar todos los inventarios utilizando HATEOAS
    @GetMapping("/hateoas")
    public List<InventarioDTO> obtenerTodosHATEOAS() {
        List<InventarioDTO> lista = inventarioService.listar();

        for (InventarioDTO dto : lista) {
            // Link de la misma API
            dto.add(linkTo(methodOn(InventarioControllers.class).obtenerHATEOAS(dto.getId())).withSelfRel());

            // Links HATEOAS para API Gateway "A mano"
            dto.add(org.springframework.hateoas.Link.of("http://localhost:8888/api/proxy/inventario").withRel("Get todos HATEOAS"));
            dto.add(org.springframework.hateoas.Link.of("http://localhost:8888/api/proxy/inventario/" + dto.getId()).withRel("Crear HATEOAS").withType("POST"));
        }

        return lista;
    }
}
