package lk.ijse.gdse.posbackend.entity;

import lk.ijse.gdse.posbackend.dao.DAOFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private String orderId;
    private String itemId;
    private int qty ;
    private Double total;
}
