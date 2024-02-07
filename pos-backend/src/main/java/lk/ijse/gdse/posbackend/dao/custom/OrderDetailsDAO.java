package lk.ijse.gdse.posbackend.dao.custom;

import lk.ijse.gdse.posbackend.dao.CrudDAO;
import lk.ijse.gdse.posbackend.entity.OrderDetails;

import java.sql.Connection;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails, Connection,String> {
}
