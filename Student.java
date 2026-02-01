import java.util.*;
import java.io.*;

public class Student {
    String projectTitle, projectDescription, projectStatus, rollNumber, projectCategory;
    int ch;

    Student(Scanner sc, String rollNumber) {
        this.rollNumber = rollNumber;

        while (true) {
            System.out.println("\n====================== Menu ======================");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Submit Idea  2. View Submitted Ideas  3. Logout");
            System.out.print("Enter your choice: ");
            ch = sc.nextInt();
            sc.nextLine(); // clear buffer

            if (ch == 1) {
                submitIdea(sc);
            } else if (ch == 2) {
                display();
            } else if (ch == 3) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
    //Submit Ideas
    void submitIdea(Scanner sc) {
        System.out.print("\n\nEnter Project Title: ");
        projectTitle = sc.nextLine();

        System.out.print("Enter Project Description: ");
        projectDescription = sc.nextLine();

        projectStatus = "Submitted";
        saveStudentIdeaToFile();

        System.out.println("Idea submitted successfully!");
    }
    //Display Submitted Ideas
    void display() {
        boolean found = false;
        try (BufferedReader reader  = new BufferedReader(new FileReader("student_ideas.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(rollNumber)) {
                    System.out.println(line.replace(",", "\n"));
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No ideas found.");
            }
        } catch (IOException e) {
            System.out.println("No ideas found.");
        }
    }
    // Save student idea to file
    void saveStudentIdeaToFile() {
        try (FileWriter writer = new FileWriter("student_ideas.txt", true)) {
            writer.write("Reg No: " + rollNumber +
                    ", Project Title: " + projectTitle +
                    ", Project Description: " + projectDescription +
                    ", Status: " + projectStatus + "\n");
        } catch (IOException e) {
            System.out.println("Error saving student idea.");
        }
    }
}
