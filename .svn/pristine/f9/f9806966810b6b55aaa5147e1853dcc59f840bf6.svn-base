/**************************************
 * cms_service.thrift
 * Lesports Quan Mei Ti Cms Service
 *************************************/
include "api_common.thrift"
include "cms_common.thrift"

include "param.thrift"

include "layout.thrift"
include "widget.thrift"
include "column.thrift"
include "page.thrift"

namespace java com.lesports.qmt.cms.api.service

/**
 * Lesports Sports CMS Service Definition.
 * @author ellios
 */
service TCmsService{

    #################### layout ##################################

    layout.TLayout getTLayoutById(1: i64 id),

    layout.TLayout getTLayoutByPath(1: string path),

    #################### widget ##################################

    widget.TWidget getTWidgetById(1: i64 id),

    widget.TWidget getTWidgetByPath(1: string path),

    #################### column ##################################

    column.TColumn getTColumnById(1: i64 id),

    column.TColumn getTColumnByFullPath(1: string path),

    #################### column page ##################################
    page.TColumnPage getTColumnPageById(1: i64 id),
}




