package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final VehicleDao vehicleDao = VehicleDao.getInstance();
	private final ClientDao clientDao = ClientDao.getInstance();
	private final ReservationDao reservationDao = ReservationDao.getInstance();

	@Override
	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		try {
			final int vehicleCount = vehicleDao.count();
			final int clientCount = clientDao.count();
			final int rentCount = reservationDao.count();
			request.setAttribute("vehicleCount", vehicleCount);
			request.setAttribute("clientCount", clientCount);
			request.setAttribute("rentCount", rentCount);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		dispatcher.forward(request, response);
	}
}


