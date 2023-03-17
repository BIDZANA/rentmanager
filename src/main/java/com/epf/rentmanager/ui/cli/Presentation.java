package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.utils.IOUtils;

public class Presentation {
  public static void Application() throws ServiceException, DaoException {

      UserController userController = new UserController();

      //Acceuil
      userController.accueil();

      boolean connected = true;
      do{
          int choix = userController.menu(); //Menu
          switch (choix){ //Gestion des clients et des véhicules
              case 0:
                  IOUtils.print("Fermeture du programme...");
                  connected = false;
                  break;
              case 1:
                  long idClient = userController.createClient();
                  if (idClient!=0){IOUtils.print("Echec de création!");}
                  else IOUtils.print(String.valueOf(idClient));
                  break;
              case 2:
                  IOUtils.print("liste des clients: ");
                  System.out.println(userController.listAllClients());
                  break;
              case 3:
                  long result = userController.deleteClient();
                  if (result<=0){IOUtils.print("Echec de suppression!");}
                  break;
              case 4:
                  long idVehicle = userController.createVehicle();
                  if (idVehicle!=0){IOUtils.print("Echec de création!");}
                  else IOUtils.print(String.valueOf(idVehicle));
                  break;
              case 5:
                  IOUtils.print("Liste des véhicules: ");
                  System.out.println(userController.ListAllVehicles());
                  break;
              case 6:
                  if (userController.deleteVehicle() <= 0){IOUtils.print("Echec de suppression!");}
                  else {IOUtils.print("Véhicule supprimé");}
                  break;
              case 7:
                  // 7-Créer une réservation
                  long idReservation = userController.createReservation();
                  if (idReservation!=0){IOUtils.print("Echec de création!");}
                  else IOUtils.print(String.valueOf(idReservation));
                  break;
              case 8:
                  IOUtils.print("Liste des réservations: ");
                  System.out.println(userController.ListAllReservations());
                  break;
              case 9:
                  //Lister toutes les Réservations associées à un client
                  System.out.println(userController.rechercherReservationParCientId());
                  break;
              case 10:
                  // 10-Lister toutes les Réservations d'un véhicule donné
                  System.out.println(userController.rechercherReservationParVehicletId());
                  break;
              case 11:
                  // 11-Supprimer une réservation
                  long ok = userController.deleteReservation();
                  if (ok<=0){IOUtils.print("Echec de suppression!");}
                  else {IOUtils.print("Réservation supprimé");}
                  break;
              default:
                  IOUtils.print("Choix Invalide!");
                  break;
          }
      }while (connected);
  }
}
