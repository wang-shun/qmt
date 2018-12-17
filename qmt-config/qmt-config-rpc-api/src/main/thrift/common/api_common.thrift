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

//排序的方向
enum Direction{
    //升序
    ASC,
    //降序1
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
    MSITE = 4,
}

//发布状态,是否可用
enum PublishStatus{
    //下线
    OFFLINE = 0,
    //上线
    ONLINE = 1,
}

//复合标签
struct CompoundTag{
    //复合标签名称
    1: string name,
    //标签id列表,这里的标签id 可以是一般的标签id,或者是球队id,球员id,比赛id等,不能是赛事
    2: list<i64> tagIds,
}

//扩展的图片地址信息
struct ImageUrlExt{
    //索引
    1: i32 index,
    //图片在本地的存储路径
    2: optional string path,
    //图片url
    3: optional string url,
}

//频道
struct Channel{
    1: i64 id,
    2: string name
}