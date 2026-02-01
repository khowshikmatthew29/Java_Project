import java.util.Scanner;
import java.io.*;

public class Welcome {

    private static final String ADMIN_PASSWORD = "HR";
    private static final String SUPERVISOR_PASSWORD = "SR";

    String firstName, lastName, rollNum, gender, passcode1, passcode2;
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
                admin(key);  //Admin Login
            } else if (ch == 2) {
                supervisor(key);  //Supervisor Login
            } else if (ch == 3) {
                student(key);  //Student Login
            } else if (ch == 4) {
                break;  // Quit
            } else{
                System.out.println("Invalid choice");  //Invalid Choice
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

            saveStudentToFile();
        } else if (choice == 2) {
            studentLogin(key);
        }else{
            System.out.println("Invalid choice");
        }
    }
    // Save student data to file
    void saveStudentToFile() {
          try (FileWriter writefile = new FileWriter(".\\students.txt", true)) {
            writefile.write("Roll Number:," + rollNum + ",Password: ," + passcode1 +
                    ",First Name: ," + firstName + ",Last Name: ," + lastName + ",Gender: ," + gender + ",Age: ," + age + "\n");
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
          
            System.out.print("Enter roll number: ");
            String roll = key.nextLine().toUpperCase();
        
            System.out.print("Enter password: ");
            String pass = key.nextLine();
          
            String line;

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
    // Check password
    String checkPassword(Scanner key) {
        while (true) {
            System.out.print("Create password: ");
            passcode1 = key.nextLine();

            System.out.print("Re-enter password: ");
            passcode2 = key.nextLine();

            if (passcode1.equals(passcode2)){
                break;
            } 
            System.out.println("Passwords do not match.");
        }
        return passcode1;
    }
}
