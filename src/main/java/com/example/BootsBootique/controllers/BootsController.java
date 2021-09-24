package com.example.BootsBootique.controllers;

import com.example.BootsBootique.entities.Boot;
import com.example.BootsBootique.enums.BootType;
import com.example.BootsBootique.exceptions.NotImplementedException;
import com.example.BootsBootique.exceptions.QueryNotSupportedException;
import com.example.BootsBootique.repositories.BootsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/boots")
public class BootsController {

    private final BootsRepository bootsRepository;


    public BootsController(BootsRepository bootsRepository) {
        this.bootsRepository = bootsRepository;
    }


    @GetMapping("/")
    public Iterable<Boot> getAllBoots() {
        return this.bootsRepository.findAll();
    }

    @GetMapping("/types")
    public List<BootType> getBootTypes() {
        return Arrays.asList(BootType.values());
    }

    @PostMapping("/")
    public Boot addBoot(@RequestBody Boot boot) {
        return bootsRepository.save(boot);
    }

    @DeleteMapping("/{id}")
    public Boot deleteBoot(@PathVariable("id") Integer id) {
        Optional<Boot> deleteBootOptional = this.bootsRepository.findById(id);
        if (!deleteBootOptional.isPresent()) {
            return null;
        }
        Boot bootToBeDeleted = deleteBootOptional.get();

        this.bootsRepository.delete(bootToBeDeleted);
        return bootToBeDeleted;
    }

    @PutMapping("/{id}/quantity/increment")
    public Boot incrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> incrementalQuantityOptional = this.bootsRepository.findById(id);
        if (!incrementalQuantityOptional.isPresent()) {
            return null;
        }
        Boot BootTobeIncremented = incrementalQuantityOptional.get();

        BootTobeIncremented.setQuantity(BootTobeIncremented.getQuantity() + 1);
        this.bootsRepository.save(BootTobeIncremented);
        return BootTobeIncremented;
    }

    @PutMapping("/{id}/quantity/decrement")
    public Boot decrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> decrementQuantityOptional = this.bootsRepository.findById(id);
        if (!decrementQuantityOptional.isPresent()) {
            return null;
        }

        Boot bootTobeDecremented = decrementQuantityOptional.get();
        bootTobeDecremented.setQuantity(bootTobeDecremented.getQuantity() - 1);
        bootsRepository.save(bootTobeDecremented);
        return bootTobeDecremented;
    }

    @GetMapping("/search")
    public List<Boot> searchBoots(@RequestParam(required = false) String material,
                                  @RequestParam(required = false) BootType type, @RequestParam(required = false) Float size,
                                  @RequestParam(required = false, name = "quantity") Integer minQuantity) throws QueryNotSupportedException {
        if (Objects.nonNull(material)) {
            if (Objects.nonNull(type) && Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a material, type, size, and minimum
                // quantity
               return bootsRepository.findByTypeAndSizeAndQuantityGreaterThan(type, size, minQuantity);

            } else if (Objects.nonNull(type) && Objects.nonNull(size)) {
                // call the repository method that accepts a material, size, and type
              return   bootsRepository.findByMaterialAndSizeAndType(material, size, type);

            } else if (Objects.nonNull(type) && Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a material, a type, and a minimum
                // quantity
               return bootsRepository.findByMaterialAndTypeAndQuantity(material, type, minQuantity);
            } else if (Objects.nonNull(type)) {
                // call the repository method that accepts a material and a type
              return   bootsRepository.findByMaterialAndType(material, type);
            } else {
                // call the repository method that accepts only a material
              return   bootsRepository.findByMaterial(material);
            }
        } else if (Objects.nonNull(type)) {
            if (Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a type, size, and minimum quantity
              return   bootsRepository.findByTypeAndSizeAndQuantityGreaterThan(type, size, minQuantity);
            } else if (Objects.nonNull(size)) {
                // call the repository method that accepts a type and a size
               return bootsRepository.findByTypeAndSize(type, size);
            } else if (Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a type and a minimum quantity
              return   bootsRepository.findByTypeAndQuantityGreaterThan(type, minQuantity);
            } else {
                // call the repository method that accept only a type
              return   bootsRepository.findByType(type);
            }
        } else if (Objects.nonNull(size)) {
            if (Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a size and a minimum quantity
              return   bootsRepository.findBySizeAndQuantity(size, minQuantity);
            } else {
                // call the repository method that accepts only a size
              return   bootsRepository.findBySize(size);
            }
        } else if (Objects.nonNull(minQuantity)) {
            // call the repository method that accepts only a minimum quantity
           return bootsRepository.findByQuantityGreaterThan(minQuantity);
        } else {
            throw new QueryNotSupportedException("This query is not supported! Try a different combination of search parameters.");
        }

    }
}
