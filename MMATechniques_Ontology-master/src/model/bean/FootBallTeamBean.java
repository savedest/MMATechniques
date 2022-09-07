package model.bean;

public class FootBallTeamBean {
    private String uri;
    private String name;
    private String thumbnail;
    private int avg_overall;
    private int min_overall;
    private int max_overall;

    @Override
    public String toString() {
        return "FootBallTeamBean{" +
                "uri='" + uri + '\'' +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", avg_overall=" + avg_overall +
                ", min_overall=" + min_overall +
                ", max_overall=" + max_overall +
                '}';
    }

    public int getAvg_overall() {
        return avg_overall;
    }

    public void setAvg_overall(int avg_overall) {
        this.avg_overall = avg_overall;
    }

    public int getMin_overall() {
        return min_overall;
    }

    public void setMin_overall(int min_overall) {
        this.min_overall = min_overall;
    }

    public int getMax_overall() {
        return max_overall;
    }

    public void setMax_overall(int max_overall) {
        this.max_overall = max_overall;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
