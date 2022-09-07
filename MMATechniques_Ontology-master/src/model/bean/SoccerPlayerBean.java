package model.bean;

import java.util.ArrayList;

public class SoccerPlayerBean {

    private String uri;
    private String name;
    private FootBallTeamBean footballTeamBean;
    private int overall;
    private String position;
    private String thumbnail;
    private String label_BallonDOr;
    private String comment_BallonDOr;
    private ArrayList<SkillBean> skills;
    private String comment;

    @Override
    public String toString() {
        return "SoccerPlayerBean{" +
                "uri='" + uri + '\'' +
                ", name='" + name + '\'' +
                ", fottballTeamBean=" + footballTeamBean +
                ", overall=" + overall +
                ", position='" + position + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", label_BallonDOr='" + label_BallonDOr + '\'' +
                ", comment_BallonDOr='" + comment_BallonDOr + '\'' +
                ", skills=" + skills +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getLabel_BallonDOr() {
        return label_BallonDOr;
    }

    public void setLabel_BallonDOr(String label_BallonDOr) {
        this.label_BallonDOr = label_BallonDOr;
    }

    public String getComment_BallonDOr() {
        return comment_BallonDOr;
    }

    public void setComment_BallonDOr(String comment_BallonDOr) {
        this.comment_BallonDOr = comment_BallonDOr;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        String[] splittedPos = position.split(",");
        for (int i = 0; i < splittedPos.length; i++) {
            if (i == 0) {
                this.position = splittedPos[i];
            } else if (!this.position.contains(splittedPos[i])) {
                    this.position += ", " + splittedPos[i];
            }
        }
        this.position = this.position.replaceAll("\\(calcio\\)", "");
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FootBallTeamBean getFootballTeamBean() {
        return footballTeamBean;
    }

    public void setFootballTeamBean(FootBallTeamBean footballTeamBean) {
        this.footballTeamBean = footballTeamBean;
    }

    public int getOverall() {
        return overall;
    }

    public void setOverall(int overall) {
        this.overall = overall;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<SkillBean> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<SkillBean> skills) {
        this.skills = skills;
    }
}
