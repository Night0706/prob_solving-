import java.util.List;

public interface JobSequencingStrategy {
    List<YouTubeVideo> schedule(List<YouTubeVideo> allVideos);
    int calculateTotalProfit(List<YouTubeVideo> selectedVideos);
}