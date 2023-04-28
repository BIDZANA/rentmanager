package com.epf.rentmanager.ui.servlets.reservation;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

@WebServlet("/rents/edit")
public class ReservationEditServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Autowired private ReservationService reservationService;

  @Autowired private ClientService clientService;

  @Autowired private VehicleService vehicleService;

  @Override
  public void init() throws ServletException {
    super.init();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      Reservation resa = reservationService.findById(Long.parseLong(request.getParameter("id")));
      Vehicle car = vehicleService.findById(resa.getVehicle_id());
      Client user = clientService.findById(resa.getClient_id());
      String carName = car.getConstructeur() + " " + car.getModele();
      String userName = user.getNom() + " " + user.getPrenom();
      request.setAttribute("reservation", resa);
      request.setAttribute("vehicle", carName);
      request.setAttribute("client", userName);
    } catch (DaoException e) {
      throw new RuntimeException(e);
    } catch (ServiceException e) {
      throw new RuntimeException(e);
    }

    this.getServletContext()
        .getRequestDispatcher("/WEB-INF/views/rents/edit.jsp")
        .forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    long id = Long.parseLong(request.getParameter("id"));

    String client_Id = request.getParameter("client");
    String vehicle_Id = request.getParameter("vehicle");
    String begin = request.getParameter("startTime");
    String end = request.getParameter("endTime");

    // vérification des paramètres
    if (client_Id == null || vehicle_Id == null || begin == null || end == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
      return;
    }

    long client_id, vehicle_id;
    LocalDate startTime, endTime;

    try {
      client_id = Long.parseLong(client_Id);
      vehicle_id = Long.parseLong(vehicle_Id);
    } catch (NumberFormatException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid client or vehicle id");
      return;
    }

    try {
      startTime = LocalDate.parse(begin);
      endTime = LocalDate.parse(end);
    } catch (DateTimeParseException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
      return;
    }
    if (startTime.isAfter(endTime)) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Start date must be before end date");
      return;
    }

    try {
      reservationService.update(id, client_id, vehicle_id, startTime, endTime);
    } catch (ServiceException e) {
      throw new RuntimeException(e);
    }

    response.sendRedirect(request.getContextPath() + "/rents");
  }
}