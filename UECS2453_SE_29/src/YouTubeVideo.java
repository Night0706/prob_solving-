public class YouTubeVideo {
    private String videoId;
    private int deadline;
    private int expectedViews;

    public YouTubeVideo(String videoId, int deadline, int expectedViews) {
        this.videoId = videoId;
        this.deadline = deadline;
        this.expectedViews = expectedViews;
    }

    public String getVideoId() { return videoId; }
    public int getDeadline() { return deadline; }
    public int getExpectedViews() { return expectedViews; }

    @Override
    public String toString() {
        return String.format("VideoID: %-12s | Deadline: %2d days | Profit(Views): %d", 
                             videoId, deadline, expectedViews);
    }
}