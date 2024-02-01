package lk.ijse.gdse.posbackend.bo.custom;

import lk.ijse.gdse.posbackend.bo.SuperBO;

import java.util.ArrayList;

public interface CustomerBO <T,C,ID> extends SuperBO {
    boolean saveCustomer(T t,C c);
    boolean updateCustomer(T t,C c);
    boolean deleteCustomer(ID id,C c);
    ArrayList<T> getAllCustomers(C c);
    T searchCustomer(C c,ID id);
}
