package main.java.com.inventario.repositories;

import com.inventario.models.InventarioModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@repository
public interface InventarioRepositories extends JpaRepository<Inventario, Integer> {

}
