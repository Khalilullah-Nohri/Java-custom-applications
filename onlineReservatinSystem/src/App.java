import java.util.Scanner;

public class App {
    signUp signup = new signUp();
    signIn signin = new signIn();

    public App() {

        System.out.println("\n\t****** Welcome to Online Rservation System ******\n");
        System.out.println("1- SignUp \n\n2- SignIn\n\n3- Any key for exit\n");

        Scanner input = new Scanner(System.in);
        System.out.print("Enter ur choice: ");
        String choice = input.next(); // Read user input
        if (choice.equals("1")) {
            {
                signup.signUp();
                System.out.println("\n********************************\n*    Successfully Exit   *\n");

            }

        } else if (choice.equals("2")) {
            signin.log_In();
        } else {

            System.out.println("\n**** bye bye ****\n");
        }
    }

    public static void main(String args[]) {
        App myObj = new App();

    }
}
