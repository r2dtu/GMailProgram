/**
 * This class contains information on a given student in the SAT class.
 * 
 * Currently unsupported: (Un)excused absences count
 */
package gmailprogram;

/**
 * @author David
 */
public class Student {
    
    // Student profile: cannot be changed
    private final String name, email;
    private final int id;
    private final String[] quizScores; // Quiz scores in order (#1-7)
    private final String[] testScores; // In-Class & Take Home Tests
    private final String[] hwResults; // Completed HW dates
    private final String[] gradeResults;
    /* Grade Results Format:
       HW Subtotal, In Class Quiz Subtotal, Online Quiz Subtotal, 
       Raw % Grade, Letter Grade */
    
    // Will be determined based on files
    private int numOfMissingAssignments;
    private int numOfRedoAssignments;
    private int numOfExcusedAssignments;
    private int blanks;
    private int numOfAbsences, numOfUnexcusedAbsences;
    
    /**
     * Creates a Student object to store information about their scores.
     * @param name student name
     * @param id student ID
     * @param email student email
     * @param quizSize how many quizzes they'll take
     * @param testSize how many tests they'll take
     * @param hwSize how many HW assignments they'll have
     * @param gradeSize how many subtotal scores to calculate for grade
     */
    public Student(String name, int id, String email, 
            int quizSize, int testSize, int hwSize, 
            int gradeSize) {
        
        // Store their profile (name, ID, email, grade)
        this.name = name;
        this.id = id;
        this.email = email;
        
        // Default scores are blank
        quizScores = new String[quizSize];
        for (int i = 0; i < quizSize; i++) {
            quizScores[i] = "";
        }
        testScores = new String[testSize];
        for (int i = 0; i < testSize; i++) {
            testScores[i] = "";
        }
        hwResults = new String[hwSize];
        for (int i = 0; i < hwSize; i++) {
            hwResults[i] = "";
        }
        gradeResults = new String[gradeSize];
        for (int i = 0; i < gradeSize; i++) {
            gradeResults[i] = "";
        }
    }

    /**
     * Get the student's name.
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the student's email.
     * @return email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Get the student's ID.
     * @return 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get the student's quiz scores.
     * @return quizScores
     */
    public String[] getQuizScores() {
        return quizScores;
    }
    
    /**
     * Get the student's test scores.
     * @return testScores
     */
    public String[] getTestScores() {
        return testScores;
    }
    
    /**
     * Get the student's HW scores.
     * @return hwResults
     */
    public String[] getHWResults() {
        return hwResults;
    }
    
    /**
     * Set the student's number of missing assignments.
     * @param numOfMissingAssignments number of missing assignments to set
     */
    public void setNumOfMissingAssignments(int numOfMissingAssignments) {
        this.numOfMissingAssignments = numOfMissingAssignments;
    }

    /**
     * Get the student's number of missing assignments.
     * @return the numOfMissingAssignments
     */    
    public int getNumOfMissingAssignments() {
        return numOfMissingAssignments;
    }

    /**
     * Set the student's number of redo assignments.
     * @param numOfRedoAssignments number of redo assignments to set
     */
    public void setNumOfRedoAssignments(int numOfRedoAssignments) {
        this.numOfRedoAssignments = numOfRedoAssignments;
    }

    /**
     * Set the student's number of redo assignments.
     * @return the numOfRedoAssignments
     */    
    public int getNumOfRedoAssignments() {
        return numOfRedoAssignments;
    }

    /**
     * Set the student's number of excused assignments.
     * @param numOfExcusedAssignments  number of excused assignments to set
     */
    public void setNumOfExcusedAssignments(int numOfExcusedAssignments) {
        this.numOfExcusedAssignments = numOfExcusedAssignments;
    }

    /**
     * Set the student's number of excused assignments.
     * @return the numOfExcusedAssignments
     */    
    public int getNumOfExcusedAssignments() {
        return numOfExcusedAssignments;
    }
    
    /**
     * Get the student's number of completed assignments by subtracting the 
     * total number of assignments thus far by the number of missing/redo/
     * excused assignments.
     * @return the number of completed assignments thus far
     */    
    public int getNumOfCompletedAssignments() {
        return GMailConstants.NUM_OF_HW_ASSIGNMENTS - 
                (numOfMissingAssignments + numOfRedoAssignments +
                numOfExcusedAssignments + blanks);
    }

    /**
     * Get the student's number of absences.
     * @return the numOfAbsences
     */
    public int getNumOfAbsences() {
        return numOfAbsences;
    }

    /**
     * Set the student's number of absences.
     * @param numOfAbsences the numOfAbsences to set
     */
    public void setNumOfAbsences(int numOfAbsences) {
        this.numOfAbsences = numOfAbsences;
    }

    /**
     * Get the student's number of unexcused absences.
     * @return the numOfUnexcusedAbsences
     */
    public int getNumOfUnexcusedAbsences() {
        return numOfUnexcusedAbsences;
    }

    /**
     * Set the student's number of unexcused absences.
     * @param numOfUnexcusedAbsences the numOfUnexcusedAbsences to set
     */
    public void setNumOfUnexcusedAbsences(int numOfUnexcusedAbsences) {
        this.numOfUnexcusedAbsences = numOfUnexcusedAbsences;
    }
    
    /**
     * Gets the student's grade.
     * @return grade
     */
    public String getGrade() {
        return getGradeResults()[getGradeResults().length - 2];
    }
    
    /**
     * Gets the student's letter grade.
     * @return letterGrade
     */
    public String getLetterGrade() {
        return getGradeResults()[getGradeResults().length - 1];
    }

    /**
     * Gets the student's grades.
     * @return gradeResults
     */
    public String[] getGradeResults() {
        return gradeResults;
    }
    
    public int getBlanks() {
        return blanks;
    }
    
    public void setBlanks(int blanks) {
        this.blanks = blanks;
    }
    
}
