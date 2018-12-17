package com.lesports.qmt.config.service;

import com.lesports.qmt.config.vo.CountryVo;
import com.lesports.qmt.mvc.QmtWebService;

import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/10/29.
 */
public interface CountryWebService extends QmtWebService<CountryVo, Long> {
//    QmtPage<CountryVo> getCountryVoPage(int pageNumber, int pageSize, String codes, String abbreviation, String chineseName);
    List<CountryVo> getCountryVoList(String codes, String abbreviation, String chineseName);
}