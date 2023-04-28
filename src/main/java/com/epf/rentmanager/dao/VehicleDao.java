package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private VehicleDao() {}

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, modele, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT * FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle";
	private static final String EDIT_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";
	private static final String FIND_VEHICLES_BY_CLIENT = "SELECT * FROM Vehicle INNER JOIN Reservation ON Reservation.vehicle_id=Vehicle.id WHERE Reservation.client_id=?;";


	public long create(Vehicle vehicle) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY);
			preparedStatement.setString(1,vehicle.getConstructeur());
			preparedStatement.setString(2,vehicle.getModele());
			preparedStatement.setInt(3,vehicle.getNb_places());
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
			return vehicle.getId();
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public long delete(Long id) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			preparedStatement.setLong(1,id);
			long result = preparedStatement.executeUpdate(); //afin de vérifier la suppression
			preparedStatement.close();
			connection.close();
			return result;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public Optional<Vehicle> findById(long id) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY);
		) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return Optional.ofNullable(instanceFromResult(resultSet));
			return Optional.empty();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_QUERY);
			ResultSet results = preparedStatement.executeQuery();
			ArrayList<Vehicle> vehicles = new ArrayList<>();
			// Extraction des résultats
			while (results.next()) {
				Long id = results.getLong("id");
				String constructeur = results.getString("constructeur");
				String modele = results.getString("modele");
				int nb_places = results.getInt("nb_places");
				Vehicle vehicle = new Vehicle(id,constructeur,modele,nb_places);
				vehicles.add(vehicle);
			}
			return vehicles;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public List<Vehicle> findByClientId(long clientId) throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_BY_CLIENT);
		) {
			preparedStatement.setLong(1, clientId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
				vehicles.add(instanceFromResult(resultSet));
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return vehicles;
	}

	public int count() throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(COUNT_VEHICLES_QUERY);
		) {
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt("count");
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public long update(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(EDIT_VEHICLE_QUERY);
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setLong(3, vehicle.getNb_places());
			preparedStatement.setLong(4, vehicle.getId());
			long id = preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			return id;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	private Vehicle instanceFromResult(ResultSet resultSet) throws SQLException {
		Vehicle vehicle = new Vehicle(
				resultSet.getLong("id"),
				resultSet.getString("constructeur"),
				resultSet.getString("modele"),
				resultSet.getInt("nb_places")
		);
		return vehicle;
	}
}
