package epf.rentmanager.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

public class ReservationServiceTest {

    @Mock
    private ReservationDao reservationDao;

    private ReservationService reservationService;

    @Before
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(reservationDao);
    }

    @Test
    public void testCreateWithValidReservation() throws DaoException, ServiceException {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setClient_id(1L);
        reservation.setVehicle_id(1L);
        reservation.setDebut(LocalDate.parse("2022-01-01"));
        reservation.setFin(LocalDate.parse("2022-01-05"));

        when(reservationDao.create(reservation)).thenReturn(1L);

        long id = reservationService.create(reservation);

        verify(reservationDao).create(reservation);

        assertEquals(1L, id);
    }

    @Test(expected = ServiceException.class)
    public void testCreateWithInvalidReservation() throws DaoException, ServiceException {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setClient_id(1L);
        reservation.setVehicle_id(1L);

        reservationService.create(reservation);

        verifyNoMoreInteractions(reservationDao);
    }

    @Test
    public void testFindAll() throws DaoException {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());

        when(reservationDao.findAll()).thenReturn(reservations);

        List<Reservation> foundReservations = reservationService.findAll();

        verify(reservationDao).findAll();

        assertEquals(2, foundReservations.size());
    }

    @Test
    public void testDelete() throws DaoException, ServiceException {
        long id = 1L;

        when(reservationDao.delete(id)).thenReturn(1L);

        long deletedRows = reservationService.delete(id);

        verify(reservationDao).delete(id);

        assertEquals(1L, deletedRows);
    }

    @Test
    public void testFindResaByClientId() throws DaoException {
        long clientId = 1L;

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());

        when(reservationDao.findResaByClientId(clientId)).thenReturn(reservations);

        List<Reservation> foundReservations = reservationService.findResaByClientId(clientId);

        verify(reservationDao).findResaByClientId(clientId);

        assertEquals(2, foundReservations.size());
    }

    @Test
    public void testFindResaByVehicleId() throws DaoException {
        long vehicleId = 1L;

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());

        when(reservationDao.findResaByVehicleId(vehicleId)).thenReturn(reservations);

        List<Reservation> foundReservations = reservationService.findResaByVehicletId(vehicleId);

        verify(reservationDao).findResaByVehicleId(vehicleId);

        assertEquals(2, foundReservations.size());
    }

    @Test
    public void testFindById() throws DaoException {
        long id = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(id);

        when(reservationDao.findById(id)).thenReturn(reservation);

        Reservation foundReservation = reservationService.findById(id);

        verify(reservationDao).findById(id);

        assertEquals(id, foundReservation.getId());
    }

    @Test
    public void testUpdate() throws DaoException {
        Reservation reservation = new Reservation();
        reservation.setId(1L);

        reservationService.update(reservation);

        verify(reservationDao).update(reservation);
    }

    @Test
    public void testCount() throws ServiceException, DaoException {
        int count = 5;

        when(reservationDao.count()).thenReturn(count);

        int foundCount = reservationService.count();

        verify(reservationDao).count();

        assertEquals(count, foundCount);
    }
}
