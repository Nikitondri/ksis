package bsuir.zakharenko.lab01.service.finder.impl;

import bsuir.zakharenko.lab01.service.finder.NetworkMaskFinder;
import bsuir.zakharenko.lab01.service.exception.ServiceException;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

//isUp запущен ли сетевой интерфейс
//isLoopback Возвращает, является ли сетевой интерфейс петлевым интерфейсом.

public class NetworkMaskFinderImpl implements NetworkMaskFinder {
    @Override
    public int find() throws ServiceException {
        int mask = -1;
        try {
            Enumeration<NetworkInterface> interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while(interfaceEnumeration.hasMoreElements()){
                mask = takeMaskValue(interfaceEnumeration.nextElement(), mask);
            }
        } catch (SocketException e) {
            throw new ServiceException(e);
        }
        return createBinaryMask(mask);
    }

    //isUp Возвращает, запущен ли сетевой интерфейс.
    //is LoopBack Возвращает, является ли сетевой интерфейс петлевым интерфейсом.
    //isVirtual Возвращает, является ли этот интерфейс виртуальным интерфейсом (также называемым субинтерфейсом).
    private int takeMaskValue(NetworkInterface current, int oldValue) throws SocketException {
        if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
            return oldValue;
        }
        InterfaceAddress host = current.getInterfaceAddresses().get(0);
        return host.getNetworkPrefixLength();
    }

    private int createBinaryMask(int mask) {
        int binaryMask = 0;
        for (int i = 0; i < 31; i++) {
            if (i < mask) {
                binaryMask++;
            }
            binaryMask <<= 1;
        }
        return binaryMask;
    }
}
