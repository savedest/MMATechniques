package model.bean;

import java.util.ArrayList;

public class MartialArtistBean {

    private String uri;
    private String name;

    private String team;

    private String teamUri;
    
    private String currWeightClassUri;

    private String currWeightClass;

    private String weight;

    private String height;
    private FootBallTeamBean footballTeamBean;
    private String thumbnail;
    private String label_BallonDOr;
    private String comment_BallonDOr;
    private String thumbnailTheUltimateFighter;
    private ArrayList<KickboxingTechniqueBean> skills;
    private String comment;

    @Override
    public String toString() {
        return "MartialArtistBean{" +
                "uri='" + uri + '\'' +
                ", name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", teamUri='" + teamUri + '\'' +
                ", currWeightClass='" + currWeightClass + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", footballTeamBean=" + footballTeamBean +
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

    public String getCurrWeightClass() {
        return currWeightClass;
    }

    public void setCurrWeightClass(String currWeightClass) {
        this.currWeightClass = currWeightClass;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeamUri() {
        return teamUri;
    }

    public void setTeamUri(String teamUri) {
        this.teamUri = teamUri;
    }

    public String getCurrWeightClassUri() {
		return currWeightClassUri;
	}

	public void setCurrWeightClassUri(String currWeightClassUri) {
		this.currWeightClassUri = currWeightClassUri;
	}

	public FootBallTeamBean getFootballTeamBean() {
        return footballTeamBean;
    }

    public void setFootballTeamBean(FootBallTeamBean footballTeamBean) {
        this.footballTeamBean = footballTeamBean;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<KickboxingTechniqueBean> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<KickboxingTechniqueBean> skills) {
        this.skills = skills;
    }

	public String getThumbnailTheUltimateFighter() {
		return thumbnailTheUltimateFighter;
	}

	public void setThumbnailTheUltimateFighter(String thumbnailTheUltimateFighter) {
		this.thumbnailTheUltimateFighter = thumbnailTheUltimateFighter;
	}
    
    
}
