/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ca1;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

// @author DTG Dylan Geraghty
// https://github.com/Dyluvian/CA1
// DONE rough version, outputs examples correctly, copes with looping, lots of validations ready, distinction #1 should work
// TODO Set up menu, fully test validations, additional validations, distinctions #2 and #3, access closure message, general cleanup

public class CA1 {
    public static void main(String[] args) {

// Try-catch general exception #1
        try {

// Readme
            System.out.println("Intro TBD");

// Read in text file
            Scanner readStudentsTxt = new Scanner(new FileReader("C:\\Users\\User\\Desktop\\students.txt")); // replace with your local path

// If empty
            if (!readStudentsTxt.hasNext()) {
                System.out.println("Input: nothing. No output. This file is empty.\nNothing in, nothing out.");
                System.exit(0);
            }

// Otherwise, start loop
            while (readStudentsTxt.hasNextLine()) {

// Parse to string
                String studentsnameline = readStudentsTxt.nextLine();
                System.out.println("---\n***** Validating next line. *****");

// Validation
// Validation line 1: If line is empty, throw error
                if (studentsnameline.isEmpty()) {
                    System.out.println("Input, line 1: nothing. No output. This line is blank. Please insert some input.");
                    continue;
                }
// Validation line 1: If first line is not empty but first name is invalid, throw error
                else if (!studentsnameline.matches("[a-zA-Z]+.*")) {
                    System.out.println("Input, line 1: \"" + studentsnameline + "\". No output. The input must begin with a first name (letters only).");
                    continue;
                }
// Validation line 1: If first name is valid but no space after first name, throw error
                else if (!studentsnameline.matches("[a-zA-Z]+\\s{1}.*")) {
                    System.out.println("Input, line 1: \"" + studentsnameline + "\". No output. The first name must be followed by a space. Please insert a space.");
                    continue;
                }
// Validation line 1: If space after first name but second name is invalid, throw error
                else if (!studentsnameline.matches("[a-zA-Z]+\\s{1}[a-zA-Z0-9]+.*")) {
                    System.out.println("Input, line 1: \"" + studentsnameline + "\". No output. The second name after the space must consist of letters and/or numbers.");
                    continue;
                }
// Validation line 1: If second name is valid, confirm, define second name, and check second line
                else {
                    System.out.println("Input, line 1: \"" + studentsnameline + "\". Validated.");
                    String studentssecondname = studentsnameline.substring(studentsnameline.indexOf(' '));
                    String numberofclassesline = readStudentsTxt.nextLine();
// Validation line 2: If second line is empty, throw error
                    if (numberofclassesline.isEmpty()) {
                        System.out.println("Input, line 2: nothing. No output. This line is blank. Please insert some input.");
                        continue;
                    }
// Validation line 2: If second line is not empty but number of classes is invalid
                    else if (!numberofclassesline.matches("[1-8]{1}")) {
                        System.out.println("Input, line 2: \"" + numberofclassesline + "\". No output. The number of classes may only range from 1 to 8.");
                        continue;
                    }
// Validation line 2: If number of classes is valid, confirm, define workload, and check third line
                    else {
                        System.out.println("Input, line 2: \"" + numberofclassesline + "\". Validated.");
                        String workload = null;
                        if (numberofclassesline.matches("1")) {
                            workload = ("Very Light");
                        }
                        if (numberofclassesline.matches("2")) {
                            workload = ("Light");
                        }
                        if (numberofclassesline.matches("[3-5]")) {
                            workload = ("Part Time");
                        }
                        if (numberofclassesline.matches("[6-8]")) {
                            workload = ("Full Time");
                        }
                        String studentnumberline = readStudentsTxt.nextLine();
// Validation line 3: If third line is empty, throw error
                        if (studentnumberline.isEmpty()) {
                            System.out.println("Input, line 3: nothing. No output. This line is blank. Please insert some input.");
                            continue;
                        }
// Validation line 3: If third line is not empty but first two characters are not valid numbers
                        else if (!studentnumberline.matches("[2-9]{1}[0-9]{1}.*")) {
                            System.out.println("Input, line 3: \"" + studentnumberline + "\". No output. The student number year (first two characters of this line) must be 20 or higher.");
                            continue;
                        }
// Validation line 3: If first two characters are valid numbers but next two characters are not letters
                        else if (!studentnumberline.matches("[2-9]{1}[0-9]{1}[a-zA-Z]{2}.*")) {
                            System.out.println("Input, line 3: \"" + studentnumberline + "\". No output. The two characters following the student number (third and fourth characters of this line) must be letters.");
                            continue;
                        }
// Validation TBD line 3: If any letters interrupt the final numerical sequence
// Validation TBD line 3: If the sequence does not end in numbers
// Validation line 3: If the third line is valid, confirm, print, and continue
                        else if (studentnumberline.matches("[2-9]{1}[0-9]{1}[a-zA-Z]{2}[a-zA-Z0-9]{1}[0-9]+")) {
                            System.out.println("Input, line 3: \"" + studentnumberline + "\". Validated.");
                            System.out.println("***** Successful! Output: *****\n" + studentnumberline + " -" + studentssecondname + "\n" + workload);
                            String output = (studentnumberline + " -" + studentssecondname + "\n" + workload + "\n");
                            FileWriter writeStatusTxt = new FileWriter("C:\\Users\\User\\Desktop\\status.txt", true); // replace with your local path
                            writeStatusTxt.write(output);
                            writeStatusTxt.close();
                            continue;
                        }
                    }
                }
                
// Closure
                readStudentsTxt.close();
                System.out.println("---\nAll valid lines have been appended to status.txt.\nHave a wonderful day.");
            }
        }
// Try-catch general exception #2
        catch (Exception e) {
            System.out.println("A general error has occurred. Please ensure a valid text file was provided.");
        }
    }
}