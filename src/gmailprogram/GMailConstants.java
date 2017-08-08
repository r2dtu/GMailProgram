/**
 * This file contains the necessary constants to run the e-mail program.
 *
 * Please only change the top 4 constants & your contact info. Do not touch
 * anything below unless you understand how the program is using each constant.
 */
package gmailprogram;

/**
 * @author David
 */
public class GMailConstants {

    // You only need to change these 4 constants!
    public static final boolean TEST_PREVIEW = false;
    public static final boolean PROGRESS_REPORT = true;
    public static final boolean ONLINE_PROGRAM_CREDENTIALS = false;
    public static final int NUM_OF_HW_ASSIGNMENTS = 17;

    // Make sure you're the right person! Fill out the correct contact info.
    public static final String ADMIN_EMAIL = GMailPrivateConstants.ADMIN_EMAIL;
    public static final String ADMIN_NAME = GMailPrivateConstants.ADMIN_NAME;
    public static final String ADMIN_PHONE = GMailPrivateConstants.ADMIN_PHONE;

    // ONLY CHANGE BELOW IF YOU KNOW WHAT YOU'RE DOING
    public static final boolean OUTPUT_TO_WORD_DOC = false;
    public static final String SOURCES_PATH = "src/";
    public static final String USERNAME = GMailPrivateConstants.USERNAME;
    public static final String PASSWORD = GMailPrivateConstants.PASSWORD;

    // Mail port
    public static final String AUTH = "true";
    public static final String ENABLE_START_TLS = "true";
    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final String SMTP_PORT = "587";

//    public static final String CSV_FILEPATH = SOURCES_PATH + "csvfiles\\"
//            + (TEST_PREVIEW ? "test\\" : "");
    public static final String CSV_FILEPATH = SOURCES_PATH + "csvfiles/"
            + (TEST_PREVIEW ? "test/" : "");
    public static final String CSV_SPLITBY = ",";

    // File specific constants
    public static final String ROSTER_FILE = "Class Roster - Roster.csv";
    public static final int FIRST_NAME_INDEX = 0;
    public static final int LAST_NAME_INDEX = 1;
    public static final int STUDENTID_INDEX = 2;
    public static final int EMAIL_INDEX = 3;
    public static final int USERNAME_INDEX = 4;
    public static final int TA_INDEX = 5;
    public static final int NUM_OF_STUDENTS_INDEX = 7;

    public static final String TEST_SCORES_FILE = "Class Roster - Tests.csv";
    public static final int DIAGNOSTIC_TEST_INDEX = 2;

    public static final String QUIZ_SCORES_FILE = "Class Roster - In-Class Quizzes.csv";
    public static final int QUIZ_1_INDEX = 2;
    
    public static final String QUIZ_SCORES_ONLINE_FILE = "Class Roster - Online Quizzes.csv";

    public static final String HW_FILE = "Class Roster - Homework.csv";
    public static final int HW1_INDEX = 2;
    public static final String HW_KEY_STRING
            = "<i>A ✓ indicates completion, and a blank indicates we do not have "
            + "it (you can still turn it in). An X indicates a missing "
            + "assignment. A section name means you "
            + "did not turn in that section of homework. (excused) indicates "
            + "you do not need to turn in that assignment. By the end "
            + "of the class, you must have no more than 2 missing assignments "
            + "and 0 incomplete/redo assignments. THE COUNTS ARE OFF, so "
            + "please see the actual data for your records.</i><br>";
    public static final String HW_RESULTS_STRING
            = "<b>Completed Assignments:</b> %d<br>"
            + "<b>Missing / Incomplete Assignments:</b> %d<br>"
            + "<b>Redo Assignments:</b> %d<br>"
            + "<br>";

    public static final String GRADES_FILE = "Class Roster - Grades.csv";
    public static final String GRADES_STRING = "Your grades for this class: <br>";
    public static final int HW_SUBSCORE_INDEX = 2;

    public static final String STUDENT_INFO_EMAIL_SUBJECT
            = "[SummerSAT] Student Progress Report [ID: %d]";
    public static final String STUDENT_INFO_EMAIL_TEXT_INTRO
            = "Hi %s,<br><br>"
            + "Here is your progress report. "
            + "If you have a blank, that means we did not record you having "
            + "done it, and you need to turn it in. There may be some notices "
            + "saying you were missing an exercise – if you believe this is an "
            + "incorrect record, please let me know ASAP. You will have to pay "
            + "penalty $25 per missing assignment over 2. Test scores will be "
            + "finalized soon. Practice for the last one!"
            + "<br><br>";
    public static final String STUDENT_INFO_EMAIL_TEXT_CONCLUSION
            = "If you believe anything is incorrect or have any questions "
            + "regarding this report, please email " + ADMIN_NAME
            + " immediately at " + ADMIN_EMAIL + " or text him at " + ADMIN_PHONE
            + ". Do NOT respond to this email.<br><br>"
            + "Cheers,<br>"
            + "Your SummerSAT teachers and TAs from Class 101";

    public static final String ONLINE_PROGRAM_EMAIL_SUBJECT
            = "[SummerSAT] Online Program Login Information [ID: %d]";
    public static final String ONLINE_PROGRAM_EMAIL_TEXT = "Hi %s,<br><br>"
            + "Here is your username and password for the SummerSAT Online "
            + "Program. You can access the website at "
            + "http://www.your_website.com/.<br>"
            + "<br>"
            + "Username: %s<br>"
            + "Password: %s<br>"
            + "<br>"
            + "Be sure to change your password once you've logged in. "
            + "If you have already been able to log in, you may ignore this "
            + "email. If you have any trouble logging in, please email "
            + ADMIN_NAME + "immediately at " + ADMIN_EMAIL + " or text him at "
            + "(714) 227-5635. Do not respond to this email.<br>"
            + "<br>"
            + "Cheers,<br>"
            + "Your SummerSAT teachers and TAs from Class 101";
}
