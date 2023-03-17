package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;


public class UserController {

    private ClientService clientService = ClientService.getInstance();
    private VehicleService vehicleService = VehicleService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    public UserController() {

    }

    public long createClient() throws DaoException, ServiceException {
        Client createdClient = new Client();
        createdClient.setNom(IOUtils.readString("Veuillez saisir le nom du client: ", true));
        createdClient.setPrenom(IOUtils.readString("Veuilliez saisir le prénom du client: ", true));
        String regex; String email;
        do {
            IOUtils.print("Entrez un email valide: ");
            email = IOUtils.readString();
            regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        } while (!email.matches(regex));
        createdClient.setEmail(email);
        createdClient.setNaissance(IOUtils.readDate("Veuillez saisir votre date de naissance: ",true));
        return clientService.create(createdClient);
    }

    public List<Client> listAllClients() throws DaoException, ServiceException{
        return clientService.findAll();
    }

    public Client rechercherClientParId(long id) throws DaoException, ServiceException{
        return clientService.findById(id);
    }

    public long deleteClient() throws DaoException, ServiceException{
        Client deletedClient = new Client();
        deletedClient.setId(IOUtils.readInt("Veuillez saisir l'id du client à supprimer: "));
        return clientService.delete(deletedClient.id);
    }

    public long createVehicle() throws DaoException, ServiceException{
        Vehicle createdVehicle = new Vehicle();
        createdVehicle.setConstructeur(IOUtils.readString("Veuillez saisir le constructeur: ",true));
        createdVehicle.setNb_places(IOUtils.readInt("Veuillez saisir le nombre de places: "));
        return vehicleService.create(createdVehicle);
    }

    public List<Vehicle> ListAllVehicles() throws DaoException, ServiceException{
        return vehicleService.findAll();
    }

    public long deleteVehicle() throws DaoException, ServiceException{
        Vehicle createdVehicle = new Vehicle();
        createdVehicle.setId(IOUtils.readInt("Veuillez saisir l'id du véhicule à supprimer: "));
        return vehicleService.delete(createdVehicle);
    }

    public long createReservation() throws DaoException, ServiceException{
        Reservation reservation = new Reservation();
        LocalDate now = LocalDate.now(); // Obtenir le jour actuel
        reservation.setClient_id(IOUtils.readInt("Entrez l'id du client qui souhaite effectuer une réservation: "));
        reservation.setVehicle_id(IOUtils.readInt("Entrez l'id du véhicule à réserver: "));
        LocalDate debut = IOUtils.readDate("Entrez la date du début de la réservation: ",true);
        LocalDate fin = IOUtils.readDate("Entrez la date de fin de la réservation: ",true);
        // Vérifions que la date de fin est supérieure à la date de début.
        if(debut.isAfter(fin)){
            IOUtils.print("La date de début doit être avant la date de fin.");
        }else{
            // Vérifions que les deux dates sont supérieures ou égales à la date actuelle.
            if(now.isAfter(debut) || now.isAfter(fin)){
                IOUtils.print("Les dates demandées ne peuvent pas être inférieures à la date actuelle.");
            }else{
                reservation.setDebut(debut);
                reservation.setFin(fin);
                return reservationService.create(reservation);
            }
        }
        return 1;
    }

    public List<Reservation> ListAllReservations() throws DaoException, ServiceException{
        return reservationService.findAll();
    }

    public List<Reservation> rechercherReservationParCientId() throws DaoException, ServiceException{
        long clientId = IOUtils.readInt("Veuillez saisir l'id du client: ");
        List<Reservation> reservations = reservationService.findResaByClientId(clientId);
        if (reservations.isEmpty()){
            IOUtils.print("Aucune réservation n'est associée à ce client!");
            return null;
        }
        return reservations;
    }

    public List<Reservation> rechercherReservationParVehicletId() throws DaoException, ServiceException{
        long vehicleId = IOUtils.readInt("Veuillez saisir l'id du véhicule: ");
        List<Reservation> reservations = reservationService.findResaByVehicletId(vehicleId);
        if (reservations.isEmpty()){
            IOUtils.print("Aucune réservation n'est associée à ce client!");
            return null;
        }
        return reservations;
    }

    public long deleteReservation() throws DaoException, ServiceException{
        Reservation reservation = new Reservation();
        reservation.setId(IOUtils.readInt("Veuillez saisir l'id du véhicule à supprimer: "));
        return reservationService.delete(reservation);
    }

    public void accueil(){
        IOUtils.print("---------------------------------------------------");
        IOUtils.print("        \uD83D\uDE97 BIENVENUE SUR L'APPLICATION \uD83D\uDE99");
        IOUtils.print("---------------------------------------------------\n");
        IOUtils.print("                       \uD83D\uDC68\u200D\uD83D\uDCBB                 ");
        IOUtils.print("  (Vous êtes connecté en tant qu'administrateur) \n");
    }

    public int menu(){
    IOUtils.print(
        "Menu \n 1-Créer un client \n 2-lister tous les clients \n 3-Supprimer un Client \n 4-Créer un Véhicule \n 5-Lister tous les Véhicules \n 6-Supprimer un Véhicule \n 7-Créer une réservation \n 8-Lister toutes les Réservations \n 9-Lister toutes les Réservations associées à un client \n 10-Lister toutes les Réservations d'un véhicule donné \n 11-Supprimer une réservation  \n 0-Quitter"
    );
        return IOUtils.readInt("Saisissez un nombre: ");
    }
}
