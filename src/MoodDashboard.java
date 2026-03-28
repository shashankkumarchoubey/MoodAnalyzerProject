import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class MoodDashboard {
    private int totalJoy = 0;
    private int totalAnger = 0;
    private int totalSadness = 0;
    private int totalAnxiety = 0;
    private int totalRants = 0;
    
    private String dirName;
    private String fileName;

    public MoodDashboard() {
        // 1. Find out where Java is currently running
        String currentPath = System.getProperty("user.dir");
        
        // 2. If running from inside 'src', go up one directory
        if (currentPath.endsWith("src")) {
            dirName = "../output";
        } else {
            dirName = "output";
        }
        
        fileName = dirName + "/user_mood_data.txt";

        // 3. Create the output folder if it doesn't exist
        File directory = new File(dirName);
        if (!directory.exists()) {
            directory.mkdirs(); 
        }
        
        loadData(); // Load previous data
    }

    // Storage Feature: Save to file (Updated to use lowercase fileName)
    public void saveData() {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(totalJoy + "," + totalAnger + "," + totalSadness + "," + totalAnxiety + "," + totalRants);
        } catch (IOException e) {
            System.out.println("Error saving your data.");
        }
    }

    // Storage Feature: Load from file (Updated to use lowercase fileName)
    private void loadData() {
        File file = new File(fileName);
        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                if (fileScanner.hasNextLine()) {
                    String[] data = fileScanner.nextLine().split(",");
                    totalJoy = Integer.parseInt(data[0]);
                    totalAnger = Integer.parseInt(data[1]);
                    totalSadness = Integer.parseInt(data[2]);
                    totalAnxiety = Integer.parseInt(data[3]);
                    totalRants = Integer.parseInt(data[4]);
                }
            } catch (Exception e) {
                System.out.println("Could not load previous data. Starting fresh.");
            }
        }
    }

    // Reset Feature
    public void resetDashboard() {
        totalJoy = 0; totalAnger = 0; totalSadness = 0; totalAnxiety = 0; totalRants = 0;
        saveData(); // Overwrite the file with zeros
        System.out.println("\n[!] All your previous mood data has been erased. Fresh start!");
    }

    // Helper: Finds the dominant emotion for the immediate result
    public String getImmediateDominantEmotion(Map<String, Integer> scores) {
        String dominant = "NEUTRAL";
        int max = 0;
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                dominant = entry.getKey();
            }
        }
        return dominant;
    }

    public void addSessionScores(Map<String, Integer> sessionScores) {
        totalJoy += sessionScores.get("JOY");
        totalAnger += sessionScores.get("ANGER");
        totalSadness += sessionScores.get("SADNESS");
        totalAnxiety += sessionScores.get("ANXIETY");
        totalRants++;
        saveData(); // Automatically save after every new rant
    }

    public void printDashboard() {
        System.out.println("\n=========================================");
        System.out.println("          YOUR MOOD DASHBOARD            ");
        System.out.println("=========================================");
        System.out.println("Total Entries Analyzed: " + totalRants);

        int totalPoints = totalJoy + totalAnger + totalSadness + totalAnxiety;

        if (totalPoints == 0) {
            System.out.println("Status: NEUTRAL. No strong emotions detected yet.");
            System.out.println("Remark: Keep writing! Speak your mind to see your emotional profile.");
            System.out.println("=========================================\n");
            return;
        }

        double joyPct = ((double) totalJoy / totalPoints) * 100;
        double angerPct = ((double) totalAnger / totalPoints) * 100;
        double sadnessPct = ((double) totalSadness / totalPoints) * 100;
        double anxietyPct = ((double) totalAnxiety / totalPoints) * 100;

        System.out.printf("Joy:     %.1f%%\n", joyPct);
        System.out.printf("Anger:   %.1f%%\n", angerPct);
        System.out.printf("Sadness: %.1f%%\n", sadnessPct);
        System.out.printf("Anxiety: %.1f%%\n", anxietyPct);
        
        System.out.println("-----------------------------------------");
        printRemark(joyPct, angerPct, sadnessPct, anxietyPct);
        System.out.println("=========================================\n");
    }

    private void printRemark(double joy, double anger, double sadness, double anxiety) {
        System.out.print("System Remark: ");
        if (joy > anger && joy > sadness && joy > anxiety) {
            System.out.println("You seem to be in a fantastic headspace! Keep radiating that positive energy.");
        } else if (anger > joy && anger > sadness && anger > anxiety) {
            System.out.println("You are running a bit hot. Ranting helps, but maybe take a deep breath or a short walk?");
        } else if (sadness > joy && sadness > anger && sadness > anxiety) {
            System.out.println("It sounds like you're going through a tough time. Please be kind to yourself today.");
        } else if (anxiety > joy && anxiety > anger && anxiety > sadness) {
            System.out.println("You seem quite stressed. Try to take things one step at a time. You've got this.");
        } else {
            System.out.println("Your emotions are complex and mixed right now. Take some time to process how you feel.");
        }
    }
}