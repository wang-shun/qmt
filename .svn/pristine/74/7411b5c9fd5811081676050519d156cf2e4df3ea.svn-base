package com.lesports.qmt.config.vo;

import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/11/7.
 */
public class CountryVo extends Country implements QmtVo {

    public CountryVo() {
    }

    public CountryVo(Country country) {
        try {
            LeBeanUtils.copyProperties(this, country);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public List<CountryVo> toCountryVoList(List<Country> countries) {
        if (true == CollectionUtils.isEmpty(countries)) return null;
        List<CountryVo> result = new ArrayList<>();
        for (Country country : countries) {
            result.add(new CountryVo(country));
        }
        return result;
    }

    public Country toCountry() {
        //直接用类型转换得到的对象会报序列化错误
        Country country = new Country();
        try {
            LeBeanUtils.copyProperties(country, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return country;
    }
}
