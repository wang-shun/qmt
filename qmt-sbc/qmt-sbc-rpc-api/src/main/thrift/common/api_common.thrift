/**************************************
 * rpc_common.thrift
 * Lesports Param
 *************************************/
namespace java com.lesports.api.common


//国家code
enum CountryCode{
    ALL = 0,
    //内地
    CN = 1,
    //香港
    HK = 2,
    //美国
    US = 3,
}

//语言code
enum LanguageCode{
    //简体中文
    ZH_CN = 0,
    //香港繁体中文
    ZH_HK = 1,
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
}

//带语言表示的字符串
struct LangString{
    1: optional LanguageCode language = LanguageCode.ZH_CN,
    2: optional string value,
}

//地区+语言决定的实体Id
struct CountryLangId{
    1: optional CountryCode country = CountryCode.CN,
    2: optional LanguageCode language = LanguageCode.ZH_CN,
    3: optional i64 id,
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

//播放平台
enum Platform{
    //PC
    PC = 0,
    //移动APP
    MOBILE = 1,
    //TV
    TV = 2,
    //pad
    PAD = 3,
    //msite
    MSITE = 4
}