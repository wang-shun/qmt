package com.lesports.qmt.config.vo;

import com.lesports.qmt.config.model.Copyright;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zhangxudong@le.com on 2016/11/7.
 */
public class CopyrightVo extends Copyright implements QmtVo {

    private Set<IdNameItemVo> clientPlatform;
    private Set<IdNameItemVo> vodPlatform;

    public Set<IdNameItemVo> getClientPlatform() {
        return clientPlatform;
    }

    public void setClientPlatform(Set<IdNameItemVo> clientPlatform) {
        this.clientPlatform = clientPlatform;
    }

    public Set<IdNameItemVo> getVodPlatform() {
        return vodPlatform;
    }

    public void setVodPlatform(Set<IdNameItemVo> vodPlatform) {
        this.vodPlatform = vodPlatform;
    }

    public CopyrightVo() {
    }

    public CopyrightVo(Copyright copyright) {
        try {
            LeBeanUtils.copyProperties(this, copyright);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public static List<CopyrightVo> toCopyrightVoList(List<Copyright> copyrights) {
        if (true == CollectionUtils.isEmpty(copyrights)) return null;
        List<CopyrightVo> result = new ArrayList<>();
        for (Copyright copyright : copyrights) {
            CopyrightVo copyrightVo = new CopyrightVo(copyright);
            copyrightVo.setClientPlatform(IdNameItemVo.convertClientPlatform(copyright.getClientPlatformId()));
            copyrightVo.setVodPlatform(IdNameItemVo.convertVodPlatform(copyright.getVodPlatformId()));
            result.add(copyrightVo);
        }
        return result;
    }


    public Copyright toCopyright() {
        //直接用类型转换得到的对象会报序列化错误
        Copyright copyright = new Copyright();
        try {
            LeBeanUtils.copyProperties(copyright, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return copyright;
    }
}
