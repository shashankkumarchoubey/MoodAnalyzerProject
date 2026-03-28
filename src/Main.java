import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TextAnalyzer analyzer = new TextAnalyzer();
        
        // This will automatically load your saved data from the file
        MoodDashboard dashboard = new MoodDashboard();

        System.out.println("Welcome to the Sentiment & Mood Analyzer!");
        System.out.println("Type whatever is on your mind. Rant, vent, or share your joy.");
        System.out.println("Commands:");
        System.out.println(" - Type 'dash' to see your overall mood dashboard.");
        System.out.println(" - Type 'reset' to erase all your saved data.");
        System.out.println(" - Type 'exit' to close the program.");
        System.out.println("---------------------------------------------------------");

        while (true) {
            System.out.print("\nYour text: ");
            String input = scanner.nextLine().trim();

            // Check commands
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Saving your data... Here is your final summary before you go:");
                dashboard.printDashboard();
                break;
            } else if (input.equalsIgnoreCase("dash")) {
                dashboard.printDashboard();
                continue;
            } else if (input.equalsIgnoreCase("reset")) {
                dashboard.resetDashboard();
                continue;
            }

            // Word count restriction check (Must be at least 6 words)
            String[] wordCountCheck = input.split("\\s+");
            if (wordCountCheck.length < 6) {
                System.out.println("[Warning] Please provide more context. A minimum of 6 words or a full sentence is required for accurate analysis.");
                continue;
            }

            // 1. Process the rant
            Map<String, Integer> currentScores = analyzer.analyze(input);
            
            // 2. Determine and print the immediate mood for this specific text
            String immediateMood = dashboard.getImmediateDominantEmotion(currentScores);
            System.out.println("-> Immediate Analysis: This text leans heavily towards " + immediateMood + ".");
            
            // 3. Send the data to the dashboard memory (which also saves it to the file)
            dashboard.addSessionScores(currentScores);
            
            System.out.println("-> Text analyzed and added to your profile! (Type 'dash' to view your overall state)");
        }

        scanner.close();
    }
}