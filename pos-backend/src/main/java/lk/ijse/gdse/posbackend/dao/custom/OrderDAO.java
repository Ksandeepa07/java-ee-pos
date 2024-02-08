package lk.ijse.gdse.posbackend.dao.custom;

import lk.ijse.gdse.posbackend.dao.CrudDAO;
import lk.ijse.gdse.posbackend.entity.Order;

import java.sql.Connection;

public interface OrderDAO extends CrudDAO<Order, Connection,String> {
    Order generateNextOrderId(Connection connection);
}
