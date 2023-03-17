package com.epf.rentmanager.ui.servlets.reservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5739237977544365030L;
	private final ClientService clientService = ClientService.getInstance();
	private final VehicleService vehicleService = VehicleService.getInstance();
	private final ReservationService reservationService = ReservationService.getInstance();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/list.jsp");
		try {
			List<ReservationRow> rows = new ArrayList<>();
	        for (Reservation reservation : reservationService.findAll()) {
	        	rows.add(new ReservationRow(reservation, clientService, vehicleService));
	        }
			System.out.println(rows);
			request.setAttribute("rents", rows);
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}