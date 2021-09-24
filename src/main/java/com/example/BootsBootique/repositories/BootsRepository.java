package com.example.BootsBootique.repositories;

import com.example.BootsBootique.entities.Boot;
import com.example.BootsBootique.enums.BootType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BootsRepository extends CrudRepository<Boot, Integer> {

  public  List<Boot>findBySize(Float size);
   public  List<Boot>findByMaterial(String Material);
  public   List<Boot>findByType(BootType type);
  public   List<Boot> findByQuantityGreaterThan(Integer quantity);

  public  List<Boot> findByMaterialAndSizeAndType(String material, Float size , BootType type);

  public List<Boot> findByTypeAndSize(BootType type, Float size);

  public List<Boot> findBySizeAndQuantity(Float size, Integer quantity);

  public List<Boot> findByMaterialAndType(String material, BootType type);

  public List<Boot> findByMaterialAndTypeAndQuantity(String material, BootType type , Integer quantity);

  public List<Boot> findByMaterialAndQuantityGreaterThan(String material, Integer minQuantity);

  public List<Boot> findByMaterialAndSizeAndQuantityGreaterThan(String material, Float size, Integer minQuantity);

  public List<Boot> findByTypeAndSizeAndQuantityGreaterThan(BootType type, Float size, Integer minQuantity);

  public List<Boot> findByTypeAndQuantityGreaterThan(BootType type, Integer minQuantity);


}
