package epf.rentmanager.servlets.client;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.ui.servlets.client.ClientCreateServlet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

public class ClientCreateServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientCreateServlet clientCreateServlet;

    private List<Client> clients;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(request.getRequestDispatcher("/WEB-INF/views/users/create.jsp")).thenReturn(dispatcher);

        clientCreateServlet = new ClientCreateServlet(clientService);

        clients = new ArrayList<>();
        clients.add(new Client("Doe", "John", "john.doe@example.com", LocalDate.of(1990, 1, 1)));
        clients.add(new Client("Smith", "Jane", "jane.smith@example.com", LocalDate.of(1995, 2, 2)));
    }

    @Test
    public void testDoPost_successful() throws ServletException, IOException, ServiceException, DaoException {
        // Préparation des paramètres de la requête
        when(request.getParameter("last_name")).thenReturn("Doe");
        when(request.getParameter("first_name")).thenReturn("Jane");
        when(request.getParameter("email")).thenReturn("jane.doe@example.com");
        when(request.getParameter("birth_date")).thenReturn("03/03/1998");

        // Préparation du service de clients
        when(clientService.findAll()).thenReturn(clients);

        // Appel de la méthode doPost
        clientCreateServlet.doPost(request, response);

        // Vérification que le nouveau client a été créé
        verify(clientService).create(new Client("Doe", "Jane", "jane.doe@example.com", LocalDate.of(1998, 3, 3)));

        // Vérification que la redirection est correcte
        verify(response).sendRedirect("http://localhost:8080/rentmanager/users");
    }

    @Test
    public void testDoPost_invalidAge() throws ServletException, IOException, ServiceException, DaoException {
        // Préparation des paramètres de la requête avec un âge insuffisant (17 ans)
        when(request.getParameter("last_name")).thenReturn("Doe");
        when(request.getParameter("first_name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john.doe@example.com");
        when(request.getParameter("birth_date")).thenReturn("01/01/2005");

        // Appel de la méthode doPost
        clientCreateServlet.doPost(request, response);

        // Vérification que le nouveau client n'a pas été créé
        verify(clientService, Mockito.never()).create(Mockito.any(Client.class));

        // Vérification que la redirection n'a pas eu lieu
        verify(response, Mockito.never()).sendRedirect(Mockito.anyString());
    }

    @Test
    public void testDoPost_duplicateEmail() throws ServletException, IOException, ServiceException, DaoException {
        // Préparation des paramètres de la requête avec un email déjà utilisé
        when(request.getParameter("last_name")).thenReturn("Doe");
        when(request.getParameter("first_name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("jane.smith@example.com"); // email déjà utilisé par un autre client
        when(request.getParameter("birth_date")).thenReturn("01/01/2000");

        // Préparation du service de clients
        when(clientService.findAll()).thenReturn(clients);

        // Appel de la méthode doPost
        clientCreateServlet.doPost(request, response);

        // Vérification que le nouveau client n'a pas été créé
        verify(clientService, Mockito.never()).create(Mockito.any(Client.class));

        // Vérification que la redirection n'a pas eu lieu
        verify(response, Mockito.never()).sendRedirect(Mockito.anyString());
    }

    @Test
    public void testDoPost_invalidName() throws ServletException, IOException, ServiceException, DaoException {
        // Préparation des paramètres de la requête avec un nom/prénom trop court (2 caractères)
        when(request.getParameter("last_name")).thenReturn("Do");
        when(request.getParameter("first_name")).thenReturn("Jo");
        when(request.getParameter("email")).thenReturn("john.doe@example.com");
        when(request.getParameter("birth_date")).thenReturn("01/01/2000");

        // Appel de la méthode doPost
        clientCreateServlet.doPost(request, response);

        // Vérification que le nouveau client n'a pas été créé
        verify(clientService, Mockito.never()).create(Mockito.any(Client.class));

        // Vérification que la redirection n'a pas eu lieu
        verify(response, Mockito.never()).sendRedirect(Mockito.anyString());
    }

}
