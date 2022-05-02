package bsuir.zakharenko.lab01.service;

import bsuir.zakharenko.lab01.service.exception.ServiceException;

public interface ScanLocalNetworkService {
    String scan() throws ServiceException;
}
