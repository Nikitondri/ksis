package com.zakharenko.lab04.service.impl;

import com.zakharenko.lab04.service.MailService;
import com.zakharenko.lab04.service.exception.ServiceException;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    @Override
    public Message[] loadMail() throws ServiceException {
        try {
            Session session = Session.getDefaultInstance(new Properties( ));
            Store store = session.getStore("imaps");
            store.connect("imap.mail.ru", 993, "nikitondri@mail.ru", "RMLAHnHwpGHsqwUTXmQE");
            Folder inbox = store.getFolder( "INBOX" );
            inbox.open( Folder.READ_ONLY );
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.ANSWERED), false));
            return messages;
        } catch (MessagingException e) {
            throw new ServiceException(e);
        }
    }
}
