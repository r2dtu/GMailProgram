/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailprogram;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author David
 */
public class SendGMailThread implements Runnable {

    private final Student student;
    private final Session session;
    
    private final List<String> testNames;
    private final List<String> quizNames;
    private final List<String> hwNames;
    private final List<String> gradebook;

    public SendGMailThread(Student student, Session session,
            List<String> testNames, List<String> quizNames,
            List<String> hwNames, List<String> gradebook) {
        this.student = student;
        this.session = session;
        this.testNames = testNames;
        this.quizNames = quizNames;
        this.hwNames = hwNames;
        this.gradebook = gradebook;
    }

    @Override
    public void run() {
        // Compose a new message
        Message message = new MimeMessage(session);

        // Set the from address name
        try {
            message.setFrom(new InternetAddress(
                    GMailConstants.USERNAME, "SummerSAT Class 101"));

            // Set recipients of the email
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(student.getEmail()));

            // Set the subject of the email
            message.setSubject(String.format(
                    GMailConstants.STUDENT_INFO_EMAIL_SUBJECT,
                    student.getId()));

            // Build the email text
            String text = String.format(GMailConstants.STUDENT_INFO_EMAIL_TEXT_INTRO, student.getName());

            // Build the test scores string
            for (int index = 0; index < testNames.size(); index++) {
                text += "<b>" + testNames.get(index) + "</b>: " + student.getTestScores()[index] + "<br>";
            }

            // Space out each section
            text += "<br>-----<br>";

            // Build the quiz scores string
            for (int index = 0; index < quizNames.size(); index++) {
                text += "<b>" + quizNames.get(index) + "</b>: " + student.getQuizScores()[index] + "<br>";
            }

            // Space out each section
            text += "<br>-----<br>";

            // Build the homework results string
            text += GMailConstants.HW_KEY_STRING;
            text += String.format(GMailConstants.HW_RESULTS_STRING,
                    student.getNumOfCompletedAssignments(),
                    student.getNumOfMissingAssignments(),
                    student.getNumOfRedoAssignments(),
                    student.getNumOfExcusedAssignments());
            for (int index = 0; index < hwNames.size(); index++) {
                text += "<b>" + hwNames.get(index) + "</b>: " + student.getHWResults()[index] + "<br>";
            }

            // Space out each section
            text += "<br>-----<br>";

            // Build the grades string
            text += GMailConstants.GRADES_STRING;
            for (int index = 0; index < gradebook.size(); index++) {
                text += "<b>" + gradebook.get(index) + "</b>: " + student.getGradeResults()[index] + "<br>";
            }

            // Space out each section
            text += "<br>-----<br>";

            // Add conclusion
            text += GMailConstants.STUDENT_INFO_EMAIL_TEXT_CONCLUSION;

            // Set the content of the email
            message.setContent(text, "text/html; charset=utf-8");

            // Send out the email message
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(SendGMailThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GMailProgressReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Debug
        System.out.println("Email sent to " + student.getName() + ".");
    }

}
