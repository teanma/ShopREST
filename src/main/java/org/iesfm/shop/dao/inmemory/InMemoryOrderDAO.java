package org.iesfm.shop.dao.inmemory;

import org.iesfm.shop.Article;
import org.iesfm.shop.Client;
import org.iesfm.shop.Order;
import org.iesfm.shop.dao.OrderDAO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryOrderDAO implements OrderDAO {

    private Map<Integer, Order> orders = new HashMap<>();

    @Override
    public List<Order> list() {
        return new LinkedList<>(orders.values());
    }


    @Override
    public List<Order> list(int clientId) {
        List<Order> orders = new LinkedList<>();
        for (Order order : list()) {
            if (order.getClientId() == clientId) {
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public Order get(int id) {
        return orders.get(id);
    }

    @Override
    public boolean insert(Order order) {
        if (!orders.containsKey(order.getId())) {
            orders.put(order.getId(), order);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Order order) {
        if(orders.containsKey(order.getId())) {
            orders.put(order.getId(), order);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if(orders.containsKey(id)) {
            orders.remove(id);
            return true;
        }
        return false;
    }
}
