package com.lesports.qmt.web.api.core.vo;

/**
 * 比赛实况
 * Created by yangyu on 2015/5/30.
 */
public class MatchAction {

    private Long id;
    //比赛id
    private Long mid;
    //队伍id
    private Long tid;
    //队员id
    private Long pid;
    //事件发生时比赛进行了多长时间,精确到秒
    private Double passedTime;
    //字典表id，行为类型,如射门,点球,得分等
    private String type;
    //用json字符串描述的行为
    private Object content;
    //球队名称
    private String teamName;
    //球队logo
    private String teamImageUrl;
    //球员名称
    private String playerName;
    //球员头像
    private String playerImageUrl;
    //typeId
    private Long typeId;

    public static class SimplerPlayer {
        //球员名称
        private String playerName;
        //球员头像
        private String playerImageUrl;

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public String getPlayerImageUrl() {
            return playerImageUrl;
        }

        public void setPlayerImageUrl(String playerImageUrl) {
            this.playerImageUrl = playerImageUrl;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("SimplerPlayer{");
            sb.append("playerName='").append(playerName).append('\'');
            sb.append(", playerImageUrl='").append(playerImageUrl).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Double getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(Double passedTime) {
        this.passedTime = passedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamImageUrl() {
        return teamImageUrl;
    }

    public void setTeamImageUrl(String teamImageUrl) {
        this.teamImageUrl = teamImageUrl;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerImageUrl() {
        return playerImageUrl;
    }

    public void setPlayerImageUrl(String playerImageUrl) {
        this.playerImageUrl = playerImageUrl;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MatchAction{");
        sb.append("id=").append(id);
        sb.append(", mid=").append(mid);
        sb.append(", tid=").append(tid);
        sb.append(", pid=").append(pid);
        sb.append(", passedTime=").append(passedTime);
        sb.append(", type='").append(type).append('\'');
        sb.append(", content=").append(content);
        sb.append(", teamName='").append(teamName).append('\'');
        sb.append(", teamImageUrl='").append(teamImageUrl).append('\'');
        sb.append(", playerName='").append(playerName).append('\'');
        sb.append(", playerImageUrl='").append(playerImageUrl).append('\'');
        sb.append(", typeId=").append(typeId);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchAction)) return false;

        MatchAction that = (MatchAction) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
