package com.epf.rentmanager.ui.servlets.client;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/users/edit")
public class ClientEditServlet {
    private final ClientService clientService = ClientService.getInstance();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object parameter = request.getAttribute("id");
        long id = Long.parseLong((String) parameter);
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
        try {
            request.setAttribute("user", clientService.findById(id));
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            clientService.update(new Client(
                    Long.parseLong(request.getParameter("id")),
                    request.getParameter("nom"),
                    request.getParameter("prenom"),
                    request.getParameter("email"),
                    LocalDate.parse(request.getParameter("naissance"), formatter)
            ));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("../users");
    }
}
