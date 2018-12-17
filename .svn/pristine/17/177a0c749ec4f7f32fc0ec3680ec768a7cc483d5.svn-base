package com.lesports.qmt.config.service.impl;

import com.lesports.qmt.config.client.QmtCountryInternalApis;
import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.config.service.CountryWebService;
import com.lesports.qmt.config.vo.CountryVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by zhangxudong@le.com on 2016/10/29.
 */
@Service("countryWebService")
public class CountryWebServiceImpl implements CountryWebService {

    private static final Logger LOG = LoggerFactory.getLogger(CountryWebServiceImpl.class);

//    @Override
//    public QmtPage<CountryVo> getCountryVoPage(int pageNumber, int pageSize, String codes, String abbreviation, String chineseName) {
//        if(0 == pageSize) pageSize = Integer.MAX_VALUE;
//        List<Country> result = new ArrayList<>();
//        if (false == StringUtils.isEmpty(codes)) {
//            result = this.getCountryListByIds(codes);
//        }
//        return null;
//    }

    @Override
    public Long saveWithId(CountryVo entity) {
        if (null == entity) return -1l;
        return QmtCountryInternalApis.saveCountry(entity);
    }

    @Override
    public CountryVo findOne(Long id) {
        if (null == id || id <= 0) return null;
        return new CountryVo(QmtCountryInternalApis.getCountryById(id));
    }

    @Override
    public boolean delete(Long id) {
        if (null == id || id <= 0) return false;
        return QmtCountryInternalApis.deleteCountry(id);
    }

    @Override
    public List<CountryVo> getCountryVoList(String codes, String abbreviation, String chineseName) {
        List<Country> result = new ArrayList<>();
        if (false == StringUtils.isEmpty(codes)) {
            result = this.getCountryListByIds(codes);
        } else if(false == StringUtils.isEmpty(abbreviation)) {
            result = this.getCountryListByAbbreviation(abbreviation);
        } else if(false == StringUtils.isEmpty(chineseName)) {
            result = this.getCountryListByChineseName(chineseName);
        } else {
            result = QmtCountryInternalApis.getCountryByQuery(new InternalQuery());
        }
        return new CountryVo().toCountryVoList(result);
    }

    private List<Country> getCountryListByChineseName(String chineseName) {
        InternalQuery query = new InternalQuery().with(new PageRequest(0, Integer.MAX_VALUE));
        query.addCriteria(new InternalCriteria("chinese_name").regex(chineseName.toUpperCase()+"\\w*"));
        return QmtCountryInternalApis.getCountryByQuery(query);
    }

    private List<Country> getCountryListByAbbreviation(String abbreviation) {
        String[] pieces = abbreviation.split(",");
        InternalQuery query = new InternalQuery().with(new PageRequest(0, Integer.MAX_VALUE));
        if(0 == pieces.length) {
            return QmtCountryInternalApis.getCountryByQuery(query);
        } else if(1 == pieces.length) {
            query.addCriteria(new InternalCriteria("code").regex(abbreviation.toUpperCase()+"\\w*"));
        } else {
            query.addCriteria(new InternalCriteria("code").in(Arrays.asList(pieces)));
        }
        return QmtCountryInternalApis.getCountryByQuery(query);
    }

    private List<Country> getCountryListByIds(String codes) {
        List<Integer> codess = new ArrayList<>();
        String[] pieces = codes.split(",");
        for (String piece : pieces) {
            try {
                Integer code = Integer.valueOf(piece.replace(" ", "").trim());
                if (code >= 0) {
                    codess.add(code);
                }
            } catch (NumberFormatException e) {
                continue ;
            }
        }
        InternalQuery query = new InternalQuery().with(new PageRequest(0, Integer.MAX_VALUE));
        query.addCriteria(new InternalCriteria("_id").in(codess));
        return QmtCountryInternalApis.getCountryByQuery(query);
    }
}
