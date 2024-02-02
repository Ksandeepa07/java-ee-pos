package lk.ijse.gdse.posbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private String id;
    private String name;
    private String type;
    private double price;
    private int qty;
}
