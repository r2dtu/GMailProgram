/**
 * This file sends the weekly progress report e-mail.
 */
package gmailprogram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.mail.*;

/**
 *
 * @author David
 */
public class GMailProgressReport implements Runnable {

    // Array of students in the class
    private final List<Student> students = new ArrayList<>();

    // Titles of quizzes, tests, hw assignments, and gradebook columns
    private List<String> quizNames, onlineQuizNames, testNames, hwNames, gradebook;

    /**
     * Runs the full program to send progress reports to individual students.
     */
    @Override
    public void run() {

        // Read from files (catch FileNotFoundException)
        try {

            // Read each file into memory
            BufferedReader roster = new BufferedReader(new FileReader(
                    GMailConstants.CSV_FILEPATH
                    + GMailConstants.ROSTER_FILE));
            BufferedReader tests = new BufferedReader(new FileReader(
                    GMailConstants.CSV_FILEPATH
                    + GMailConstants.TEST_SCORES_FILE));
            BufferedReader quizzes = new BufferedReader(new FileReader(
                    GMailConstants.CSV_FILEPATH
                    + GMailConstants.QUIZ_SCORES_FILE));
            BufferedReader onlineQuizzes = new BufferedReader(new FileReader(
                    GMailConstants.CSV_FILEPATH
                    + GMailConstants.QUIZ_SCORES_ONLINE_FILE));
            BufferedReader homework = new BufferedReader(new FileReader(
                    GMailConstants.CSV_FILEPATH
                    + GMailConstants.HW_FILE));
            BufferedReader grades = new BufferedReader(new FileReader(
                    GMailConstants.CSV_FILEPATH
                    + GMailConstants.GRADES_FILE));

            // Current line we're parsing
            String line;

            // Catch an IOException (reading lines)
            try {

                // Read the header rows of each file
                // Instead of just reading the first line, we could
                // figure out the column indices of the different
                // information about each student. But let's just say
                // we're following a template, and the format is always
                // the same. =)
                /* ROSTER:
                 *
                 * | First | Last | Student ID | Email | Username | TA |
                 */
                roster.readLine();

                // The first line is all headers - but we need to make
                // sure we ignore the blank columns (dividers),
                // which are separations of sections for user-friendly
                // reading on the excel workbook: just for
                // quizzes and tests
                List<Pair<String, Integer>> blankColumns = new ArrayList<>();

                // Get test names
                testNames = getAssignmentNames("test", tests, blankColumns, GMailConstants.DIAGNOSTIC_TEST_INDEX);

                // Get quiz names
                quizNames = getAssignmentNames("quiz", quizzes, blankColumns, GMailConstants.QUIZ_1_INDEX);

                // Get online quizzes
                onlineQuizNames = getAssignmentNames("online", onlineQuizzes, blankColumns, GMailConstants.QUIZ_1_INDEX);

                // Get homework assignments
                hwNames = getAssignmentNames("hw", homework, blankColumns, GMailConstants.HW1_INDEX);

                // Get gradebook titles (first row)
                gradebook = getAssignmentNames("grades", grades, blankColumns, GMailConstants.HW_SUBSCORE_INDEX);

                // Do this for every student
                while ((line = roster.readLine()) != null) {

                    // Read student information from file
                    String[] information = line.
                            split(GMailConstants.CSV_SPLITBY);

                    // Create a student to email
                    Student student = new Student(
                            information[GMailConstants.FIRST_NAME_INDEX],
                            Integer.parseInt(
                                    information[GMailConstants.STUDENTID_INDEX]),
                            information[GMailConstants.EMAIL_INDEX],
                            quizNames.size(), testNames.size(),
                            hwNames.size(), gradebook.size());

                    // Read test scores
                    line = tests.readLine();
                    addScoresToStudent("test", line, student, GMailConstants.DIAGNOSTIC_TEST_INDEX, blankColumns);

                    // Read quiz scores
                    line = quizzes.readLine();
                    addScoresToStudent("quiz", line, student, GMailConstants.QUIZ_1_INDEX, blankColumns);

                    // Read online quiz scores
                    line = onlineQuizzes.readLine();
                    addScoresToStudent("online", line, student, GMailConstants.QUIZ_1_INDEX, blankColumns);

                    // Read in homework information
                    line = homework.readLine();
                    addScoresToStudent("hw", line, student, GMailConstants.HW1_INDEX, blankColumns);

                    // Read grades
                    line = grades.readLine();
                    addScoresToStudent("grades", line, student, GMailConstants.HW_SUBSCORE_INDEX, blankColumns);

//                    System.out.println(student.getName() + ": ");
//                    for (String s : information) {
//                        System.out.print(s + ", ");
//                    }
//                    System.out.println();

                    // Add the student to the list of students
                    students.add(student);
                }
            } catch (IOException ex) {
                Logger.getLogger(GMailProgressReport.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GMailProgressReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Begin preparing to send the message (e-mail)
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
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                GMailConstants.USERNAME,
                                GMailConstants.PASSWORD);
                    }
                });
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Do this for each individual student
        for (Student student : students) {
            executor.execute(new SendGMailThread(student, session,
                    testNames, quizNames, hwNames, gradebook));

        }

