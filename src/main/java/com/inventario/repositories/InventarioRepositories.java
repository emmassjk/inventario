package com.inventario.repositories;

import com.inventario.models.InventarioModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventarioRepositories extends JpaRepository<InventarioModels, Integer> {

}
