package com.lesports.qmt.web.api.core.rconverter;

import com.lesports.qmt.resource.cvo.*;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;

/**
 * Created by gengchengliang on 2016/12/29.
 */
public interface BaseResourceContentConverter<T, E extends BaseCvo> {

	public T getVo();

	public T fillVo(E e, Object... args);

	public default void adapterH5(T t, H5Cvo h5CCvo){};

	public default void adapterTopic(T t, SubjectCvo subjectCvo){};

	public default void adapterNews(T t, NewsCvo newsCvo){};

	public default void adapterPost(T t, PostCvo postCvo){};

	public default void adapterVoType(T t, ResourceItemType rType, Object... args){};

	public default void adapterNewsType(T t, BaseCvo content){};

	public default void adapterEpisode(T t, EpisodeCvo content, Object... args){};
}