        executor.shutdown();
        while (!executor.isTerminated()) {

        }
        System.out.println("Done.");
    }

    /**
     * Parses a given string with a student's score, and adds it to their
     * records in memory.
     * 
     * @param type - quiz, hw, test, etc.
     * @param line - string to parse
     * @param student - student data
     * @param startPos - column with first score
     * @param blankColumns - list of blank columns in CSV file
     */
    private void addScoresToStudent(String type, String line, Student student, int startPos, List<Pair<String, Integer>> blankColumns) {

        String[] information = line.split(GMailConstants.CSV_SPLITBY);

        // Find any missing/redo assignments
        int missingCount = 0;
        int redoCount = 0;
        int excusedCount = 0;
        int blanks = 0;

        // Add scores to student
        int nextCol = 0;
        for (int column = startPos; column < information.length; column++) {
            // If the column is a blank column, skip it
            if (blankColumns.contains(new Pair(type, column))) {
                continue;
            }

            switch (type) {
                case "test":
                    student.getTestScores()[nextCol] = information[column];
                    break;
                case "quiz":
                    student.getQuizScores()[nextCol] = information[column];
                    break;
                case "online":
                    student.getQuizScores()[nextCol] = information[column];
                    break;
                case "hw":

                    if (column == information.length - 1) {
                        break;
                    }
                    // Get the HW result
                    String result = information[column];
                    if (isMissing(result) || isIncomplete(result)) {
                        missingCount++;
                    } else if (isRedo(result)) {
                        redoCount++;
                    } else if (isExcused(result)) {
                        excusedCount++;
                    } else if (result.isEmpty() || result.equals("") || result.equals("\n")) {
                        blanks++;
                        break;
                    }
                    // check mark is complete, but just subtract from total
                    student.getHWResults()[nextCol] = information[column];
                    break;
                case "grades":
                    student.getGradeResults()[nextCol] = information[column];
                    break;
            }
            nextCol++;
        }

        // Set number of missing / redo / excused assignments
        if (type.equals("hw")) {
            student.setNumOfMissingAssignments(missingCount);
            student.setNumOfRedoAssignments(redoCount);
            student.setNumOfExcusedAssignments(excusedCount);
            student.setBlanks(blanks);
        }
    }

    /**
     * Returns a list of the assignment names recorded in the file.
     *
     * @param type
     * @param file
     * @param blankColumns
     * @param startPos
     * @throws IOException
     * @return
     */
    private ArrayList<String> getAssignmentNames(String type, BufferedReader file, List<Pair<String, Integer>> blankColumns, int startPos) throws IOException {
        ArrayList<String> names = new ArrayList<>();
        String[] broken = file.readLine().
                split(GMailConstants.CSV_SPLITBY);

        // Store information from the header row
        for (int i = startPos;
                i < broken.length; i++) {

            // If it's a blank column, record it and move on
            if (broken[i].isEmpty()) {
                blankColumns.add(new Pair(type, i));
                continue;
            }
            names.add(broken[i]);
        }
        return names;
    }

    /**
     * Checks if a homework assignment is a redo assignment.
     *
     * @param result data
     * @return true if redo, false if not
     */
    private boolean isRedo(String result) {
        return result.contains("REDO") || result.contains("redo")
                || result.contains("Redo");
    }

    /**
     * Checks if a homework assignment is an incomplete assignment.
     *
     * @param result data
     * @return true if incomplete, false if not
     */
    private boolean isIncomplete(String result) {
        return result.contains("Writing") || result.contains("Reading")
                || result.contains("Math");
    }

    /**
     * Checks if a homework assignment is a missing assignment.
     *
     * @param result data
     * @return true if redo, false if not
     */
    private boolean isMissing(String result) {
        return result.equalsIgnoreCase("x") || result.equalsIgnoreCase("inc") || result.isEmpty();
    }

    /**
     * Checks if a homework assignment is an excused assignment.
     *
     * @param result data
     * @return true if redo, false if not
     */
    private boolean isExcused(String result) {
        return result.contains("excused");
    }
}
