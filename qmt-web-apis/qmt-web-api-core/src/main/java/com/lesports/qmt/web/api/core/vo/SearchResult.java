package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;
import java.util.List;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/10
 */
public class SearchResult implements Serializable {
    List<HallEpisodeVo> lives;
    List<SearchResultItem> topics;
    List<SearchResultItem> news;
    Integer count;
    Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<HallEpisodeVo> getLives() {
        return lives;
    }

    public void setLives(List<HallEpisodeVo> lives) {
        this.lives = lives;
    }

    public List<SearchResultItem> getTopics() {
        return topics;
    }

    public void setTopics(List<SearchResultItem> topics) {
        this.topics = topics;
    }

    public List<SearchResultItem> getNews() {
        return news;
    }

    public void setNews(List<SearchResultItem> news) {
        this.news = news;
    }
}
