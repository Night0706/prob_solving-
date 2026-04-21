import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<YouTubeVideo> allVideos = new ArrayList<>();
        boolean dataLoaded = false;

        System.out.println("=== YouTube Content Scheduling System ===");

        // -------------------------
        // 1. Data Input Method
        // -------------------------
        while (!dataLoaded) {
            try {
                System.out.println("\nPlease select a data input method:");
                System.out.println("1. Read from file");
                System.out.println("2. Random generation");
                System.out.print("Enter your choice (1 or 2): ");
                int inputMethod = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (inputMethod == 1) {
                    System.out.print("Enter the file name (e.g., videos.txt): ");
                    String filename = scanner.nextLine();
                    allVideos = readDataFromFile(filename);
                    if (!allVideos.isEmpty()) dataLoaded = true;
                } else if (inputMethod == 2) {
                    System.out.print("Enter the number of videos to generate: ");
                    int num = scanner.nextInt();
                    allVideos = generateRandomData(num);
                    dataLoaded = true;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number!");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        // -------------------------
        // 2. Algorithm Selection
        // -------------------------
        JobSequencingStrategy scheduler = null;
        while (scheduler == null) {
            try {
                System.out.println("\nPlease select the scheduling algorithm:");
                System.out.println("1. Earliest Deadline First (EDF)");
                System.out.println("2. Naive Recursive");
                // We will add Greedy and Brute-Force here later
                System.out.print("Enter algorithm number: ");
                int algoChoice = scanner.nextInt();

                switch (algoChoice) {
                    case 1:
                        scheduler = new EDFScheduler();
                        break;
                    case 2:
                        scheduler = new NativeRecu();
                        break;
                    default:
                        System.out.println("Algorithm not implemented yet. Please choose again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number!");
                scanner.nextLine();
            }
        }

        // -------------------------
        // 3. Apply Algorithm & Display Output
        // -------------------------
        List<YouTubeVideo> originalList = new ArrayList<>(allVideos);
        List<YouTubeVideo> selectedVideos = scheduler.schedule(allVideos);
        int totalProfit = scheduler.calculateTotalProfit(selectedVideos);

        System.out.println("\n============= FINAL OUTPUT =============");
        
        // a. The job details
        System.out.println("[a] All Video Tasks Details:");
        for (YouTubeVideo v : originalList) {
            System.out.println("  " + v.toString());
        }

        // b. The selected sequence
        System.out.println("\n[b] Selected Upload Sequence:");
        for (YouTubeVideo v : selectedVideos) {
            System.out.println("  " + v.toString());
        }

        // c. The total profit
        System.out.println("\n[c] Total Expected Views (Profit): " + totalProfit);

        // d. The unselected jobs
        System.out.println("\n[d] Unselected Videos (Missed Deadline):");
        originalList.removeAll(selectedVideos);
        if (originalList.isEmpty()) {
            System.out.println("  None (All videos were successfully scheduled!)");
        } else {
            for (YouTubeVideo v : originalList) {
                System.out.println("  " + v.toString());
            }
        }
        System.out.println("========================================");
        
        scanner.close();
    }

    // Helper Method: Read from external file
    public static List<YouTubeVideo> readDataFromFile(String filePath) {
        List<YouTubeVideo> list = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File(filePath));
            if (fileScanner.hasNextLine()) fileScanner.nextLine(); // Skip header
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                if (data.length == 3) {
                    String id = data[0].trim();
                    int deadline = Integer.parseInt(data[1].trim());
                    int profit = Integer.parseInt(data[2].trim());
                    list.add(new YouTubeVideo(id, deadline, profit));
                }
            }
            fileScanner.close();
            System.out.println("File read successfully! Loaded " + list.size() + " videos.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Cannot find file '" + filePath + "'.");
        } catch (Exception e) {
            System.out.println("Error: Data format issue while reading the file.");
        }
        return list;
    }

    // Helper Method: Random data generation
    public static List<YouTubeVideo> generateRandomData(int count) {
        List<YouTubeVideo> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= count; i++) {
            String id = "Video_" + i;
            int deadline = random.nextInt(5) + 1; // 1 to 5 days
            int expectedViews = random.nextInt(9001) + 1000; // 1000 to 10000 views
            list.add(new YouTubeVideo(id, deadline, expectedViews));
        }
        System.out.println("Successfully generated " + count + " random video tasks.");
        return list;
    }
}