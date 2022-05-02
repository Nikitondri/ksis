package bsuir.zakharenko.lab01.service.finder;

import bsuir.zakharenko.lab01.service.exception.ServiceException;

import java.net.NetworkInterface;
import java.util.List;

public interface NetworkMaskFinder {
    int find() throws ServiceException;
}
