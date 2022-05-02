package bsuir.zakharenko.lab01.service.impl;

import bsuir.zakharenko.lab01.service.ScanLocalNetworkService;
import bsuir.zakharenko.lab01.service.exception.ServiceException;
import bsuir.zakharenko.lab01.service.finder.NetworkInfoFinder;
import bsuir.zakharenko.lab01.service.finder.NetworkMaskFinder;
import bsuir.zakharenko.lab01.service.finder.impl.LocalNetworkInfoFinderImpl;
import bsuir.zakharenko.lab01.service.finder.impl.NetworkMaskFinderImpl;
import bsuir.zakharenko.lab01.service.finder.impl.NodesNetworkInfoFinderImpl;


public class ScanLocalNetworkServiceImpl implements ScanLocalNetworkService {

    @Override
    public String scan() throws ServiceException {
        StringBuilder stringBuilder = new StringBuilder("Info:").append("\n");
        NetworkMaskFinder interfacesFinder = new NetworkMaskFinderImpl();
        NetworkInfoFinder localInfoFinder = new LocalNetworkInfoFinderImpl();
        int mask = interfacesFinder.find();
        NetworkInfoFinder nodesInfoFinder = new NodesNetworkInfoFinderImpl(mask);
        return stringBuilder.append(localInfoFinder.find()).append(nodesInfoFinder.find()).toString();
    }
}
