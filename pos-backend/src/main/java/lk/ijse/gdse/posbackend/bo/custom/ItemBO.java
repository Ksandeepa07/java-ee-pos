package lk.ijse.gdse.posbackend.bo.custom;

import lk.ijse.gdse.posbackend.bo.SuperBO;

import java.util.ArrayList;

public interface ItemBO <T,C,ID>  extends SuperBO {
    boolean saveItem(T t,C c);
    boolean updateItem(T t,C c);
    boolean deleteItem(ID id,C c);
    ArrayList<T> getAllItems(C c);
    T searchItem(C c,ID id);
}
