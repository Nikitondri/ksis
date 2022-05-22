package com.zakharenko.lab04mail.runner;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Arrays;
import java.util.Properties;

public class Runner {
    public static void main(String[] args) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties( ));
        Store store = session.getStore("imaps");
//        store.connect("imap.mail.ru", 993, "nikitondri@mail.ru", "RMLAHnHwpGHsqwUTXmQE");
        store.connect("imap.gmail.com", 993, "nikitazacharenko25071995@gmail.com", "lqlyyefaipbyjkya");
        Folder inbox = store.getFolder( "INBOX" );
        inbox.open( Folder.READ_ONLY );
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
//        Arrays.sort( messages, (m1, m2 ) -> {
//            try {
//                return m2.getSentDate().compareTo( m1.getSentDate() );
//            } catch ( MessagingException e ) {
//                throw new RuntimeException( e );
//            }
//        } );
        for ( Message message : messages ) {
            System.out.println(
                    "sendDate: " + message.getSentDate()
                            + " subject:" + message.getSubject()  );
        }
    }
}
