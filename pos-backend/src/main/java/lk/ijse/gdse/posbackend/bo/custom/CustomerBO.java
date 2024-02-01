package lk.ijse.gdse.posbackend.bo.custom;

import java.util.ArrayList;

public interface CustomerBO <T,C,ID>{
    boolean saveCustomer(T t,C c);
    boolean updateCustomer(T t,C c);
    boolean deleteCustomer(ID id,C c);
    ArrayList<T> getAllCustomers(C c);
    T searchCustomer(C c,ID id);
}
