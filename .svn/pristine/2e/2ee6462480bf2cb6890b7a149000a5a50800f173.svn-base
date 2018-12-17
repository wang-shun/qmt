package com.lesports.qmt.web.datacenter.dataprocess;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.web.datacenter.vo.ContentBaseVo;
import com.lesports.qmt.web.datacenter.vo.H5Vo;
import com.lesports.qmt.web.datacenter.vo.PostVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TBase;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wangjichuan  date:16-12-20 time:17:35
 */
public interface DataProcess<T,E extends ContentBaseVo> {

    public default List<E> getEntitys(){
        return new ArrayList<>();
    };

    public E getEntity();

    public default Class getVoClass(){
        return getEntity().getClass();
    };

    public default void fillVoByDto(E e,T t){};

    public default void fillVosByDtos(List<E> eList,List<T> tList){
        E e = getEntity();
        for(T t : tList){
            fillVoByDto(e,t);
            eList.add(e);
        }
    }

    public default void fillVosType(List<E> eList,ResourceItemType resourceItemType){
        for(E e : eList){
           e.setType(resourceItemType);
        }
    }


    public default void constructManualRemoteData(E e,String itemId,CallerParam callerParam){

    };

    public default List<E> constructAutoRemoteData(TResourceContent tResourceContent,PageParam pageParam,CallerParam callerParam){
        return null;
    };

    /**
     *
     * @param tResourceContent
     * @param callerParam
     * @param constructType  组装数据的类型
     * @return
     */
     public default E constructManualData(TResourceContent tResourceContent,CallerParam callerParam,ConstructType constructType){
         E e = getEntity();
         String itemId = tResourceContent.getItemId();
         if(getVoClass() == H5Vo.class){
             itemId = tResourceContent.getH5Url();
         }
         e.setType(tResourceContent.getType());
         if(StringUtils.isNotBlank(itemId)){
             if(constructType != ConstructType.NO_RPC){
                 constructManualRemoteData(e,itemId,callerParam);
             }
         }
         if(constructType != ConstructType.ONLY_RPC){
             constructCommonData(e,tResourceContent);
         }
         return e;
     };

    /**
     *
     * @param tResourceContent
     * @param callerParam
     * @return
     */
    public default List<E> constructAutoData(TResourceContent tResourceContent,PageParam pageParam,CallerParam callerParam){
        return constructAutoRemoteData(tResourceContent,pageParam,callerParam);
    };


    /**
     * 通用数据，就是资源位内容本身的内容
     * @param e
     * @param tResourceContent
     */
    public default void constructCommonData(E e,TResourceContent tResourceContent){
        e.setName(tResourceContent.getName());
        e.setDurationTime(tResourceContent.getDurationTime());
        e.setMobileImg(tResourceContent.getMobileImg());
        e.setIpadImg(tResourceContent.getIpadImg());
        e.setTvImg(tResourceContent.getTvImg());
        e.setOverdRound(tResourceContent.getOverdRound());
        e.setLivingRound(tResourceContent.getLivingRound());
        e.setToStartRound(tResourceContent.getToStartRound());
        e.setTagIds(tResourceContent.getTagIds());
        e.setOrder(tResourceContent.getOrder());
        e.setStartTime(tResourceContent.getStartTime());
        e.setEndTime(tResourceContent.getEndTime());
    }
    static enum ConstructType{
        ALL(1),NO_RPC(2),ONLY_RPC(3);
        private String desc;
        private int value;
        ConstructType(int value){
            this.value = value;
        }
        public ConstructType findByValue(int value){
            for(ConstructType constructType : ConstructType.values()){
                if(constructType.value == value){
                    return constructType;
                }
            }
            return null;
        }
    }
}
