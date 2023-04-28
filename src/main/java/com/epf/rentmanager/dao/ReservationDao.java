package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_ID_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS count FROM Reservation";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin = ? WHERE id=?;";

	public long create(Reservation reservation) throws DaoException, SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY);

			preparedStatement.setLong(1,reservation.getClient_id());
			preparedStatement.setLong(2,reservation.getVehicle_id());
			preparedStatement.setString(3,String.valueOf(Date.valueOf(reservation.getDebut())));
			preparedStatement.setString(4, String.valueOf(Date.valueOf(reservation.getFin())));

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Error while creating a reservation" + e.getMessage());
		} finally {
			preparedStatement.close();
			connection.close();
		}
		return reservation.getId();
	}
	
	public long delete(Long id) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			preparedStatement.setLong(1,id);
			long result = preparedStatement.executeUpdate(); //afin de vérifier la suppression
			preparedStatement.close();
			connection.close();
			return result;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setLong(1,clientId);
			ResultSet resultats = preparedStatement.executeQuery();
			ArrayList<Reservation> reservations = new ArrayList<>();
			while (resultats.next()) {
				long id = resultats.getLong("id");
				long client_id = resultats.getLong("client_id");
				long vehicle_id = resultats.getLong("vehicle_id");
				LocalDate debut = resultats.getDate("debut").toLocalDate();
				LocalDate fin = resultats.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, client_id,vehicle_id,debut,fin);
				reservations.add(reservation);
			}
			return reservations;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setLong(1,vehicleId);
			ResultSet resultats = preparedStatement.executeQuery();
			ArrayList<Reservation> reservations = new ArrayList<>();
			while (resultats.next()) {
				long id = resultats.getLong("id");
				long client_id = resultats.getLong("client_id");
				long vehicle_id = resultats.getLong("vehicle_id");
				LocalDate debut = resultats.getDate("debut").toLocalDate();
				LocalDate fin = resultats.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id,client_id,vehicle_id,debut,fin);
				reservations.add(reservation);
			}
			return reservations;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet results = preparedStatement.executeQuery();
			ArrayList<Reservation> reservations = new ArrayList<>();
			// Extraction des résultats
			while (results.next()) {
				long id = results.getLong("id");
				long client_id = results.getLong("client_id");
				long vehicle_id = results.getLong("vehicle_id");
				LocalDate debut = results.getDate("debut").toLocalDate();
				LocalDate fin = results.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id,client_id,vehicle_id,debut,fin);
				reservations.add(reservation);
			}
			return reservations;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public Reservation findById(long id) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_ID_QUERY);
			preparedStatement.setLong(1,id);
			ResultSet resultat = preparedStatement.executeQuery();
			Reservation reservation = new Reservation();
			reservation.setId(id);
			reservation.setClient_id(resultat.getLong("client_id"));
			reservation.setVehicle_id(resultat.getLong("vehicle_id"));
			reservation.setDebut(resultat.getDate("debut").toLocalDate());
			reservation.setFin(resultat.getDate("fin").toLocalDate());
			return reservation;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}

	}

	public int count() throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(COUNT_RESERVATIONS_QUERY);
		) {
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt("count");
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public void update(Reservation reservation) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_RESERVATION_QUERY);

			preparedStatement.setLong(1, reservation.getClient_id());
			preparedStatement.setLong(2, reservation.getVehicle_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
			preparedStatement.setLong(5, reservation.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
