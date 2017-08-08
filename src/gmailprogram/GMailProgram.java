/**
 * Instructions: Please read the following before using the program.
 * 
 * This program was designed to be used with SummerSAT 2016 Class 101, and is
 * used for sending out individual e-mails to students with their online
 * SAT test submission program credentials and/or their weekly progress reports
 * which included information on their test, quiz, and homework scores.
 * 
 * The Excel sheet records must be downloaded in a .csv (comma-splitted values)
 * format. Only data for students may be in these .csv files (extraneous rows
 * of data will inhibit functionality for the program).
 * 
 * Before running the program, go to GMailConstants and ensure the top 5
 * constants are what you want the program to have. It's always good to 
 * preview the output first with a few rows of data (and email them to yourself)
 * before you run the program in its entirety.
 */
package gmailprogram;

/**
 *
 * @author David
 */
public class GMailProgram {

    /**
     * We're running the program on 1 thread; multi-threading kills the beast.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        // Check what kind of email we want to send
        boolean emailSent = false;
        if (GMailConstants.ONLINE_PROGRAM_CREDENTIALS) {
            new Thread(new GMailOnlineProgramCredentials()).start();
            emailSent = true;
        }
        if (GMailConstants.PROGRESS_REPORT) {
            new Thread(new GMailProgressReport()).start();
            emailSent = true;
        }

        // If we sent emails, we're done.
        if (emailSent) {
            return;
        }
        
        // All email types were false ... pointless
        System.out.println("No email type specified. Please check the "
                + "GMailConstants file and make sure at least one of the "
                + "email types is set to true.");
    }
}
