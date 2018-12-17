/**************************************
 * rpc_common.thrift
 * Lesports Param
 *************************************/
namespace java com.lesports.api.common

//乐词
struct Leci{
    1: i64 id,
    2: string name,
}

//国家code
enum CountryCode{
    ALL = 0,
    //内地
    CN = 1,
    //香港
    HK = 2,
    //美国
    US = 3,
    //台湾
    TW = 4,
    //印度
    IN = 5,
}

//语言code
enum LanguageCode{
    //简体中文
    ZH_CN = 0,
    //香港繁体中文
    ZH_HK = 1,
    //英文
    EN_US = 2,
    //台湾繁体中文
    ZH_TW = 3,
    //香港英文
    EN_HK = 4,
}

//渠道
enum PubChannel{
    //乐视体育官方
    LETV = 10001,
    //TCL
    TCL = 10002,
    //华数
    WASU = 10003,
}

//排序的方向
enum Direction{
    //升序
    ASC,
    //降序
    DESC,
}

struct Sort{
    1: list<Order> orders,
}

struct Order{
    1: required string field,
    2: required Direction direction = Direction.DESC,
}

//分页请求
struct PageParam{
    //页数
    1: optional i32 page,
    //请求个数
    2: optional i32 count,
    //排序的字段
    3: optional Sort sort,
}

//请求者参数信息
struct CallerParam{
    1: required i64 callerId,
    2: optional CountryCode country,
    3: optional LanguageCode language,
    4: optional PubChannel pubChannel,
}

//带语言表示的字符串
struct LangString{
    1: optional LanguageCode language = LanguageCode.ZH_CN,
    2: optional string value,
}

//带地区表示的字符串
struct CounString{
    1: optional CountryCode country = CountryCode.CN,
    2: optional string value,
}

//地区+语言决定的实体Id
struct CountryLangId{
    1: optional CountryCode country = CountryCode.CN,
    2: optional LanguageCode language = LanguageCode.ZH_CN,
    3: optional i64 id,
}

//报警状态
enum AlarmStatus {
    //正常
    NORMAL = 200,
    //节目状态异常
    EPISODE_STATUS_EXCEPTION = 400,
}

//报警检查结果
struct Alarm {
    //报警服务名
    1: optional string serviceName,
    //报警主题
    2: optional string subject,
    //报警接口
    3: optional string api,
    //报警内容
    4: optional string content,
    //报警状态
    5: optional AlarmStatus status,
    //附加信息: IP,端口...
    6: optional map<string,string> extra,
}