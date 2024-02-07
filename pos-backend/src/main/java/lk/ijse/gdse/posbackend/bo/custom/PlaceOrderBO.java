package lk.ijse.gdse.posbackend.bo.custom;

import lk.ijse.gdse.posbackend.bo.SuperBO;
import lk.ijse.gdse.posbackend.dto.OrderDTO;

import java.util.ArrayList;

public interface PlaceOrderBO <Cu,I,O,C,ID> extends SuperBO {

   ArrayList <Cu> loadCustomerIds(C c);
   ArrayList <I> loadItemIds(C c);

   Cu searchCustomer(C c,ID id);
   I searchItem(C c,ID id);
    boolean placeOrder(O o,C c);

}
