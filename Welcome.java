import java.util.Scanner;
import java.io.*;

public class Welcome {

    private static final String ADMIN_PASSWORD = "HR";
    private static final String SUPERVISOR_PASSWORD = "SR";

    String firstName, lastName, rollNum, gender, passcode1, passcode2, city, college, color;
    int age, maxAttempts , numStdRegistered;

    Welcome(Scanner key) {
        try (BufferedReader reader = new BufferedReader(new FileReader(".\\students.txt"))) {
            while ((reader.readLine()) != null) {
                numStdRegistered++;
            }
        } catch (IOException e) {
            numStdRegistered = 0;
        }
        
        while (true) {
            System.out.println("\n==========================================");
            System.out.println("    Welcome to Idea Submission Tracker    ");
            System.out.println("==========================================");
            System.out.println("1.Admin  2.Supervisor  3.Student  4.Quit");
            
            System.out.print("Enter your role: ");
            int ch = key.nextInt();
            key.nextLine();
            if (ch == 1 ){
                admin(key);
            } else if (ch == 2) {
                supervisor(key);
            } else if (ch == 3) {
                student(key);
            } else if (ch == 4) {
                break;
            } else{
                System.out.println("Invalid choice");
            }
        }
    }
    // Admin login
    void admin(Scanner key){
        maxAttempts = 3;
        while(true){
            System.out.print("\nEnter the password: ");
            passcode1 = key.nextLine().toUpperCase();
            if (!passcode1.equals(ADMIN_PASSWORD)){
                if (maxAttempts == 0){
                    System.out.println("Login is not allowed");
                    break;
                }
                System.out.println("Wrong password. Try again");
                System.out.println("Remaining attempts: " + maxAttempts);
                maxAttempts -= 1;
            }else{
                System.out.println("Admin login Successful!");
                System.out.println("Number of students registered: " + numStdRegistered);
                break;
            }
        }
    }
    // Supervisor login
    void supervisor(Scanner key){
        maxAttempts = 3;
        while(true){
            System.out.print("\nEnter the password: ");
            passcode1 = key.nextLine().toUpperCase();
            if (!passcode1.equals(SUPERVISOR_PASSWORD)){
                if (maxAttempts == 0){
                    System.out.println("Login is not allowed");
                    break;
                }
                System.out.println("Wrong password. Try again");
                System.out.println("Remaining attempts: " + maxAttempts);
                maxAttempts -= 1;
            }else{
                System.out.println("Supervisor login Successful!");
                System.out.println("\nNumber of students registered: " + numStdRegistered);
                break;
            }
        }
    }
    //Student login
    void student(Scanner key) {
        System.out.println("\n      STUDENT      ");
        System.out.println("-------------------");
        System.out.println("1.Register  2.Login");
        System.out.print("Enter your choice: ");
        int choice = key.nextInt();
        key.nextLine();

        if (choice == 1) {
            System.out.print("\nFirst name: ");
            firstName = key.nextLine();

            System.out.print("Last name: ");
            lastName = key.nextLine();

            System.out.print("Gender(M/F/O): ");
            gender = key.nextLine().toUpperCase();
            if (gender.equals("M")){
                gender = "Male";
                System.out.println("Gender: Male");
            } else if (gender.equals("F")) {
                gender = "Female";
                System.out.println("Gender: Female");
            } else {
                gender = "Other";
                System.out.println("Gender: Other");
            }

            System.out.print("Age: ");
            age = key.nextInt();
            key.nextLine();

            System.out.print("College register number: ");
            rollNum = key.nextLine().toUpperCase();

            checkPassword(key);

            System.out.println("\n======== Security Questions =========");
            System.out.println("-------------------------------------");
            System.out.println("Answer the security question to reset your password if you forget.");
            System.out.println("1. In what city were you born?");
            city = key.nextLine().toUpperCase();
            System.out.println("2. What is the name of your college/university?");
            college = key.nextLine().toUpperCase();
            System.out.println("3. What is your favorite color?");
            color = key.nextLine().toUpperCase();

            saveStudentToFile();
        } else if (choice == 2) {
            studentLogin(key);
        }else{
            System.out.println("Invalid choice");
        }
    }
    // Save student data to file
    void saveStudentToFile() {
        if (isRegNumExists(rollNum)) {
            System.out.println("Error: Registration number already exists!");
            return;
        }
        
        try (FileWriter writefile = new FileWriter(".\\students.txt", true)) {
            writefile.write("Roll Number:," + rollNum + ",Password: ," + passcode1 +
                    ",First Name: ," + firstName + ",Last Name: ," + lastName + ",Gender: ," + gender + ",Age: ," + age + ",City: ," + city + ",College: ," + college + ",Color: ," + color + "\n");
            numStdRegistered++;
            System.out.println("Student registered successfully.");
        } catch (IOException e) {
            System.out.println("Error saving student data.");
        }
    }
    // Student login
    void studentLogin(Scanner key) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(".\\students.txt"))) {
 
            System.out.print("\nEnter roll number: ");
            String roll = key.nextLine().toUpperCase();
        
            System.out.print("Enter password or [F]orget password: ");
            String pass = key.nextLine().toUpperCase();
            String line;
            if (pass.equals("F")){
                forgetPassword(key, roll);
                return;
            }

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].trim().equals(roll) && data[3].trim().equals(pass)) {
                    System.out.println("Login successful!");
                    found = true;
                    new Student(key, roll);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading student data.");
        }

        if (!found && numStdRegistered > 0) {
            System.out.println("Invalid password or registration number");
        }else if (!found){
            System.out.println("No registrations are found!.");
        }
    }
    // Check if registration number exists
    boolean isRegNumExists(String regNum) {
        try (BufferedReader reader = new BufferedReader(new FileReader(".\\students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].trim().equals(regNum)) {
                    return true;
                }
            }
        }catch(Exception e){
            System.out.println("File not found!");
        }
        return false;
    }
    // Check password
    String checkPassword(Scanner key) {
        while (true) {
            System.out.print("Create password: ");
            passcode1 = key.nextLine().toUpperCase();

            System.out.print("Re-enter password: ");
            passcode2 = key.nextLine().toUpperCase();

            if (passcode1.equals(passcode2)){
                break;
            } 
            System.out.println("Passwords do not match.");
        }
        return passcode1;
    }

    // Forget Password - Reset using security questions
    void forgetPassword(Scanner key, String roll) {
        System.out.println("\n======== Password Reset ========");
        System.out.println("Answer security questions to reset your password.");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(".\\students.txt"))) {
            String line;
            boolean found = false;
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].trim().equals(roll)) {
                    found = true;
                    // data[13] = City, data[15] = College, data[17] = Color
                    String actualCity = data[13].trim();
                    String actualCollege = data[15].trim();
                    String actualColor = data[17].trim();
                    
                    int correctAnswers = 0;
                    
                    // Question 1: City
                    System.out.print("In what city were you born? ");
                    String answerCity = key.nextLine().toUpperCase();
                    if (answerCity.equals(actualCity)) {
                        correctAnswers++;
                    }
                    
                    // Question 2: College
                    System.out.print("What is the name of your college/university? ");
                    String answerCollege = key.nextLine().toUpperCase();
                    if (answerCollege.equals(actualCollege)) {
                        correctAnswers++;
                    }
                    
                    // Question 3: Color
                    System.out.print("What is your favorite color? ");
                    String answerColor = key.nextLine().toUpperCase();
                    if (answerColor.equals(actualColor)) {
                        correctAnswers++;
                    }
                    
                    // Verify at least 2 out of 3 correct
                    if (correctAnswers >= 2) {
                        System.out.println("\nSecurity questions answered correctly!");
                        System.out.println("-------- Reset Your Password --------");
                        String newPassword = checkPassword(key);
                        updatePassword(roll, newPassword);
                        System.out.println("Your password has been reset successfully.");
                    } else {
                        System.out.println("\nSecurity questions answered incorrectly.");
                        System.out.println("Password reset failed.");
                    }
                    break;
                }
            }
            
            if (!found) {
                System.out.println("Registration number not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading student data.");
        }
    }
    
    // Update password in file
    void updatePassword(String roll, String newPassword) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(".\\students.txt"));
            StringBuilder fileContent = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].trim().equals(roll)) {
                    // Update the password (data[3] is the password field)
                    data[3] = newPassword;
                    fileContent.append(String.join(",", data)).append("\n");
                } else {
                    fileContent.append(line).append("\n");
                }
            }
            reader.close();
            
            FileWriter writer = new FileWriter(".\\students.txt");
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error updating password.");
        }
    }
}
