package lk.ijse.gdse.posbackend.dao.custom;

import lk.ijse.gdse.posbackend.dao.CrudDAO;
import lk.ijse.gdse.posbackend.entity.Customer;

import javax.sql.DataSource;
import java.sql.Connection;

public interface CustomerDAO extends CrudDAO<Customer, Connection,String> {

}
