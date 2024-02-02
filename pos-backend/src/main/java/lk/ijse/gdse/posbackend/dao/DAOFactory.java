package lk.ijse.gdse.posbackend.dao;

import lk.ijse.gdse.posbackend.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse.posbackend.dao.custom.impl.ItemDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getInstance(){
        if (daoFactory==null){
            return daoFactory=new DAOFactory();
        }else {
            return daoFactory;
        }
    }

    public enum DAOTypes{
        CUSTOMER,ITEM
    }

    public  <T extends SuperDAO>T getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return (T) new CustomerDAOImpl();

            case ITEM:
                return (T) new ItemDAOImpl();
            default:
                return null;
        }

    }
}
