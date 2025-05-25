package edu3431.matiukhin.productmanagment.repository;


import edu3431.matiukhin.productmanagment.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    @Override
    Optional<ProductModel> findById(Long id);

}
