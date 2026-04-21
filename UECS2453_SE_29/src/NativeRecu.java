import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NativeRecu extends AbstractVideoScheduler {

    @Override
    public List<YouTubeVideo> schedule(List<YouTubeVideo> allVideos) {
        // Create a copy to avoid modifying the original list
        List<YouTubeVideo> sortedVideos = new ArrayList<>(allVideos);
        
        // Step 1: Sort videos by deadline in ascending order.
        // This is crucial for the recursive logic to evaluate time slots chronologically.
        Collections.sort(sortedVideos, new Comparator<YouTubeVideo>() {
            @Override
            public int compare(YouTubeVideo v1, YouTubeVideo v2) {
                return Integer.compare(v1.getDeadline(), v2.getDeadline());
            }
        });

        // Step 2: Start the recursive search from index 0 and day 0
        return findMaxProfit(sortedVideos, 0, new ArrayList<>(), 0);
    }

    // The helper recursive method
    private List<YouTubeVideo> findMaxProfit(List<YouTubeVideo> videos, int index, List<YouTubeVideo> currentSelection, int currentDay) {
        // Base case: If we have evaluated all videos, return the current selection
        if (index == videos.size()) {
            return new ArrayList<>(currentSelection);
        }

        YouTubeVideo currentVideo = videos.get(index);

        // Branch 1: Exclude the current video
        List<YouTubeVideo> excludeResult = findMaxProfit(videos, index + 1, currentSelection, currentDay);

        // Branch 2: Include the current video (only if it meets the deadline)
        List<YouTubeVideo> includeResult = new ArrayList<>(); // Empty list by default
        
        // Check if we have enough time to finish this video before its deadline (assuming 1 video = 1 day)
        if (currentDay + 1 <= currentVideo.getDeadline()) {
            // Add to our current selection
            currentSelection.add(currentVideo);
            
            // Recurse to the next video, incrementing the day counter
            includeResult = findMaxProfit(videos, index + 1, currentSelection, currentDay + 1);
            
            // Backtrack: Remove the video so we don't mess up other recursive branches
            currentSelection.remove(currentSelection.size() - 1); 
        }

        // Compare which branch yielded a higher total profit (expected views)
        if (calculateTotalProfit(includeResult) > calculateTotalProfit(excludeResult)) {
            return includeResult;
        } else {
            return excludeResult;
        }
    }
}