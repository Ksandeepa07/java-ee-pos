package lk.ijse.gdse.posbackend.api;

import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.posbackend.bo.BOFactory;
import lk.ijse.gdse.posbackend.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.posbackend.dto.CustomerDTO;
import org.w3c.dom.ls.LSOutput;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "customer", value = "/customer")
public class CustomerServlet extends HttpServlet {

    DataSource pool;
    CustomerBOImpl customerBO = BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);


    public void init() {

        try {
            pool = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/TestDB");
            System.out.println(pool.getConnection());
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = pool.getConnection()) {

            CustomerDTO customerDTO = JsonbBuilder.create().fromJson(req.getReader(), CustomerDTO.class);
            CustomerDTO searchCustomer = customerBO.searchCustomer(connection, customerDTO.getId());
            System.out.println("ddd" + searchCustomer);

            if (searchCustomer != null) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);

            } else {
                if (customerBO.saveCustomer(customerDTO, connection)) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("Customer saved successfully !!");
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Failed to save customer !!");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("tyoe" + request.getParameter("method"));

        if (request.getParameter("method").equals("getAll")) {
            try (Connection connection = pool.getConnection()) {
                ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);
                if (allCustomers != null) {
                    System.out.println(allCustomers);
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    response.getWriter().write(JsonbBuilder.create().toJson(allCustomers));

                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                    response.getWriter().write("Failed to retrieve customers !!");
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        } else if (request.getParameter("method").equals("search")) {
            System.out.println(request.getParameter("name"));
            try (Connection connection = pool.getConnection()) {
                ArrayList<CustomerDTO> customerDTOS = customerBO.liveSearch(connection, request.getParameter("name"));
                response.setContentType("application/json");
                response.getWriter().write(JsonbBuilder.create().toJson(customerDTOS));
                System.out.println(customerDTOS);

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = pool.getConnection()) {
            System.out.println(req.getParameter("id"));

            CustomerDTO searchCustomer = customerBO.searchCustomer(connection, req.getParameter("id"));
            System.out.println("ddd" + searchCustomer);

            if (searchCustomer != null) {
                if (customerBO.deleteCustomer(req.getParameter("id"), connection)) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("Customer Deleted successfully !!");
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                    resp.getWriter().write("Failed to delete customer !!");
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
            CustomerDTO customerDTO = JsonbBuilder.create().fromJson(req.getReader(), CustomerDTO.class);
            System.out.println("update"+customerDTO);

            CustomerDTO searchCustomer = customerBO.searchCustomer(connection, customerDTO.getId());

            if(searchCustomer!=null){
                if( customerBO.updateCustomer(customerDTO,connection)){
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("Customer updated successfully !!");
                }else{
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Failed to update customer !!");
                }
            }else{
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }


        }catch (Exception e){
            System.out.println(e);
            resp.getWriter().write(HttpServletResponse.SC_BAD_GATEWAY);
        }

    }

    public void destroy() {
    }
}