/*************************
 * transcode_internal_service.thrift
 * Lesports QMT
 *************************************/
namespace java com.lesports.qmt.transcode.service
include "api_common.thrift"
/**
* Lesports  QuanMeiTi Definition.
* @author pangchuanxiao
*/

//转码服务
service TTranscodeInternalService {
        ####################### crud ################################
        i64 saveEntity(1: binary bf, 2: binary classType);

        bool deleteEntity(1: i64 id, 2: binary classType);

        binary getEntityBytesById(1: i64 id, 2: binary classType);

        binary getEntityBytesByIds(1: list<i64> ids, 2: binary classType);

        binary getEntitiesBytesByQuery(1: binary query, 2: binary classType);

        list<i64> getEntityIdsByQuery(1: binary query, 2: binary classType);

        i64 countByQuery(1: binary query, 2: binary classType);



        #####################################################################################
        /**
        * 检查转码状态
        **/
        api_common.Alarm checkTranscodeStatus();
}