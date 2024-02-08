package lk.ijse.gdse.posbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String orderId;
    private String customerId;
    private String date;
    private ArrayList <OrderDetailsDTO> orderDetailsList;

    public OrderDTO(String orderId, String customerId, String date) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.date = date;
    }
}
