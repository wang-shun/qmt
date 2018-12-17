package com.lesports.qmt.tlive.model;

import com.google.common.collect.Sets;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by lufei1 on 2015/9/13.
 */
@Document(collection = "votes")
public class Vote extends QmtModel<Long> {
    private static final long serialVersionUID = 4095336372236203051L;

    //主题
    private String topic;
    //选项
    private Set<Option> options = Sets.newHashSet();
    //是否被删除
    private Boolean deleted;
	//前台传值
	@Transient
	private List<Option> optionList;

    public static class Option implements Serializable {
        private static final long serialVersionUID = -6436473770776665543L;

        //选项id
        @Field("option_id")
        private Long optionId;
        //选项内容
        private String title;
        //投票数
        private Integer num = 0;
		//排序
		private Integer order;

		public Integer getOrder() {
			return order;
		}

		public void setOrder(Integer order) {
			this.order = order;
		}

		public Long getOptionId() {
            return optionId;
        }

        public void setOptionId(Long optionId) {
            this.optionId = optionId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("Option{");
			sb.append("optionId=").append(optionId);
			sb.append(", title='").append(title).append('\'');
			sb.append(", num=").append(num);
			sb.append(", order=").append(order);
			sb.append('}');
			return sb.toString();
		}
	}

	public List<Option> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", options=" + options +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
