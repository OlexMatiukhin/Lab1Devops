package edu3431.matiukhin.clientmanagment.repository;


import edu3431.matiukhin.clientmanagment.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {
    ClientModel findClientByEmail(String email);
    void deleteClientByEmail(String email);

    @Override
    Optional<ClientModel> findById(Long id);


}
