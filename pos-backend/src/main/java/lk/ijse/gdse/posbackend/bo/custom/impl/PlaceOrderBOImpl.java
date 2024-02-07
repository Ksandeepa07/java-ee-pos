package lk.ijse.gdse.posbackend.bo.custom.impl;

import lk.ijse.gdse.posbackend.bo.custom.PlaceOrderBO;
import lk.ijse.gdse.posbackend.dao.DAOFactory;
import lk.ijse.gdse.posbackend.dao.custom.CustomerDAO;
import lk.ijse.gdse.posbackend.dao.custom.ItemDAO;
import lk.ijse.gdse.posbackend.dao.custom.OrderDAO;
import lk.ijse.gdse.posbackend.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.posbackend.dto.CustomerDTO;
import lk.ijse.gdse.posbackend.dto.ItemDTO;
import lk.ijse.gdse.posbackend.dto.OrderDTO;
import lk.ijse.gdse.posbackend.dto.OrderDetailsDTO;
import lk.ijse.gdse.posbackend.entity.Customer;
import lk.ijse.gdse.posbackend.entity.Item;
import lk.ijse.gdse.posbackend.entity.Order;
import lk.ijse.gdse.posbackend.entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO<CustomerDTO, ItemDTO, OrderDTO, Connection, String> {

    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);

    OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);


    @Override
    public ArrayList<CustomerDTO> loadCustomerIds(Connection connection) {

        ArrayList<Customer> all = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer customer : all) {
            customerDTOS.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            ));

        }
        return customerDTOS;

    }

    @Override
    public ArrayList<ItemDTO> loadItemIds(Connection connection) {
        ArrayList<Item> all = itemDAO.getAll(connection);
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item item : all) {
            itemDTOS.add(new ItemDTO(
                    item.getId(),
                    item.getName(),
                    item.getType(),
                    item.getPrice(),
                    item.getQty()
            ));

        }
        return itemDTOS;
    }

    @Override
    public CustomerDTO searchCustomer(Connection connection, String id) {
        Customer search = customerDAO.search(connection, id);
        return new CustomerDTO(
                search.getId(),
                search.getName(),
                search.getAddress(),
                search.getSalary()
        );
    }

    @Override
    public ItemDTO searchItem(Connection connection, String id) {
        Item search = itemDAO.search(connection, id);
        return new ItemDTO(
                search.getId(),
                search.getName(),
                search.getType(),
                search.getPrice(),
                search.getQty()
        );
    }

    @Override
    public boolean placeOrder(OrderDTO orderDTO, Connection connection) {

        try {
            connection.setAutoCommit(false);
            boolean isSaveOrder = orderDAO.save(new Order(
                    orderDTO.getOrderId(),
                    orderDTO.getCustomerId(),
                    orderDTO.getDate()
            ), connection);

            if (isSaveOrder) {
                for (OrderDetailsDTO listData : orderDTO.getOrderDetailsList()) {
                    boolean isSaveOrderDetails = orderDetailsDAO.save(new OrderDetails(
                            listData.getOrderId(),
                            listData.getItemId(),
                            listData.getQty(),
                            listData.getTotal()

                    ), connection);

                    if (!isSaveOrderDetails) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }

                    Item item = itemDAO.search(connection, listData.getItemId());
                    System.out.println(item);
                    int newQty = item.getQty() - listData.getQty();
                    boolean isItemUpdate = itemDAO.update(new Item(
                            listData.getItemId(),
                            item.getName(),
                            item.getType(),
                            item.getPrice(),
                            newQty
                    ), connection);

                    if (!isItemUpdate) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }
                }

                connection.commit();
                return true;
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}

