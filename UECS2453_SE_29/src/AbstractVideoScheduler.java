import java.util.List;

public abstract class AbstractVideoScheduler implements JobSequencingStrategy {
    
    @Override
    public abstract List<YouTubeVideo> schedule(List<YouTubeVideo> allVideos);

    @Override
    public int calculateTotalProfit(List<YouTubeVideo> selectedVideos) {
        int total = 0;
        for (YouTubeVideo video : selectedVideos) {
            total += video.getExpectedViews();
        }
        return total;
    }
}