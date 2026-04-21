import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EDFScheduler extends AbstractVideoScheduler {

    @Override
    public List<YouTubeVideo> schedule(List<YouTubeVideo> allVideos) {
        List<YouTubeVideo> sortedVideos = new ArrayList<>(allVideos);
        // 按 deadline 升序排序
        Collections.sort(sortedVideos, new Comparator<YouTubeVideo>() {
            @Override
            public int compare(YouTubeVideo v1, YouTubeVideo v2) {
                return Integer.compare(v1.getDeadline(), v2.getDeadline());
            }
        });

        List<YouTubeVideo> selectedVideos = new ArrayList<>();
        int currentDay = 0;

        for (YouTubeVideo video : sortedVideos) {
            if (currentDay + 1 <= video.getDeadline()) {
                selectedVideos.add(video);
                currentDay++;
            }
        }
        return selectedVideos;
    }
}