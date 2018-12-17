package com.lesports.qmt.userinfo.model;

import com.google.common.collect.Lists;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 用户工作台
 * Created by denghui on 2017/2/14.
 */
@Document(collection = "workbenches")
public class Workbench extends QmtModel<Long> {
    private static final long serialVersionUID = 5091629923212613945L;

    //订阅的板块列表
    @Field("resource_ids")
    private List<Long> resourceIds = Lists.newArrayList();

    public List<Long> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public void addResourceId(Long id) {
        if (LeNumberUtils.toLong(id) <= 0 || resourceIds.contains(id)) {
            return;
        }
        resourceIds.add(0, id);
    }
}
