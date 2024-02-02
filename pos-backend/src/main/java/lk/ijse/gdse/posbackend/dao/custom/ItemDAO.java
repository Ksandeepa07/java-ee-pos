package lk.ijse.gdse.posbackend.dao.custom;

import lk.ijse.gdse.posbackend.dao.CrudDAO;
import lk.ijse.gdse.posbackend.entity.Item;

import java.sql.Connection;

public interface ItemDAO extends CrudDAO<Item,Connection,String> {
}
