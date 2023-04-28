package epf.rentmanager.services;


import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {

    private VehicleService vehicleService;

    @Mock
    private VehicleDao vehicleDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        vehicleService = new VehicleService(vehicleDao);
    }

    @Test
    public void create_should_return_vehicle_id() throws DaoException, ServiceException {
        Vehicle vehicle = new Vehicle();
        vehicle.setConstructeur("Renault");
        vehicle.setModele("Clio");
        vehicle.setNb_places(5);

        when(vehicleDao.create(vehicle)).thenReturn(1L);

        long id = vehicleService.create(vehicle);

        verify(vehicleDao, times(1)).create(vehicle);
        assertEquals(id, 1L);
    }

    @Test(expected = ServiceException.class)
    public void create_should_throw_service_exception_when_dao_exception_occurs() throws DaoException, ServiceException {
        Vehicle vehicle = new Vehicle();
        vehicle.setConstructeur("Renault");
        vehicle.setModele("Clio");
        vehicle.setNb_places(5);

        doThrow(DaoException.class).when(vehicleDao).create(vehicle);

        vehicleService.create(vehicle);
    }

    @Test
    public void create_should_return_zero_when_entries_are_invalid() throws DaoException, ServiceException {
        Vehicle vehicle = new Vehicle();
        vehicle.setConstructeur("Renault");
        vehicle.setModele("Clio");
        vehicle.setNb_places(1);

        long id = vehicleService.create(vehicle);

        verify(vehicleDao, never()).create(vehicle);
        assertEquals(0L, id);
    }

    @Test
    public void findById_should_return_vehicle() throws DaoException, ServiceException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setConstructeur("Renault");
        vehicle.setModele("Clio");
        vehicle.setNb_places(5);

        when(vehicleDao.findById(1L)).thenReturn(Optional.of(vehicle));

        Vehicle foundVehicle = vehicleService.findById(1L);

        verify(vehicleDao, times(1)).findById(1L);
        assertEquals(foundVehicle.getId(), 1L);
        assertEquals(foundVehicle.getConstructeur(), "Renault");
        assertEquals(foundVehicle.getModele(), "Clio");
        assertEquals(foundVehicle.getNb_places(), 5);
    }

    @Test(expected = ServiceException.class)
    public void findById_should_throw_service_exception_when_dao_exception_occurs() throws DaoException, ServiceException {
        doThrow(DaoException.class).when(vehicleDao).findById(1L);
        vehicleService.findById(1L);
    }

    @Test
    public void findAll_should_return_list_of_vehicles() throws DaoException, ServiceException {
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setConstructeur("Renault");
        vehicle1.setModele("Clio");
        vehicle1.setNb_places(5);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        vehicle2.setConstructeur("Peugeot");
        vehicle2.setModele("208");
        vehicle2.setNb_places(4);

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);

        when(vehicleDao.findAll()).thenReturn(vehicles);

        List<Vehicle> foundVehicles = vehicleService.findAll();

        verify(vehicleDao, times(1)).findAll();
        assertEquals(foundVehicles.get(0).getId(), 1L);
        assertEquals(foundVehicles.get(0).getConstructeur(), "Renault");
        assertEquals(foundVehicles.get(0).getModele(), "Clio");
        assertEquals(foundVehicles.get(0).getNb_places(), 5);
        assertEquals(foundVehicles.get(1).getId(), 2L);
        assertEquals(foundVehicles.get(1).getConstructeur(), "Peugeot");
        assertEquals(foundVehicles.get(1).getModele(), "208");
        assertEquals(foundVehicles.get(1).getNb_places(), 4);
    }

    @Test(expected = ServiceException.class)
    public void findAll_should_throw_service_exception_when_dao_exception_occurs() throws DaoException, ServiceException {
        doThrow(DaoException.class).when(vehicleDao).findAll();
        vehicleService.findAll();
    }

    @Test
    public void delete_should_return_one_when_deletion_done() throws DaoException, ServiceException {
        when(vehicleDao.delete(1L)).thenReturn(1L);
        long id = vehicleService.delete(1L);
        verify(vehicleDao, times(1)).delete(1L);
        assertEquals(id, 1L);
    }

    @Test(expected = ServiceException.class)
    public void delete_should_throw_service_exception_when_dao_exception_occurs() throws DaoException, ServiceException {
        doThrow(DaoException.class).when(vehicleDao).delete(1L);
        vehicleService.delete(1L);
    }

    @Test
    public void findByClient_should_return_list_of_vehicles() throws DaoException, ServiceException {
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setConstructeur("Renault");
        vehicle1.setModele("Clio");
        vehicle1.setNb_places(5);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        vehicle2.setConstructeur("Peugeot");
        vehicle2.setModele("208");
        vehicle2.setNb_places(4);

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);

        when(vehicleDao.findByClientId(1L)).thenReturn(vehicles);

        List<Vehicle> foundVehicles = vehicleService.findByClient(1L);

        verify(vehicleDao, times(1)).findByClientId(1L);
        assertEquals(foundVehicles.get(0).getId(), 1L);
        assertEquals(foundVehicles.get(0).getConstructeur(), "Renault");
        assertEquals(foundVehicles.get(0).getModele(), "Clio");
        assertEquals(foundVehicles.get(0).getNb_places(), 5);
        assertEquals(foundVehicles.get(1).getId(), 2L);
        assertEquals(foundVehicles.get(1).getConstructeur(), "Peugeot");
        assertEquals(foundVehicles.get(1).getModele(), "208");
        assertEquals(foundVehicles.get(1).getNb_places(), 4);
    }

    @Test(expected = ServiceException.class)
    public void findByClient_should_throw_service_exception_when_dao_exception_occurs() throws DaoException, ServiceException {
        doThrow(DaoException.class).when(vehicleDao).findByClientId(1L);
        vehicleService.findByClient(1L);
    }

    @Test
    public void count_should_return_count_of_vehicles() throws DaoException, ServiceException {
        when(vehicleDao.count()).thenReturn(5);

        int count = vehicleService.count();

        verify(vehicleDao, times(1)).count();
        assertEquals(count, 5);
    }

    @Test(expected = ServiceException.class)
    public void count_should_throw_service_exception_when_dao_exception_occurs() throws DaoException, ServiceException {
        doThrow(DaoException.class).when(vehicleDao).count();
        vehicleService.count();
    }

    @Test
    public void update_should_return_updated_vehicle_id() throws DaoException, ServiceException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setConstructeur("Renault");
        vehicle.setModele("Clio");
        vehicle.setNb_places(5);

        when(vehicleDao.update(vehicle)).thenReturn(1L);

        long id = vehicleService.update(vehicle);

        verify(vehicleDao, times(1)).update(vehicle);
        assertEquals(id, 1L);
    }

    @Test(expected = ServiceException.class)
    public void update_should_throw_service_exception_when_dao_exception_occurs() throws DaoException, ServiceException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setConstructeur("Renault");
        vehicle.setModele("Clio");
        vehicle.setNb_places(5);

        doThrow(DaoException.class).when(vehicleDao).update(vehicle);

        vehicleService.update(vehicle);
    }
}
