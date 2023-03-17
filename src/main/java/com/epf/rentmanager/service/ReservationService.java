package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import java.util.List;

public class ReservationService {
    //Reservation(long id, long client_id, long vehicule_id, LocalDate debut, LocalDate fin)

    private ReservationDao reservationDao;
    public static ReservationService instance;
    public ReservationService(){this.reservationDao = ReservationDao.getInstance();}

    public static ReservationService getInstance(){
        if (instance == null){
            instance = new ReservationService();
        }
        return instance;
    }

    public long create(Reservation reservation) throws DaoException, ServiceException {
        try{
            if (reservation.getDebut() != null && reservation.getFin() != null){
                return reservationDao.create(reservation);
            }
        }catch (Exception e){
            throw new ServiceException("erreur!");
        }
        System.out.println("Entrées invalides!");
        return 0;
    }

    public List<Reservation> findAll() throws DaoException {
        return reservationDao.findAll();
    }

    public long delete(Reservation reservation) throws ServiceException, DaoException {
        return reservationDao.delete(reservation);
    }

    public List<Reservation> findResaByClientId(long clientId) throws DaoException {
        return reservationDao.findResaByClientId(clientId);
    }

    public List<Reservation> findResaByVehicletId(long vehicleId) throws DaoException {
        return reservationDao.findResaByVehicleId(vehicleId);
    }

    public Reservation findById(long id) throws DaoException {
        return reservationDao.findById(id);
    }

    public void update(Reservation reservation) throws DaoException {
        reservationDao.update(reservation);
    }
}
