package com.epf.rentmanager.ui.servlets.client;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ClientCreateServlet(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Récupération des paramètres du formulaire
            String lastName = request.getParameter("last_name");
            String firstName = request.getParameter("first_name");
            String email = request.getParameter("email");
            LocalDate birthDate = LocalDate.parse(request.getParameter("birth_date"), formatter);

            // Calcul de l'âge du client
            LocalDate now = LocalDate.now();
            int age = now.getYear() - birthDate.getYear();
            if (now.getMonthValue() < birthDate.getMonthValue()
                    || (now.getMonthValue() == birthDate.getMonthValue() && now.getDayOfMonth() < birthDate.getDayOfMonth())) {
                age--;
            }

            // Vérification que le client a au moins 18 ans
            if (age < 18) {
                throw new Exception("Le client doit avoir au moins 18 ans.");
            }

            // Vérification que l'e-mail n'est pas déjà utilisé par un autre client
            List<Client> clients = clientService.findAll();
            for (Client client : clients) {
                if (client.getEmail().equals(email)) {
                    throw new Exception("Cette adresse e-mail est déjà utilisée par un autre client.");
                }
            }

            // Vérification que le nom et le prénom ont au moins 3 caractères
            if (lastName.length() < 3 || firstName.length() < 3) {
                throw new Exception("Le nom et le prénom doivent faire au moins 3 caractères.");
            }

            // Création du client
            clientService.create(new Client(lastName, firstName, email, birthDate));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/users");
    }
}
