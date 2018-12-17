package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2016/11/25.
 */
public class PlayerVo implements Serializable {

    private static final long serialVersionUID = -3204888774232451423L;

    private Long id;
    private String name;
    private String position;
    private String nationality;
    private String birth;
    private Integer height;
    private Team  club;
    private Team  country;
    private String logo;
    private Boolean isGoalKeeper = false;
    private Integer number;

    public static class Team{

        private Long tid;
        private String name;
        private Map<String,String> stats;
        private String logo;
        private Integer number;

        public Long getTid() {
            return tid;
        }

        public void setTid(Long tid) {
            this.tid = tid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getStats() {
            return stats;
        }

        public void setStats(Map<String, String> stats) {
            this.stats = stats;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "Team{" +
                    "tid=" + tid +
                    ", name='" + name + '\'' +
                    ", stats=" + stats +
                    ", logo='" + logo + '\'' +
                    ", number=" + number +
                    '}';
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Team getClub() {
        return club;
    }

    public void setClub(Team club) {
        this.club = club;
    }

    public Team getCountry() {
        return country;
    }

    public void setCountry(Team country) {
        this.country = country;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getIsGoalKeeper() {
        return isGoalKeeper;
    }

    public void setIsGoalKeeper(Boolean isGoalKeeper) {
        this.isGoalKeeper = isGoalKeeper;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PlayerVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", nationality='" + nationality + '\'' +
                ", birth='" + birth + '\'' +
                ", height=" + height +
                ", club=" + club +
                ", country=" + country +
                ", logo='" + logo + '\'' +
                ", isGoalKeeper=" + isGoalKeeper +
                ", number=" + number +
                '}';
    }
}
