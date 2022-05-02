package bsuir.zakharenko.lab01.service.finder.impl;

import bsuir.zakharenko.lab01.service.exception.ServiceException;
import bsuir.zakharenko.lab01.service.finder.NetworkInfoFinder;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NodesNetworkInfoFinderImpl implements NetworkInfoFinder {
    private static final String PATTERN_MAC = "\\s*([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})";

    private final int mask;

    public NodesNetworkInfoFinderImpl(int mask) {
        this.mask = mask;
    }

    @Override
    public String find() throws ServiceException {
        int ip = findIp();
        int address = ip & mask;
        int amount = ~mask - 1;
        return findInfo(address, amount);
    }

    private int findIp() throws ServiceException {
        try {
            InetAddress localHost = InetAddress.getLocalHost(); //Адрес локального хоста
            //Удобный метод поиска сетевого интерфейса, к которому привязан указанный адрес интернет-протокола (IP).
            return findIpAddress(localHost);
        } catch (UnknownHostException e) {
            throw new ServiceException(e);
        }
    }

    private String findInfo(int address, int amount) throws ServiceException {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 1; i <= amount; i++) {
            long outerAddress = address + i;
            String anotherApi = createIP(outerAddress);
            try {
                //TODO: jar file from bat script
                InetAddress inetAddress = InetAddress.getByName(anotherApi);
                stringBuilder.append("IP: ").append(inetAddress.getHostAddress()).append("\n");
                //Address resolution protocol
                stringBuilder.append("MAC: ").append(checkARPTable(inetAddress.getHostAddress())).append("\n\n");
            } catch (IOException e) {
                throw new ServiceException(e);
            }
        }
        return stringBuilder.toString();
    }

    private String createIP(long outerAddress) {
        short[] buf = new short[4];
        short check = 255;
        buf[3] = (short) (outerAddress & check);
        buf[2] = (short) ((outerAddress >> 8) & check);
        buf[1] = (short) ((outerAddress >> 16) & check);
        buf[0] = (short) ((outerAddress >> 24) & check);
        return buf[0] + "." + buf[1] + "." + buf[2] + "." + buf[3];
    }

    private String checkARPTable(String ip) throws IOException {
        String systemInput = takeARP(ip);
        Pattern pattern = Pattern.compile(PATTERN_MAC);
        Matcher matcher = pattern.matcher(systemInput);
        if (matcher.find()) {
            return matcher.group().trim();
        } else {
            return "not found";
        }
    }

    private String takeARP(String ip) throws IOException {
        Scanner scanner = new Scanner(Runtime.getRuntime().exec("arp -a " + ip).getInputStream()).useDelimiter("\\A");
        return scanner.next();
    }
}

