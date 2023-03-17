package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.exception.ServiceException;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	public ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException, DaoException {
		try{
			if (client.getNom() != null && client.getPrenom() != null){
				client.setNom(client.getNom().toUpperCase());
				return clientDao.create(client);
			}
		}catch (Exception e){
			throw new ServiceException("erreur!");
		}
		System.out.println("Entrées invalides!");
		return 0;
	}

	public Client findById(long id) throws ServiceException, DaoException {
		try {
			return clientDao.findById(id).get();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Client> findAll() throws ServiceException, DaoException {
		return clientDao.findAll();
	}

	public long delete(Long clientId) throws ServiceException, DaoException {
		return clientDao.delete(clientId);
	}

	public long update(Client client) throws ServiceException, DaoException{
		try{
			client.setNom(client.getNom().toUpperCase());
			return clientDao.update(client);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Client> findClientByVehicleId(long vehicle_id) throws ServiceException {
		try {
			return clientDao.findByVehicleId(vehicle_id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
