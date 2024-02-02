package lk.ijse.gdse.posbackend.bo;

import lk.ijse.gdse.posbackend.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.posbackend.bo.custom.impl.ItemBOImpl;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getInstance(){
        if (boFactory==null){
           return boFactory=new BOFactory();
        }else{
            return boFactory;
        }
    }


    public enum BOTypes{
        CUSTOMER,ITEM
    }

public <T extends SuperBO > T getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return (T) new CustomerBOImpl();

            case ITEM:
                return (T) new ItemBOImpl();

            default:
                return null;
        }
}
}
