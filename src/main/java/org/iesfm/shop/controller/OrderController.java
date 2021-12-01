package org.iesfm.shop.controller;

import org.iesfm.shop.Order;
import org.iesfm.shop.dao.OrderDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    private OrderDAO orderDAO;

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/clients/{clientId}/orders")
    public List<Order> list(@PathVariable("clientId") int clientId) {
        if (orderDAO.list(clientId) == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "client not found"
            );
        } else {
            return orderDAO.list(clientId);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/clients")
    public void insert(@RequestBody Order order) {
        if (!orderDAO.insert(order)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "client not found"
            );
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/clients/{clientId}/orders/{orderId}")
    public void update(@RequestBody Order order) {
        if (!orderDAO.update(order)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "client not found"
            );
        }
    }
}
