/**************************************
 * sbd_internal_service.thrift
 * Lesports Qmt Service
 *************************************/
include "api_common.thrift"
include "sbd_common.thrift"

include "param.thrift"

namespace java com.lesports.qmt.sbd.api.service

/**
 * Lesports Sports Basic Data Internal Service Definition.
 * @author lufei
 */
service TSbdInternalService{

   /**
   *  保存业务实体,返回实体ID,允许控制属性是否置空
   **/
   i64 saveEntity(1: binary bf, 2: binary classType, 3: bool allowEmpty);

    /**
    * 删除业务实体
    **/
    bool deleteEntity(1: i64 id, 2: binary classType);

    /**
    * 根据实体ID获取业务实体
    **/
    binary getEntityBytesById(1: i64 id, 2: binary classType);

    binary getEntityBytesById2(1: string id, 2: binary classType);

    /**
    * 根据实体ID批量获取业务实体
    **/
    binary getEntityBytesByIds(1: list<i64> ids, 2: binary classType);

    binary getEntitiesBytesByQuery(1: binary query, 2: binary classType);

    /**
    * 根据InternalQuery查询条件,查询符合条件的实体ID
    **/
    list<i64> getEntityIdsByQuery(1: binary query, 2: binary classType);

    /**
    * 根据InternalQuery查询条件,查询符合条件的实体数量
    **/
    i64 countByQuery(1: binary query, 2: binary classType);


    ####################### toplist ################################
    /**
     * 删除榜单
    **/
    void deleteTopListItem(1:i64 id, 2:binary topListItem);

    ####################### toplist ################################

    ####################### match ################################
    /**
     * 更新match
    **/
    void doUpdateMatch(1:binary match);

    ####################### match ################################
}