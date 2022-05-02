package bsuir.zakharenko.lab01.service.finder.impl;

import bsuir.zakharenko.lab01.service.exception.ServiceException;
import bsuir.zakharenko.lab01.service.finder.NetworkInfoFinder;

import java.net.*;
import java.util.Enumeration;

public class LocalNetworkInfoFinderImpl implements NetworkInfoFinder {
    /**
     * @see InetAddress Этот класс представляет адрес интернет-протокола (IP).
     * @see Inet4Address Этот класс представляет адрес Интернет-протокола версии 4 (IPv4).
     * @return Информацию об MAC-адресе и имени собственного компьютера
     */
    @Override
    public String find() throws ServiceException {
        StringBuilder resultString = new StringBuilder("LocalHost Info: ").append("\n");
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            resultString.append("Name ").append(inetAddress.getHostName()).append("\n");
            resultString.append("Mac: ").append(findMac());
            resultString.append("IP: ").append(inetAddress.getHostAddress()).append("\n\n");
        } catch (UnknownHostException e) {
            throw new ServiceException("LocalNetworkInfoFinderImpl error!");
        }
        return resultString.toString();
    }

    private String findMac() throws ServiceException {
        StringBuilder result = new StringBuilder();
//        try {
//            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//            while (interfaces.hasMoreElements()) {
//                byte[] hardwareAddress = interfaces.nextElement().getHardwareAddress();
//                if (hardwareAddress != null) {
//                    result.append("\t").append(findMacAddress(hardwareAddress)).append("\n");
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
            result.append(findMacAddress(network.getHardwareAddress())).append("\n");
        } catch (UnknownHostException | SocketException e) {
            throw new ServiceException(e);
        }
        return result.toString();
    }
}
