package com.zakharenko.lab04.runner;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import javax.mail.*;
//import org.slf4j.Logg
import javax.mail.Message;
import javax.mail.search.FlagTerm;

public class Runner {
    public static void main(String[] args) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties( ));
        Store store = session.getStore("imaps");
        store.connect("imap.mail.ru", 993, "nikitondri@mail.ru", "RMLAHnHwpGHsqwUTXmQE");
        Folder inbox = store.getFolder( "INBOX" );
        inbox.open( Folder.READ_ONLY );
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.ANSWERED), false));
        Arrays.sort( messages, (m1, m2 ) -> {
            try {
                return m2.getSentDate().compareTo( m1.getSentDate() );
            } catch ( MessagingException e ) {
                throw new RuntimeException( e );
            }
        } );
        for ( Message message : messages ) {
            System.out.println(
                    "sendDate: " + message.getSentDate()
                            + " subject:" + message.getSubject()  );
        }

    }
}
