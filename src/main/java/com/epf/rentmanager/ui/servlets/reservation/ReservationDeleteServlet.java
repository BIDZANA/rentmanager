package com.epf.rentmanager.ui.servlets.reservation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;


@WebServlet("/rents/delete")
public class ReservationDeleteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4563944459899516421L;
	private ReservationService reservationService = ReservationService.getInstance();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
/*	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// reservationService.delete(Long.parseLong(request.getParameter("id").toString()));
		} catch (NumberFormatException | ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		response.sendRedirect("../rents");
	} */
}