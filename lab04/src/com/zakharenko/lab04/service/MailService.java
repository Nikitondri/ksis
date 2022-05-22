package com.zakharenko.lab04.service;

import com.zakharenko.lab04.service.exception.ServiceException;

import javax.mail.Message;

public interface MailService {
    Message[] loadMail() throws ServiceException;
}
