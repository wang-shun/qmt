package com.lesports.qmt.config.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.config.service.CountryWebService;
import com.lesports.qmt.config.vo.CountryVo;
import com.lesports.qmt.log.WebLogAnnotation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangxudong@le.com on 11/10/16.
 */
@RestController
@RequestMapping(value = "/countries")
@WebLogAnnotation(ID_TYPE = IdType.COUNTRY)
public class CountryController {
    @Resource
    private CountryWebService countryWebService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CountryVo> getCountrySubdivisionCodeVoListByCodeOrAbbreviationOrChineseName(
            @RequestParam(value = "ids", required = false, defaultValue = "") String ids,
            @RequestParam(value = "code", required = false, defaultValue = "") String code,
            @RequestParam(value = "chineseName", required = false, defaultValue = "") String chineseName) {
        return countryWebService.getCountryVoList(ids, code, chineseName);
    }
}
