package lk.ijse.gdse.posbackend.api;

import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.posbackend.bo.BOFactory;
import lk.ijse.gdse.posbackend.bo.custom.impl.ItemBOImpl;
import lk.ijse.gdse.posbackend.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse.posbackend.dto.CustomerDTO;
import lk.ijse.gdse.posbackend.dto.ItemDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(name = "item",urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    DataSource pool;
    ItemBOImpl itemBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.ITEM);

    @Override
    public void init() throws ServletException {
        try {
            pool = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/TestDB");
            System.out.println(pool.getConnection());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("method"));
        if (req.getParameter("method").equals("getAll")) {
            try (Connection connection = pool.getConnection()) {
                ArrayList<ItemDTO> allItems = itemBO.getAllItems(connection);
                System.out.println("b " + allItems);

                if (allItems != null) {
                    System.out.println(allItems);
                    resp.setContentType("application/json");
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write(JsonbBuilder.create().toJson(allItems));
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                    resp.getWriter().write("Failed to retrieve customers !!");
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }else if(req.getParameter("method").equals("search")){
            System.out.println(req.getParameter("name"));
            try (Connection connection = pool.getConnection()) {
                ArrayList<ItemDTO> itemDTOS = itemBO.liveSearch(connection, req.getParameter("name"));
                resp.setContentType("application/json");
                resp.getWriter().write(JsonbBuilder.create().toJson(itemDTOS));
//                System.out.println(customerDTOS);

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = pool.getConnection()) {
            ItemDTO itemDTO = JsonbBuilder.create().fromJson(req.getReader(), ItemDTO.class);
            ItemDTO searchItem = itemBO.searchItem(connection, itemDTO.getId());

            if (searchItem != null) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);

            } else {
                if (itemBO.saveItem(itemDTO, connection)) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("Item saved successfully !!");

                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Failed to save item !!");
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = pool.getConnection()) {
            System.out.println(req.getParameter("id"));

            ItemDTO searchItem = itemBO.searchItem(connection, req.getParameter("id"));
            System.out.println("delete search" + searchItem);

            if (searchItem != null) {
                if (itemBO.deleteItem(req.getParameter("id"), connection)) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("Item Deleted successfully !!");
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                    resp.getWriter().write("Failed to delete item !!");
                }

            } else {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection=pool.getConnection()){
            ItemDTO itemDTO = JsonbBuilder.create().fromJson(req.getReader(), ItemDTO.class);
            System.out.println("update "+itemDTO);

            ItemDTO searchItem = itemBO.searchItem(connection, itemDTO.getId());

            if(searchItem!=null){
                if( itemBO.updateItem(itemDTO,connection)){
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("Item updated successfully !!");
                }else{
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Failed to update item !!");
                }
            }else{
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }


        }catch (Exception e){
            System.out.println(e);
            resp.getWriter().write(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }
}
