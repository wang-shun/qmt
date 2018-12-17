package com.lesports.qmt.sbd.model;

import com.google.common.collect.Lists;
import com.lesports.api.common.CountryCode;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * 队伍的赛季情况
 * User: ellios
 * Time: 15-6-10 : 下午9:07
 */
@Document(collection = "team_seasons")
public class TeamSeason extends QmtModel<Long> {

    private static final long serialVersionUID = -5762287045999316288L;


    //队伍id
    private Long tid;
    //球队教练id
    private Long coachId;
    //赛季id
    private Long csid;
    //队员信息
    private List<TeamPlayer> players;
    //现任队长
    @Field("current_captain")
    private Long currentCaptain;
    //核心球员
    @Field("core_players")
    private List<Long> corePlayers;
    //允许展示的国家
    @Field("allow_countries")
    private List<CountryCode> allowCountries;
    //是否被删除
    private Boolean deleted = false;

    public List<TeamPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<TeamPlayer> players) {
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getCsid() {
        return csid;
    }

    public void setCsid(Long csid) {
        this.csid = csid;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }


    public List<CountryCode> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<CountryCode> allowCountries) {
        this.allowCountries = allowCountries;
    }


    public Long getCurrentCaptain() {
        return currentCaptain;
    }

    public void setCurrentCaptain(Long currentCaptain) {
        this.currentCaptain = currentCaptain;
    }

    public List<Long> getCorePlayers() {
        return corePlayers;
    }

    public void setCorePlayers(List<Long> corePlayers) {
        this.corePlayers = corePlayers;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


    public void addPlayer(TeamPlayer player) {
        if (player == null) {
            return;
        }
        // $addToSet: add when not exists
        if (this.players == null) {
            this.players = Lists.newArrayList();
            this.players.add(player);
        } else if (!this.players.contains(player)) {
            this.players.add(player);
        }
    }

    public void deletePlayer(TeamPlayer player) {
        if (this.players == null || player == null) {
            return;
        }
        this.players.remove(player);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamSeason{");
        sb.append("id=").append(id);
        sb.append(", tid=").append(tid);
        sb.append(", coachId=").append(coachId);
        sb.append(", csid=").append(csid);
        sb.append(", players=").append(players);
        sb.append(", allowCountries=").append(allowCountries);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamSeason)) return false;

        TeamSeason that = (TeamSeason) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * 嵌入到队伍里地简单队员信息
     */
    public static class TeamPlayer implements Serializable {

        private static final long serialVersionUID = -5762287045999316287L;

        //球员id
        private Long pid;

        //号码
        private String number;
        public Long getPid() {
            return pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TeamPlayer)) return false;

            TeamPlayer that = (TeamPlayer) o;

            if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return pid != null ? pid.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "TeamPlayer{" +
                    "pid=" + pid +
                    ", number='" + number + '\'' +
                    '}';
        }
    }


}
