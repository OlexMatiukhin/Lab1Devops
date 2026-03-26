package edu3431.matiukhin.ordermangment.repository;


import edu3431.matiukhin.ordermangment.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<OrderModel, Long> {
   
     List<OrderModel> findAllByClientId(Long clientId);
     List<OrderModel> findByClientIdIn(List<Long> clientIds);
}
