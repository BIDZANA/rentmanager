package com.epf.rentmanager.ui.servlets.reservation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.DaoException;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;


@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

	private static final long serialVersionUID = -6400136202534318065L;

	private VehicleService vehicleService = VehicleService.getInstance();
	private ClientService clientService = ClientService.getInstance();
	private ReservationService reservationService = ReservationService.getInstance();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
		try {
			final List<Client> users = clientService.findAll();
			final List<Vehicle> vehicles = vehicleService.findAll();
			request.setAttribute("users", users);
			request.setAttribute("vehicles", vehicles);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			reservationService.create(new Reservation(
					Long.parseLong(request.getParameter("client_id").toString()),
					Long.parseLong(request.getParameter("vehicle_id").toString()),
					LocalDate.parse(request.getParameter("debut").toString(), formatter),
					LocalDate.parse(request.getParameter("fin").toString(), formatter)
			));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		response.sendRedirect("../rents");
	}
}