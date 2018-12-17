package com.lesports.qmt.config.web.controller;

import com.lesports.qmt.config.vo.CountrySubdivisionCodeVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhangxudong@le.com on 11/10/16.
 */
@RestController
@RequestMapping(value = "/countrySubdivisionCodes")
public class CountrySubdivisionCodeController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CountrySubdivisionCodeVo> getCountrySubdivisionCodeVoListByCodeOrAbbreviationOrChineseName(
            @RequestParam(value = "codes", required = false, defaultValue = "") String codes,
            @RequestParam(value = "abbreviation", required = false, defaultValue = "") String abbreviation,
            @RequestParam(value = "chineseName", required = false, defaultValue = "") String chineseName) {
        if (false == StringUtils.isEmpty(codes)) return CountrySubdivisionCodeVo.getListByCode(codes);
        if (false == StringUtils.isEmpty(abbreviation))
            return CountrySubdivisionCodeVo.getListByAbbreviation(abbreviation);
        if (false == StringUtils.isEmpty(chineseName))
            return CountrySubdivisionCodeVo.getListByChineseName(chineseName);
        return CountrySubdivisionCodeVo.getAll();
    }
}
