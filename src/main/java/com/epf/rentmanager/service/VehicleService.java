package com.epf.rentmanager.service;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	public VehicleService(VehicleDao vehicleDao){
		this.vehicleDao = vehicleDao;
	}

	public long create(Vehicle vehicle) throws ServiceException, DaoException {
		try{
			if (vehicle.getConstructeur() != null && vehicle.getNb_places()>1){
				return this.vehicleDao.create(vehicle);
			}
		}catch (Exception e){
			throw new ServiceException("erreur!");
		}
		System.out.println("Entrées invalides!");
		return 0;
	}

	public Vehicle findById(long id) throws ServiceException, DaoException {
		try {
			return this.vehicleDao.findById(id).get();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws ServiceException, DaoException {
		try {
			return this.vehicleDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public long delete(Long vehicle_id) throws ServiceException, DaoException {
		try {
			return this.vehicleDao.delete(vehicle_id);
		}catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Vehicle> findByClient(long clientId) throws ServiceException {
		try {
			return this.vehicleDao.findByClientId(clientId);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public int count() throws ServiceException {
		try {
			return this.vehicleDao.count();
		}catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long update(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.update(vehicle);
		}catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;

	}
}
