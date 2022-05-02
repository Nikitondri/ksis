package bsuir.zakharenko.lab01.service.finder;

import bsuir.zakharenko.lab01.service.exception.ServiceException;

import java.net.InetAddress;

public interface NetworkInfoFinder {
    String find() throws ServiceException;

    default String findMacAddress(byte[] mac){
        if(mac == null){
            return "Not found MAC address";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }

    default int findIpAddress(InetAddress localHost){
        byte[] arrIP = localHost.getAddress(); //Возвращает необработанный IP-адрес этого InetAddress объекта.
        int result = arrIP[0] & 255;
        for (int i = 1; i < arrIP.length; i++) {
            result <<= 8;
            result += arrIP[i] & 255;

        }
        return result;
    }
}
