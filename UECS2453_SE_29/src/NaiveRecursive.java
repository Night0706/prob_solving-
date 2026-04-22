import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NaiveRecursive extends AbstractVideoScheduler {

    @Override
    public List<YouTubeVideo> schedule(List<YouTubeVideo> allVideos) {
        List<YouTubeVideo> sortedVideos = new ArrayList<>(allVideos);
        sortedVideos.sort(Comparator.comparingInt(YouTubeVideo::getDeadline));
        
        return findMaxProfit(sortedVideos, 0, new ArrayList<>(), 0);
    }

    private List<YouTubeVideo> findMaxProfit(List<YouTubeVideo> videos, int index, List<YouTubeVideo> currentSelection, int currentDay) {
        if (index == videos.size()) {
            return new ArrayList<>(currentSelection);
        }

        YouTubeVideo currentVideo = videos.get(index);
        
        List<YouTubeVideo> excludeResult = findMaxProfit(videos, index + 1, currentSelection, currentDay);
        List<YouTubeVideo> includeResult = new ArrayList<>(); 
        
        if (currentDay + 1 <= currentVideo.getDeadline()) {
            currentSelection.add(currentVideo);
            includeResult = findMaxProfit(videos, index + 1, currentSelection, currentDay + 1);
            currentSelection.remove(currentSelection.size() - 1); 
        }

        return calculateTotalProfit(includeResult) > calculateTotalProfit(excludeResult) ? includeResult : excludeResult;
    }
}