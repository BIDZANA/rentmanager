package com.epf.rentmanager.ui.servlets.client;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {

    private final VehicleService vehicleService = VehicleService.getInstance();

    private final ClientService clientService = ClientService.getInstance();

    private final ReservationService reservationService = ReservationService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/details.jsp");
        try {
            final Long id = Long.parseLong(request.getParameter("id"));
            final List<Reservation> reservations = reservationService.findResaByClientId(id);
            final List<Vehicle> vehicles = vehicleService.findByClient(id);
            request.setAttribute("user", clientService.findById(id));
            request.setAttribute("reservations", reservations);
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("countr", reservations.size());
            request.setAttribute("countv", vehicles.size());
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
