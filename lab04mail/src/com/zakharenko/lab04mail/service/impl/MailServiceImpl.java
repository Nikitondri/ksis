package com.zakharenko.lab04mail.service.impl;

import com.zakharenko.lab04mail.service.MailService;
import com.zakharenko.lab04mail.service.exception.ServiceException;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    @Override
    public Message[] loadMail() throws ServiceException {
        Session session = Session.getDefaultInstance(new Properties( ));
        try {
            Store store = session.getStore("imaps");
            store.connect("imap.mail.ru", 993, "nikitondri@mail.ru", "RMLAHnHwpGHsqwUTXmQE");
            Folder inbox = store.getFolder( "INBOX" );
            inbox.open( Folder.READ_ONLY );
            return inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
        } catch (MessagingException e) {
            throw new ServiceException(e);
        }
    }
}
