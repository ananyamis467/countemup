package org.example;

import java.util.Scanner;

//driver program
public class App {
   public static void main(String[] args) {
        GroceryCounter counter = new GroceryCounter();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("Welcome to Grocery Counter System");
        
        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            
            String choice = scanner.nextLine().trim();
            System.out.println();
            
            switch (choice) {
                case "1":
                    counter.tens();
                    System.out.println("Added $10.00");
                    displayTotal(counter);
                    break;
                    
                case "2":
                    counter.ones();
                    System.out.println("Added $1.00");
                    displayTotal(counter);
                    break;
                    
                case "3":
                    counter.tenths();
                    System.out.println("Added $0.10");
                    displayTotal(counter);
                    break;
                    
                case "4":
                    counter.hundredths();
                    System.out.println("Added $0.01");
                    displayTotal(counter);
                    break;
                    
                case "5":
                    displayTotal(counter);
                    break;
                    
                case "6":
                    displayOverflowInfo(counter);
                    break;
                    
                case "7":
                    counter.clear();
                    System.out.println("✓ Counter cleared!");
                    displayTotal(counter);
                    break;
                    
                case "8":
                    demonstrateOverflow(counter);
                    break;
                    
                case "9":
                    System.out.println("Thank you for using Grocery Counter System!");
                    running = false;
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    /**
     * displays the main menu options
     */
    private static void displayMenu() {
        System.out.println("              MAIN MENU");
        System.out.println("---------------------------------------");
        System.out.println("1. Add $10.00 (Tens)");
        System.out.println("2. Add $1.00  (Ones)");
        System.out.println("3. Add $0.10  (Tenths)");
        System.out.println("4. Add $0.01  (Hundredths)");
        System.out.println("5. Show Current Total");
        System.out.println("6. Show Overflow Count");
        System.out.println("7. Clear Counter");
        System.out.println("8. Demonstrate Overflow");
        System.out.println("9. Exit");
    }
    
    /**
     * displays the current total with formatting
     */
    private static void displayTotal(GroceryCounter counter) {
        System.out.println("║   Current Total: " + counter.total() + "   ║");
    }
    
    /**
     * displays overflow information
     */
    private static void displayOverflowInfo(GroceryCounter counter) {
        System.out.println("\n Overflow Information:");
        System.out.println("   Total Overflows: " + counter.overflows());
        System.out.println("   Current Total: " + counter.total());
        
        if (counter.overflows() > 0) {
            System.out.println("\n   The counter has wrapped around " + counter.overflows() + " time(s).");
            System.out.println("   Each overflow represents going past $99.99 back to $0.00.");
        } else {
            System.out.println("\n    No overflows yet. Counter can go up to $99.99.");
        }
    }
    
    /**
     * demonstrates the overflow behavior by building up to $99.99 and beyond
     */
    private static void demonstrateOverflow(GroceryCounter counter) {
        System.out.println("\n Demonstrating Overflow Behavior...\n");
        
        // Clear the counter first
        counter.clear();
        System.out.println("Starting with: " + counter.total());
        System.out.println("Overflow count: " + counter.overflows());
        
        // auild up to $99.99
        System.out.println("\nBuilding to $99.99...");
        for (int i = 0; i < 9; i++) {
            counter.tens();
        }
        for (int i = 0; i < 9; i++) {
            counter.ones();
        }
        for (int i = 0; i < 9; i++) {
            counter.tenths();
        }
        for (int i = 0; i < 9; i++) {
            counter.hundredths();
        }
        
        System.out.println("At maximum: " + counter.total());
        System.out.println("Overflow count: " + counter.overflows());
        
        // add one more cent to cause overflow
        System.out.println("\nAdding $0.01 (should overflow to $0.00)...");
        counter.hundredths();
        System.out.println("After overflow: " + counter.total());
        System.out.println("Overflow count: " + counter.overflows());
        
        // add $10 to show it continues working
        System.out.println("\nAdding $10.00 to show counter still works...");
        counter.tens();
        System.out.println("New total: " + counter.total());
        System.out.println("Overflow count: " + counter.overflows());
        
        System.out.println("\n✓ Overflow demonstration complete!");
    }
}
