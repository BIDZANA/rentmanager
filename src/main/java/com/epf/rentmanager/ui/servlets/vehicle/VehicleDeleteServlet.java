package com.epf.rentmanager.ui.servlets.vehicle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/cars/delete")
public class VehicleDeleteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4325359811604254645L;

	private VehicleService vehicleService = VehicleService.getInstance();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Vehicle vehicle = vehicleService.findById(Long.parseLong(request.getParameter("id")));
			vehicleService.delete(vehicle);
		} catch (NumberFormatException | ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
		response.sendRedirect("../cars");
	}
}
