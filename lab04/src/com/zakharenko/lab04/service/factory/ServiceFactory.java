package com.zakharenko.lab04.service.factory;

import com.zakharenko.lab04.service.MailService;
import com.zakharenko.lab04.service.impl.MailServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private MailService mailService = new MailServiceImpl();


    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public MailService getMailService() {
        return mailService;
    }
}
