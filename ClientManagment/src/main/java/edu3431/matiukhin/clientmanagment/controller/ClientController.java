package edu3431.matiukhin.clientmanagment.controller;




import dto.ClientDTO;
import dto.SaveClientDTO;
import dto.UpdateClientDTO;
import edu3431.matiukhin.clientmanagment.service.ClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Documented;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
@RequestMapping("/api/v1/clients")
@AllArgsConstructor

public class ClientController {
    @Autowired
    private final ClientService clientService;
    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping
    public void  saveClient(@Valid @RequestBody SaveClientDTO client) {
        clientService.saveClient(client);

    }


    @PutMapping
    public void updateClient( @Valid @RequestBody UpdateClientDTO client) {
        clientService.updateClient(client);
    }


    @PostMapping("/names")
    public Map<Long, String> getNames(@RequestBody List<Long> ids){
        return clientService.getClientNames(ids);
    }


    @GetMapping("/email/{email}")
    public ClientDTO findClientByEmail(@PathVariable String email) {
        return clientService.findClientByEmail(email);
    }

    @GetMapping("/id/{clientId}")
    public ClientDTO findClientById(@PathVariable("clientId") Long id) {
        return clientService.findClientById(id);
    }



    @DeleteMapping ("{email}")

    public void deleteClientByEmail(@PathVariable String email) {
        clientService.deleteClient(email);
    }
}

