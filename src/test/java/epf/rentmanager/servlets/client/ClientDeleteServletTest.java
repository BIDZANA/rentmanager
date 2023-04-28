package epf.rentmanager.servlets.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.ui.servlets.client.ClientDeleteServlet;
import org.junit.Before;
import org.junit.Test;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;

public class ClientDeleteServletTest {

    private ClientDeleteServlet clientDeleteServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ClientService clientService;

    @Before
    public void setUp() {
        clientDeleteServlet = new ClientDeleteServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        clientService = mock(ClientService.class);
        clientDeleteServlet.clientService = clientService;
    }

    @Test
    public void should_call_service_when_valid_id_provided() throws ServletException, IOException, ServiceException, DaoException {
        // Given
        Long id = 1L;
        when(request.getParameter("id")).thenReturn(id.toString());

        // When
        clientDeleteServlet.doGet(request, response);

        // Then
        verify(clientService).delete(id);
        verifyNoMoreInteractions(response);
    }

    @Test
    public void should_redirect_to_list_page_after_successful_deletion() throws ServletException, IOException, ServiceException, DaoException {
        // Given
        Long id = 1L;
        when(request.getParameter("id")).thenReturn(id.toString());

        // When
        clientDeleteServlet.doGet(request, response);

        // Then
        verify(clientService).delete(id);
        verify(response).sendRedirect("http://localhost:8080/rentmanager/users");
    }

    @Test
    public void should_handle_number_format_exception() throws ServletException, IOException {
        // Given
        when(request.getParameter("id")).thenReturn("invalid_id");

        // When
        clientDeleteServlet.doGet(request, response);

        // Then
        verifyNoMoreInteractions(clientService);
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals("Invalid parameter: id", response.getHeader("errorMessage"));
    }

    @Test
    public void should_rethrow_dao_exception_as_runtime_exception() throws ServletException, IOException, ServiceException, DaoException {
        // Given
        Long id = 1L;
        when(request.getParameter("id")).thenReturn(id.toString());
        doThrow(DaoException.class).when(clientService).delete(any(Long.class));

        // When - Then
        try {
            clientDeleteServlet.doGet(request, response);
        } catch (RuntimeException e) {
            assertEquals(DaoException.class, e.getCause().getClass());
        }
    }
}
