package com.epf.rentmanager.ui;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.ui.cli.Presentation;

public class Main {
  public static void main(String[] args) throws ServiceException, DaoException {
      Presentation userCmdInterface = new Presentation();
      userCmdInterface.Application();
  }
}
