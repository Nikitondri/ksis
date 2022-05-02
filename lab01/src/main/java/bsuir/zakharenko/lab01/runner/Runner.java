package bsuir.zakharenko.lab01.runner;


import bsuir.zakharenko.lab01.service.ScanLocalNetworkService;
import bsuir.zakharenko.lab01.service.exception.ServiceException;
import bsuir.zakharenko.lab01.service.impl.ScanLocalNetworkServiceImpl;


public class Runner  {
    public static void main(String[] args) {
        ScanLocalNetworkService scanInfo = new ScanLocalNetworkServiceImpl();
        try {
            System.out.println(scanInfo.scan());
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }
}
