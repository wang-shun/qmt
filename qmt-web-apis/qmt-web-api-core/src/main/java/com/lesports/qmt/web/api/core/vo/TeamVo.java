package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;

/**
 * Created by ruiyuansheng on 2016/11/21.
 */
public class TeamVo implements Serializable {

    private static final long serialVersionUID = -4152680468984026908L;
    //球队id
    private Long id;
    //球队名称
    private String name;
    //球队当前赛事id
    private Long cid;
    //球队当前赛季id
    private Long csid;
    //球队当前赛季名称
    private String csName;
    //球队logo
    private String logo;
    //球队排名
    private Rank rank;
    //头号射手
    private Player goaler;
    //头号助攻
    private Player assistant;

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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getCsid() {
        return csid;
    }

    public void setCsid(Long csid) {
        this.csid = csid;
    }

    public String getCsName() {
        return csName;
    }

    public void setCsName(String csName) {
        this.csName = csName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Player getGoaler() {
        return goaler;
    }

    public void setGoaler(Player goaler) {
        this.goaler = goaler;
    }

    public Player getAssistant() {
        return assistant;
    }

    public void setAssistant(Player assistant) {
        this.assistant = assistant;
    }

    public static class Rank  implements Serializable{
        private static final long serialVersionUID = 6075583444281864519L;
        //排名
        private Integer rank;
        //胜
        private Integer win;
        //平
        private Integer flat;
        //负
        private Integer loss;

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public Integer getWin() {
            return win;
        }

        public void setWin(Integer win) {
            this.win = win;
        }

        public Integer getFlat() {
            return flat;
        }

        public void setFlat(Integer flat) {
            this.flat = flat;
        }

        public Integer getLoss() {
            return loss;
        }

        public void setLoss(Integer loss) {
            this.loss = loss;
        }

        @Override
        public String toString() {
            return "Rank{" +
                    "rank=" + rank +
                    ", win=" + win +
                    ", flat=" + flat +
                    ", loss=" + loss +
                    '}';
        }
    }

    public static class Player implements Serializable{
        private static final long serialVersionUID = -1354060697598961372L;
        //球员id
        private Long pid;
        //球员名称
        private String name;
        //进球或者助攻数量
        private Integer number;
        //球员id
        private String logo;

        public Long getPid() {
            return pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "pid=" + pid +
                    ", name='" + name + '\'' +
                    ", number=" + number +
                    ", logo=" + logo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TeamVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cid=" + cid +
                ", csid=" + csid +
                ", csName='" + csName + '\'' +
                ", logo='" + logo + '\'' +
                ", rank=" + rank +
                ", goaler=" + goaler +
                ", assistant=" + assistant +
                '}';
    }
}
