package com.lesports.qmt.api.resources;

import com.google.common.base.Preconditions;
import com.lesports.LeConstants;
import com.lesports.jersey.AlternateMediaType;
import com.lesports.jersey.annotation.LJSONP;
import com.lesports.jersey.core.LeStatus;
import com.lesports.jersey.exception.LeWebApplicationException;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.utils.LeProperties;
import com.lesports.utils.MD5Util;
import com.lesports.utils.SSOApi;
import com.lesports.utils.pojo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Random;

/**
 * Created by ruiyuansheng on 2016/8/2.
 */
@Path("/ssports")
public class SsportsResource {


    private static final Logger LOG = LoggerFactory.getLogger(SsportsResource.class);

    private static final String SECRET = LeProperties.getString("ssports.secret","9fc66976d4a44bed8f322dd86e0f9b82");
    private static final String SSPORTS_PC_URL = LeProperties.getString("ssports.pc.url","http://ssports.smgbb.cn/coop/p/leshi.html");
    private static final String SSPORTS_M_URL = LeProperties.getString("ssports.m.url","http://m.ssports.smgbb.cn/coop/p/leshi.html");
    private static final String SSPORTS_PC_URL2 = LeProperties.getString("ssports.pc.url2","http://ssports.smgbb.cn/coop/p/leshi.html");
    private static final String SSPORTS_M_URL2 = LeProperties.getString("ssports.m.url2","http://m.ssports.smgbb.cn/coop/p/leshi.html");

    @GET
    @LJSONP
    @Path("/vipUser/destUrl")
    @Produces({AlternateMediaType.UTF_8_APPLICATION_JSON})
    public String getSomedayCount( @QueryParam("ssoTk") String ssoTk,
                                   @QueryParam("userKey") String userKey,
                                   @QueryParam("ssportsMid") long ssportsMid,
                                   @QueryParam("day") int day,
                                   @QueryParam("bind") @DefaultValue("1") int bind,
                                   @QueryParam("isYearVip") int isYearVip,
                                   @QueryParam("from") @DefaultValue("lesports_hm") String from,
                                   @BeanParam CallerBean callerBean,
                                   ContainerRequestContext containerRequestContext) {
        try {

            String uid = "";
            if (callerBean.getCallerId() == LeConstants.LESPORTS_PC_CALLER_CODE || callerBean.getCallerId() == LeConstants.LESPORTS_MSITE_CALLER_CODE) {
                Preconditions.checkArgument(StringUtils.isNotEmpty(userKey), "用户不存在");
                uid = SSOApi.checkUserKey(userKey);
            }else {
                //根据用户tk获取uid
                UserVo userVo = SSOApi.getUserByToken(ssoTk);
                Preconditions.checkArgument(null != userVo, "用户不存在");
                uid = userVo.getUid();
            }
            Preconditions.checkArgument(StringUtils.isNotEmpty(uid), "用户不存在");

            //获取时间戳
            DateTime dateTime = new DateTime();
            long timeStamp = dateTime.getMillis();
            String afterSecret = "";
            String r =  getRandomString(16);
            if(1 == isYearVip) {
                //根据新英规则MD5加密后字符串
                 afterSecret = MD5Util.md5(SECRET + timeStamp + uid + r);
            }else{
                 afterSecret = MD5Util.md5(SECRET + timeStamp + uid + r + day);
            }

            //拼接跳转新英URL
            StringBuffer sb = new StringBuffer();

            if (callerBean.getCallerId() == LeConstants.LESPORTS_PC_CALLER_CODE) {//pc的新英付费地址
                if(1 == isYearVip) {
                    sb.append(SSPORTS_PC_URL);
                }else{
                    sb.append(SSPORTS_PC_URL2);
                }
            } else {//m站的新英付费地址
                if(1 == isYearVip) {
                    sb.append(SSPORTS_M_URL);
                }else{
                    sb.append(SSPORTS_M_URL2);
                }
            }
            sb.append("?t=").append(timeStamp).append("&lu=").append(uid)
                    .append("&k=").append(afterSecret).append("&b=").append(bind)
                    .append("&m=").append(ssportsMid).append("&s=").append(from).append("&r=").append(r);
            if(1 != isYearVip) {
                sb.append("&d=").append(day);
            }

            return sb.toString();

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


}
