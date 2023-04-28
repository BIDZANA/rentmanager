package epf.rentmanager.services;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateClient() throws ServiceException, DaoException, SQLException {
        Client client = new Client();
        client.setNom("DOE");
        client.setPrenom("John");
        when(clientDao.create(any(Client.class))).thenReturn(1L);
        clientService.create(client);
        verify(clientDao, times(1)).create(any(Client.class));
    }

    @Test
    public void testFindById() throws ServiceException, DaoException{
        Client client = new Client();
        client.setId((long) 1);
        Optional<Client> optional = Optional.of(client);
        when(clientDao.findById(anyLong())).thenReturn(optional);
        clientService.findById(1L);
        verify(clientDao, times(1)).findById(anyLong());
    }

    @Test
    public void testFindAll() throws ServiceException, DaoException{
        List<Client> clients = new ArrayList<>();
        when(clientDao.findAll()).thenReturn(clients);
        clientService.findAll();
        verify(clientDao, times(1)).findAll();
    }

    @Test
    public void testDelete() throws ServiceException, DaoException{
        Long clientId = 1L;
        when(clientDao.delete(anyLong())).thenReturn(1L);
        clientService.delete(clientId);
        verify(clientDao, times(1)).delete(anyLong());
    }

    @Test
    public void testFindClientByVehicleId() throws ServiceException, DaoException {
        List<Client> clients = new ArrayList<>();
        when(clientDao.findByVehicleId(anyLong())).thenReturn(clients);
        clientService.findClientByVehicleId(1L);
        verify(clientDao, times(1)).findByVehicleId(anyLong());
    }

    @Test
    public void testCount() throws ServiceException, DaoException {
        when(clientDao.count()).thenReturn(5);
        clientService.count();
        verify(clientDao, times(1)).count();
    }
}
