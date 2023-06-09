package com.epf.rentmanager.ui.servlets.reservation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet("/rents/delete")
public class ReservationDeleteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ReservationDeleteServlet() {
	}

	@Autowired
	ReservationService reservationService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			reservationService.delete(Long.parseLong(request.getParameter("id")));
		} catch (NumberFormatException | ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
		response.sendRedirect("../rents");
	}
}