package com.zakharenko.lab04mail.service;

import com.zakharenko.lab04mail.entity.MailMessage;
import com.zakharenko.lab04mail.service.exception.ServiceException;

import javax.mail.Message;
import java.util.List;

public interface MailService {
    List<MailMessage> loadMail() throws ServiceException;
}
