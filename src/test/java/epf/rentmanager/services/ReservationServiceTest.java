package epf.rentmanager.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

class ReservationServiceTest {

    @Mock
    private ReservationDao reservationDao;

    private ReservationService reservationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.reservationService = new ReservationService(reservationDao);
    }

    @Test
    void testCreate() throws ServiceException, DaoException, SQLException {
        // On définit les paramètres
        long clientId = 1L;
        long vehicleId = 2L;
        LocalDate startTime = LocalDate.now();
        LocalDate endTime = LocalDate.now().plusDays(7);
        // On créé une nouvelle réservation
        Reservation reservation = new Reservation(clientId, vehicleId, startTime, endTime);
        // On configure le mock pour renvoyer la réservation enregistrée
        when(reservationDao.create(reservation)).thenReturn((long) 1);
        // On appelle la méthode create du service
        reservationService.create(clientId, vehicleId, startTime, endTime);
        // On vérifie que le DAO a bien été appelé avec la bonne réservation
        verify(reservationDao).create(reservation);
    }

    @Test
    void testFindAll() throws DaoException {
        // On définit la liste de réservations à renvoyer
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation1 = new Reservation(1L, 2L, LocalDate.now(), LocalDate.now().plusDays(7));
        Reservation reservation2 = new Reservation(3L, 4L, LocalDate.now().plusDays(7), LocalDate.now().plusDays(14));
        reservations.add(reservation1);
        reservations.add(reservation2);
        // On configure le mock pour renvoyer la liste de réservations
        when(reservationDao.findAll()).thenReturn(reservations);
        // On appelle la méthode findAll du service
        List<Reservation> result = reservationService.findAll();
        // On vérifie que le résultat renvoyé est bien celui attendu
        assertEquals(reservations, result);
    }

    @Test
    void testDelete() throws DaoException, ServiceException {
        long id = 1L;
        // On configure le mock pour renvoyer l'id supprimé
        when(reservationDao.delete(id)).thenReturn(1L);
        // On appelle la méthode delete du service
        reservationService.delete(id);
        // On vérifie que le DAO a bien été appelé avec l'id de la réservation à supprimer
        verify(reservationDao).delete(id);
    }

    @Test
    void testFindResaByClientId() throws DaoException {
        long clientId = 1L;
        // On définit la liste de réservations à renvoyer
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation1 = new Reservation(1L, 2L, LocalDate.now(), LocalDate.now().plusDays(7));
        Reservation reservation2 = new Reservation(3L, 4L, LocalDate.now().plusDays(7), LocalDate.now().plusDays(14));
        reservations.add(reservation1);
        reservations.add(reservation2);
        // On configure le mock pour renvoyer la liste de réservations
        when(reservationDao.findResaByClientId(clientId)).thenReturn(reservations);
        // On appelle la méthode findResaByClientId du service
        List<Reservation> result = reservationService.findResaByClientId(clientId);
        // On vérifie que le résultat renvoyé est bien celui attendu
        assertEquals(reservations, result);
    }

    @Test
    void testFindResaByVehicleId() throws DaoException {
        long vehicleId = 2L;
        // On définit la liste de réservations à renvoyer
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation1 = new Reservation(1L, 2L, LocalDate.now(), LocalDate.now().plusDays(7));
        Reservation reservation2 = new Reservation(3L, 4L, LocalDate.now().plusDays(7), LocalDate.now().plusDays(14));
        reservations.add(reservation1);
        reservations.add(reservation2);
        // On configure le mock pour renvoyer la liste de réservations
        when(reservationDao.findResaByVehicleId(vehicleId)).thenReturn(reservations);
        // On appelle la méthode findResaByVehicleId du service
        List<Reservation> result = reservationService.findResaByVehicletId(vehicleId);
        // On vérifie que le résultat renvoyé est bien celui attendu
        assertEquals(reservations, result);
    }

    @Test
    void testFindById() throws DaoException {
        long id = 1L;
        // On définit la réservation à renvoyer
        Reservation reservation = new Reservation(1L, 2L, LocalDate.now(), LocalDate.now().plusDays(7));
        // On configure le mock pour renvoyer la réservation
        when(reservationDao.findById(id)).thenReturn(reservation);
        // On appelle la méthode findById du service
        Reservation result = reservationService.findById(id);
        // On vérifie que le résultat renvoyé est bien celui attendu
        assertEquals(reservation, result);
    }

    @Test
    void testUpdate() throws ServiceException, DaoException {
        long id = 1L;
        long clientId = 2L;
        long vehicleId = 3L;
        LocalDate startTime = LocalDate.now();
        LocalDate endTime = LocalDate.now().plusDays(7);
        // On créé une nouvelle réservation
        Reservation reservation = new Reservation(id, clientId, vehicleId, startTime, endTime);
        // On configure le mock pour ne rien retourner
        doNothing().when(reservationDao).update(reservation);
        // On appelle la méthode update du service
        reservationService.update(id, clientId, vehicleId, startTime, endTime);
        // On vérifie que le DAO a bien été appelé avec la bonne réservation
        verify(reservationDao).update(reservation);
    }

    @Test
    void testCount() throws ServiceException, DaoException {
        // On configure le mock pour renvoyer le nombre de réservations
        when(reservationDao.count()).thenReturn(10);
        // On appelle la méthode count du service
        int result = reservationService.count();
        // On vérifie que le résultat renvoyé est bien celui attendu
        assertEquals(10, result);
    }
}
