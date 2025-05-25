package edu3431.matiukhin.productincartmanagment.repository;


import edu3431.matiukhin.productincartmanagment.model.ProductInCartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCartModel, Long> {
    ProductInCartModel findByProductIdAndClientId(Long productId, Long clientId);
    List<ProductInCartModel> findAllByClientId(Long clientId);
    void deleteById(Long id);
}
