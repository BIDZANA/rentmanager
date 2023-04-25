package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.exception.ServiceException;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;
	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}

	public long create(Client client) throws ServiceException, DaoException {
		try{
			if (client.getNom() != null && client.getPrenom() != null){
				client.setNom(client.getNom().toUpperCase());
				return this.clientDao.create(client);
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
		try {
			return this.clientDao.findAll();
		}catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public long delete(Long clientId) throws ServiceException, DaoException {
		try {
			return this.clientDao.delete(clientId);
		}catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long update(Client client) throws ServiceException, DaoException{
		try{
			client.setNom(client.getNom().toUpperCase());
			return this.clientDao.update(client);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Client> findClientByVehicleId(long vehicle_id) throws ServiceException {
		try {
			return this.clientDao.findByVehicleId(vehicle_id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public int count() throws ServiceException {
		try {
			return this.clientDao.count();
		}catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
