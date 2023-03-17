package com.epf.rentmanager.service;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	public VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException, DaoException {
		try{
			if (vehicle.getConstructeur() != null && vehicle.getNb_places()>1){
				return vehicleDao.create(vehicle);
			}
		}catch (Exception e){
			throw new ServiceException("erreur!");
		}
		System.out.println("Entrées invalides!");
		return 0;
	}

	public Vehicle findById(long id) throws ServiceException, DaoException {
		try {
			return vehicleDao.findById(id).get();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws ServiceException, DaoException {
		return vehicleDao.findAll();
	}

	public long delete(Vehicle vehicle) throws ServiceException, DaoException {
		return vehicleDao.delete(vehicle);
	}

	public List<Vehicle> findByClient(long clientId) throws ServiceException {
		try {
			return vehicleDao.findByClientId(clientId);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
}
