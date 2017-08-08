/**
 * This file sends the online program credentials e-mail.
 * 
 * Please note the template for this program. An example roster file can be
 * found in src/csvfiles/test. Be sure that you are formatting the Excel sheet
 * correctly, or the indices of the arrays will be wrong.
 */
package gmailprogram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author David
 */
public class GMailOnlineProgramCredentials implements Runnable {

    @Override
    public void run() {

        // Get the smtp authentication and port, then connect to it
        Properties props = new Properties();
        props.put("mail.smtp.auth", GMailConstants.AUTH);
        props.put("mail.smtp.starttls.enable", GMailConstants.ENABLE_START_TLS);
        props.put("mail.smtp.host", GMailConstants.SMTP_HOST);
        props.put("mail.smtp.port", GMailConstants.SMTP_PORT);

        // Login to the gmail account
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected
                    PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                GMailConstants.USERNAME,
                                GMailConstants.PASSWORD);
                    }
                });

        // Begin preparing to send the message (e-mail)
        try {

            // We need to open the files
            try {

                // We need to load all the information!
                List<String> names = new ArrayList<>();
                List<Integer> ids = new ArrayList<>();
                List<String> emails = new ArrayList<>();
                List<String> usernames = new ArrayList<>();

                // Read from file
                BufferedReader roster = new BufferedReader(
                        new FileReader(GMailConstants.CSV_FILEPATH
                                + GMailConstants.ROSTER_FILE));

                // Current line we're parsing
                String line;

                // Catch an IOException (reading lines)
                try {

                    // Read the first line of all files
                    roster.readLine();

                    // Read all lines of the roster
                    while ((line = roster.readLine()) != null) {

                        // Read student information from file
                        String[] information = line.split(
                                GMailConstants.CSV_SPLITBY);

                        // Add names to array
                        names.add(information[GMailConstants.FIRST_NAME_INDEX]);

                        // Add student IDs to array
                        ids.add(Integer.parseInt(
                                information[GMailConstants.STUDENTID_INDEX]));

                        // Add emails to array
                        emails.add(information[GMailConstants.EMAIL_INDEX]);

                        // Add usernames to array
                        usernames.add(
                                information[GMailConstants.USERNAME_INDEX]);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GMailProgressReport.class.getName()).log(
                            Level.SEVERE, null, ex);
                }

                // Send out invdividual emails
                for (int i = 0; i < names.size(); i++) {
                    Message message = new MimeMessage(session);
                    try {
                        message.setFrom(new InternetAddress(
                            GMailConstants.USERNAME, "SummerSAT Class 101"));
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(
                                GMailProgressReport.class.getName()).log(
                                        Level.SEVERE, null, ex);
                    }
                    
                    // Recipient: Student
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(emails.get(i)));
                    
                    // Message subject
                    message.setSubject(String.format(
                            GMailConstants.ONLINE_PROGRAM_EMAIL_SUBJECT,
                            ids.get(i)));
                    
                    // Body of the e-mail, HTML format
                    message.setContent(String.format(
                            GMailConstants.ONLINE_PROGRAM_EMAIL_TEXT,
                            names.get(i), usernames.get(i), usernames.get(i)),
                            "text/html; charset=utf-8");

                    // Send the e-mail
                    Transport.send(message);

                    // Debug
                    System.out.println("Email sent to " + names.get(i));
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(
                        GMailProgressReport.class.getName()).log(
                                Level.SEVERE, null, ex);
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finished.");
    }
}
