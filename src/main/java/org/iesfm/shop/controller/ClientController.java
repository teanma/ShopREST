package org.iesfm.shop.controller;

import org.iesfm.shop.Client;
import org.iesfm.shop.dao.ClientDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ClientController {

    private ClientDAO clientDAO;

    public ClientController(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/clients")
    public List<Client> list() { return clientDAO.list(); }

    @RequestMapping(method = RequestMethod.GET, path = "/clients/{id}")
    public Client get(@PathVariable("id") int id) {
        if (clientDAO.get(id) == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "client not found"
            );
        } else {
            return clientDAO.get(id);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/clients")
    public void insert(@RequestBody Client client) {
        if (clientDAO.insert(client)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "client already exists"
            );
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/clients/{id}")
    public void update(@RequestBody Client client) {
        if (!clientDAO.update(client)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "client not found"
            );
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/clients/{id}")
    public void delete(@PathVariable ("id") int id) {
        if (!clientDAO.delete(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "client not found"
            );
        }
    }
}
