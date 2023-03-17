package com.epf.rentmanager.exception;

import com.epf.rentmanager.utils.IOUtils;

public class DaoException extends Exception {
    public DaoException(String message) {
        IOUtils.print(message);
    }
}
