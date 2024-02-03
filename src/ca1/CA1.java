package ca1;

// Import utilities for reading/writing/scanning input
import java.io.FileReader; // PLEASE NOTE for convenience: file path to adjust is on line #24
import java.io.FileWriter; // PLEASE NOTE for convenience: file path to adjust is on line #179
import java.util.Scanner;

public class CA1 {

/*/ @author DTG Dylan Geraghty
    https://github.com/Dyluvian/CA1
    Enjoy! This submission attempts to satisfy all distinction work criteria. /*/

    public static void main(String[] args) {

/*/ This could potentially have been broken into multiple methods for aesthetics but advice was NOT to do so for the sake of it.
    Did not see practical value in a more piecemeal construction, so all is processed through main. /*/

// Try-catch exception for any general errors #1
        try {

// Initialise scanners for deciding file-based or manual/user input, and reading either form of input
            Scanner askAutomaticOrManual = new Scanner(System.in);
            Scanner readStudentsTxt = new Scanner(new FileReader("C:\\Users\\User\\Desktop\\students.txt")); // replace with your local path
            Scanner readUserInput = new Scanner(System.in);

// Welcome message and instructions
            System.out.println("Welcome!\nThis program is (optimistically) designed to process the details of fictitious students as instructed.\nFeed it details to be validated in the following format:\nLine #1: <First Name - letters only> <Second Name - letters and/or numbers>\nLine #2: <Number of classes - integer from 1 to 8 inclusive>\nLine #3: <Student number - must be a minimum of 6 characters with the first 2 characters forming a number of 20 or higher, the third and fourth characters (and possibly fifth) being letters, and everything after the last letter character forming a number from 1 to 200.>\nFully validated sets of data will be appended to C:\\Users\\User\\Desktop\\status.txt, while sets with any invalid data will be disregarded.\nHope nothing breaks! --DSG\n---");
            System.out.println("Would you like to input data via file (processed automatically) or input manually via the console?\nBy default, the program checks for files at C:\\Users\\User\\Desktop\\students.txt.\nInput via file will scan for multiple sequences of student data - one sequence should be positioned below another (e.g. a second sequence should begin on line #4).\nInput via console is submitted line by line.\n---\nFor file-based validation, type 1 into the console and press enter.\nFor manual input, type 2 instead and press enter.");

// Next input determines either file-based or manual/user input
            String automaticOrManual = askAutomaticOrManual.nextLine();

// Set up variables for later - inputs, workload, line number in file, and step in sequence for manual input. This prevents having to reinput validated steps in manual mode
            String studentNameInput = null;
            String numberOfClassesInput = null;
            String workload = null;
            String studentNumberInput = null;
            int lineNumber = 0;
            int currentManualStep = 1;

// Begin the overarching loop
            while (true) {

// If using file input, close the loop and program once end of file is reached. This also handles an empty file
                if (!readStudentsTxt.hasNextLine() && automaticOrManual.matches("1")) {
                    System.out.println("---\nEnd of file reached! All lines and sequences have been checked.\nHave a wonderful day.");
                    System.exit(0);
                } else if (readStudentsTxt.hasNextLine() || readUserInput.hasNextLine()) {

// Choose either file-based input or manual input. If already chosen then skip this
                    while (!automaticOrManual.matches("[1|2]")) {
                        System.out.println("---\nInvalid. A single '1' (file-based data) or '2' (manual input) is required to indicate your preference.\n---");
                        automaticOrManual = askAutomaticOrManual.nextLine();
                    }

// Seek the next input sequence, whether file-based or manual. Increase the printed line number each time if reading lines in a file
                    if (automaticOrManual.matches("1")) {
                        System.out.println("---\n***** Checking next line for new sequence (student name)... *****");
                        studentNameInput = readStudentsTxt.nextLine();
                        lineNumber++;
                    } else if (automaticOrManual.matches("2") && currentManualStep == 1) {
                        System.out.println("---\nTo begin submitting new data, please type the student name into the console and press enter.\nREMINDER: The student name should be formatted: <First Name> <Second Name>. The first name must be letters only. The second name must be letters and/or numbers.");
                        studentNameInput = readUserInput.nextLine();
                        lineNumber = 1;
                    }

/*/ Validation process is grouped together here.
    All of this is built around ugly, disgusting, hammy regex patterns. I love these /*/

// Validation step 1: If expected student name is empty, throw snarky error
                    if (studentNameInput.isEmpty()) {
                        System.out.println("Line #" + lineNumber + " (student name): blank. Invalid student name. Nothing in, nothing out. Please insert some input.\nNo output.");
                        continue;
                    }
// Validation step 1: If expected student name is not empty but first name is invalid, throw error
                    else if (!studentNameInput.matches("[a-zA-Z]+.*")) {
                        System.out.println("Line #" + lineNumber + " (student name): \"" + studentNameInput + "\". Invalid student name. The student name must begin with a first name (letters only).\nNo output.");
                        continue;
                    }
// Validation step 1: If first name begins with letters but no space exists after first name, throw error
                    else if (!studentNameInput.matches("[a-zA-Z]+\\s{1}.*")) {
                        System.out.println("Line #" + lineNumber + " (student name): \"" + studentNameInput + "\". Invalid student name. The first name must consist of letters only and be followed by a space.\nNo output.");
                        continue;
                    }
// Validation step 1: If space after first name exists but second name is invalid, throw error
                    else if (!studentNameInput.matches("[a-zA-Z]+\\s{1}[a-zA-Z0-9]+")) {
                        System.out.println("Line #" + lineNumber + " (student name): \"" + studentNameInput + "\". Invalid student name. The second name after the single expected space must consist of letters and/or numbers.\nNo output.");
                        continue;
                    }
// Validation step 1: If second name is valid, confirm, define second name, and seek number of classes
                    else if (studentNameInput.matches("[a-zA-Z]+\\s{1}[a-zA-Z0-9]+")) {
                        if (automaticOrManual.matches("1") || currentManualStep == 1) {
                            System.out.println("Line #" + lineNumber + " (student name): \"" + studentNameInput + "\". Validated! Proceeding to next line (number of classes)...");
                            currentManualStep = 2;
                        }
                        String studentSecondNameInput = studentNameInput.substring(studentNameInput.indexOf(' '));
                        if (automaticOrManual.matches("1")) {
                            numberOfClassesInput = readStudentsTxt.nextLine();
                            lineNumber++;
                        } else if (automaticOrManual.matches("2") && currentManualStep == 2) {
                            System.out.println("---\nPlease type the number of classes into the console and press enter.\nREMINDER: The number of classes must be an integer value between 1 and 8 (inclusive).");
                            numberOfClassesInput = readUserInput.nextLine();
                            lineNumber = 2;
                        }

// Validation step 2: If expected number of classes is empty, throw error
                        if (numberOfClassesInput.isEmpty()) {
                            System.out.println("Line #" + lineNumber + " (number of classes): blank. Invalid number of classes. Nothing in, nothing out. Please insert some input.\nNo output.");
                            continue;
                        }
// Validation step 2: If expected number of classes is not empty but input is out of scope
                        else if (!numberOfClassesInput.matches("[1-8]{1}")) {
                            System.out.println("Line #" + lineNumber + " (number of classes): \"" + numberOfClassesInput + "\". Invalid number of classes. The number of classes must be an integer value between 1 and 8 (inclusive).\nNo output.");
                            continue;
                        }
// Validation step 2: If number of classes is valid, confirm, define workload based on number, and seek student number
                        else if (numberOfClassesInput.matches("[1-8]{1}")) {
                            if (numberOfClassesInput.matches("1")) {
                                workload = ("Very Light");
                            }
                            if (numberOfClassesInput.matches("2")) {
                                workload = ("Light");
                            }
                            if (numberOfClassesInput.matches("[3-5]")) {
                                workload = ("Part Time");
                            }
                            if (numberOfClassesInput.matches("[6-8]")) {
                                workload = ("Full Time");
                            }
                            if (automaticOrManual.matches("1") || currentManualStep == 2) {
                                System.out.println("Line #" + lineNumber + " (number of classes): \"" + numberOfClassesInput + "\". Validated! The workload is: \"" + workload + "\". Proceeding to next line (student number)...");
                                currentManualStep = 3;
                            }
                            if (automaticOrManual.matches("1")) {
                                studentNumberInput = readStudentsTxt.nextLine();
                                lineNumber++;
                            } else if (automaticOrManual.matches("2") && currentManualStep == 3) {
                                System.out.println("---\nPlease type the student number into the console and press enter.\nREMINDER: The student number must be a minimum of 6 characters with the first 2 characters forming a number of 20 or higher, the third and fourth characters (and possibly fifth) being letters, and everything after the last letter character forming a number from 1 to 200.");
                                studentNumberInput = readUserInput.nextLine();
                                lineNumber = 3;
                            }

// Validation step 3: If expected student number is empty, throw error
                            if (studentNumberInput.isEmpty()) {
                                System.out.println("Line #" + lineNumber + " (student number): blank. Invalid student number. Nothing in, nothing out. Please insert some input.\nNo output.");
                                continue;
                            }
// Validation step 3: If expected student number is not empty but first two characters are not valid numbers
                            else if (!studentNumberInput.matches("[2-9]{1}[0-9]{1}.*")) {
                                System.out.println("Line #" + lineNumber + " (student number): \"" + studentNumberInput + "\". Invalid student number. The student number year (first two characters of this line) must form a number - 20 or higher.\nNo output.");
                                continue;
                            }
// Validation step 3: If first two characters are valid numbers but next two characters are not letters
                            else if (!studentNumberInput.matches("[2-9]{1}[0-9]{1}[a-zA-Z]{2}.*")) {
                                System.out.println("Line #" + lineNumber + " (student number): \"" + studentNumberInput + "\". Invalid student number. The two characters following the student number (third and fourth characters of this line) must be letters.\nNo output.");
                                continue;
                            }
// Validation step 3: If next two characters are letters but following character is not a letter or number
                            else if (!studentNumberInput.matches("[2-9]{1}[0-9]{1}[a-zA-Z]{2}[a-zA-Z0-9].*")) {
                                System.out.println("Line #" + lineNumber + " (student number): \"" + studentNumberInput + "\". Invalid student number. The third character following the student number (fifth character of this line) must be a letter or a number.\nNo output.");
                                continue;
                            }
// Validation step 3: If the above is not followed by and concluded with a numerical sequence from 1-200
                            else if (!studentNumberInput.matches("[2-9]{1}[0-9]{1}[a-zA-Z]{2}([a-zA-Z]{1}((1[0-9]{2}|[1-9][0-9]?)(\\\\.[0-9])?|200(\\\\.0)?|0\\\\.[1-9])$|((1[0-9]{2}|[1-9][0-9]?)(\\\\.[0-9])?|200(\\\\.0)?|0\\\\.[1-9]))$")) {
                                System.out.println("Line #" + lineNumber + " (student number): \"" + studentNumberInput + "\". Invalid student number. The letters following the student number must be followed by a number from 1 to 200.\nNo output.");
                                continue;
                            }
// Validation step 3: If student number contains fewer than six characters
                            else if (!studentNumberInput.matches("[0-9a-zA-Z]{6,}")) {
                                System.out.println("Line #" + lineNumber + " (student number): \"" + studentNumberInput + "\". Invalid student number. The student number must contain a minimum of six characters.\nNo output.");
                                continue;
                            }
// Validation step 3: If student number is valid, confirm, print, reset to step 1 for manual input, and continue
                            else if (studentNumberInput.matches("[2-9]{1}[0-9]{1}[a-zA-Z]{2}([a-zA-Z]{1}((1[0-9]{2}|[1-9][0-9]?)(\\\\.[0-9])?|200(\\\\.0)?|0\\\\.[1-9])$|((1[0-9]{2}|[1-9][0-9]?)(\\\\.[0-9])?|200(\\\\.0)?|0\\\\.[1-9]))$")) {
                                System.out.println("Line #" + lineNumber + " (student number): \"" + studentNumberInput + "\". Fully validated!");
                                String output = (studentNumberInput + " -" + studentSecondNameInput + "\n" + workload + "\n");
                                System.out.println("***** The following has been appended to status.txt: *****\n" + studentNumberInput + " -" + studentSecondNameInput + "\n" + workload);
                                FileWriter writeStatusTxt = new FileWriter("C:\\Users\\User\\Desktop\\status.txt", true); // replace with your local path
                                writeStatusTxt.write(output);
                                writeStatusTxt.close();
                                currentManualStep = 1;
                                continue;
                            }
                        }
                    }
                }
            }
        } // Try-catch exception for any general errors #2
        catch (Exception e) {
            System.out.println("A general error has occurred. Please ensure a valid text file or valid input was provided.");
        }
    }
}
