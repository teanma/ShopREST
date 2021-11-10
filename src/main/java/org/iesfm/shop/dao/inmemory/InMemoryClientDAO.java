package org.iesfm.shop.dao.inmemory;

import org.iesfm.shop.Article;
import org.iesfm.shop.Client;
import org.iesfm.shop.dao.ClientDAO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryClientDAO implements ClientDAO {

    private Map<Integer, Client> clients = new HashMap<>();

    @Override
    public List<Client> list() {
        return new LinkedList<>(clients.values());
    }


    @Override
    public Client get(int id) {
        return clients.get(id);
    }

    @Override
    public boolean insert(Client client) {
        if (!clients.containsKey(client.getId())) {
            clients.put(client.getId(), client);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Client client) {
        if(clients.containsKey(client.getId())) {
            clients.put(client.getId(), client);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if(clients.containsKey(id)) {
            clients.remove(id);
            return true;
        }
        return false;
    }
}
