package lk.ijse.gdse.posbackend.dao.custom.impl;

import lk.ijse.gdse.posbackend.dao.custom.OrderDAO;
import lk.ijse.gdse.posbackend.entity.Order;
import lk.ijse.gdse.posbackend.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(Order order, Connection connection) {

        try {
            String sql="insert into orders values (?,?,?)";
            return CrudUtil.execute(sql,connection,
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getDate());
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Order order, Connection connection) {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) {
        return false;
    }

    @Override
    public ArrayList<Order> getAll(Connection connection) {
        return null;
    }

    @Override
    public Order search(Connection connection, String s) {
        return null;
    }
}
