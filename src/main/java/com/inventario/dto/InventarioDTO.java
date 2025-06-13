package main.java.com.inventario.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO 
{
    private Integer id;
    private Integer idProducto;
    private Integer stockDisponible;
    private String ubicacionBodega;
}
