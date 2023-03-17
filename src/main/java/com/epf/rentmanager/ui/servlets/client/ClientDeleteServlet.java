package com.epf.rentmanager.ui.servlets.client;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/delete")
public class ClientDeleteServlet extends HttpServlet {

    private ClientService clientService = ClientService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            clientService.delete(Long.parseLong(request.getParameter("id")));
        } catch (NumberFormatException | ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("../users");
    }
}
