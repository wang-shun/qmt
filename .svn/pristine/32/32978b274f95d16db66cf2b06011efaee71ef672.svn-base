/**************************************
 * sms_service.thrift
 * Lesports Sms Service
 *************************************/
include "api_common.thrift"
include "sbc_common.thrift"

include "param.thrift"

namespace java com.lesports.qmt.sbc.api.service

struct SaveResult {
    1: optional i64 id;
    2: optional string msg;
    3: optional bool success;
}

/**
 * Lesports Sports Basic Content Internal Service Definition.
 * @author ellios
 */
service TSbcInternalService{

    /**
    *  保存业务实体,返回实体ID,允许控制属性是否置空
    **/
    SaveResult saveEntityWithResult(1: binary bf, 2: binary classType, 3: bool allowEmpty);

    /**
    *  保存业务实体,返回实体ID,允许控制属性是否置空
    **/
    i64 saveEntity(1: binary bf, 2: binary classType, 3: bool allowEmpty);

    /**
    * 删除业务实体
    **/
    bool deleteEntity(1: i64 id, 2: binary classType);

    /**
     * 定时任务,更新直播的状态
    **/
    bool schedulerUpdateLiveStatus();

    /**
     * 克隆视频到专辑
    **/
    bool cloneVideoToAlbum(1: i64 videoId, 2: list<i64> albumIds);

    /**
    * 根据实体ID获取业务实体
    **/
    binary getEntityBytesById(1: i64 id, 2: binary classType);

    binary getEntityBytesById2(1: string id, 2: binary classType);

    /**
    * 根据实体ID批量获取业务实体
    **/
    binary getEntityBytesByIds(1: list<i64> ids, 2: binary classType);

    /**
    * 根据InternalQuery查询条件,查询符合条件的实体ID
    **/
    list<i64> getEntityIdsByQuery(1: binary query, 2: binary classType);
    /**
        * 根据InternalQuery查询条件,查询符合条件的实体
    **/
    binary getEntitiesBytesByQuery(1: binary query, 2: binary classType);

    /**
    * 根据InternalQuery查询条件,查询符合条件的实体数量
    **/
    i64 countByQuery(1: binary query, 2: binary classType);

    /**
    * 根据mid创建节目
    **/
    list<i64> createEpisodes(1: i64 mid);

    /**
    *  改变指定类型数据，指定id的序号
    **/
    bool changeOrder(1:i64 id,2:i32 increment,3:binary classType);

}