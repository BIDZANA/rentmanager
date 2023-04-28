package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    public ReservationService(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
    }

    public void create(long client_Id, long vehicle_Id, LocalDate startTime, LocalDate endTime) throws ServiceException {
        Reservation reservation = new Reservation(client_Id, vehicle_Id, startTime, endTime);
        try {
            reservationDao.create(reservation);
        } catch (Exception e) {
            throw new ServiceException("Problem when creating the reservation " + e.getMessage());
        }
    }

    public List<Reservation> findAll() throws DaoException {
        try {
            return this.reservationDao.findAll();
        }catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long delete(Long reservation_id) throws ServiceException, DaoException {
        try {
            return reservationDao.delete(reservation_id);
        }catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Reservation> findResaByClientId(long clientId) throws DaoException {
        try {
            return this.reservationDao.findResaByClientId(clientId);
        }catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Reservation> findResaByVehicletId(long vehicleId) throws DaoException {
        try {
            return this.reservationDao.findResaByVehicleId(vehicleId);
        }catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Reservation findById(long id) throws DaoException {
        try {
            return this.reservationDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(long id, long client_id, long vehicle_id, LocalDate startTime, LocalDate endTime) throws ServiceException {
        Reservation reservation = new Reservation(id, client_id, vehicle_id, startTime, endTime);
        try {
            reservationDao.update(reservation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Problem when updating the reservation " + e.getMessage());
        }
    }

    public int count() throws ServiceException {
        try {
            return this.reservationDao.count();
        }catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
