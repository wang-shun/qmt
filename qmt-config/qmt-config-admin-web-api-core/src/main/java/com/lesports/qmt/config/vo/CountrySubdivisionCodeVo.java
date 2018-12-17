package com.lesports.qmt.config.vo;

import com.lesports.qmt.config.api.common.CountrySubdivisionCode;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxudong@le.com on 11/10/16.
 */
public class CountrySubdivisionCodeVo implements Serializable {

    private int code;
    private String abbreviation;
    private String chineseName;

    public CountrySubdivisionCodeVo() {
    }

    public CountrySubdivisionCodeVo(int code, String abbreviation, String chineseName) {
        this.code = code;
        this.abbreviation = abbreviation;
        this.chineseName = chineseName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public static List<CountrySubdivisionCodeVo> getAll() {
        List<CountrySubdivisionCodeVo> result = new ArrayList<>();
        for (CountrySubdivisionCode code : CountrySubdivisionCode.values()) {
            result.add(new CountrySubdivisionCodeVo(code.getCode(), code.getAbbreviation(), code.getChineseName()));
        }
        return result;
    }

    public static List<CountrySubdivisionCodeVo> getListByCode(String codes) {
        List<CountrySubdivisionCodeVo> result = new ArrayList<>();
        if (true == StringUtils.isEmpty(codes)) return result;
        try {
            String[] pieces = codes.split(",");
            for (String piece : pieces) {
                Integer code = Integer.valueOf(piece.replace(" ", "").trim());
                if (code >= 0 && code < CountrySubdivisionCode.values().length) {
                    CountrySubdivisionCodeVo vo = new CountrySubdivisionCodeVo(CountrySubdivisionCode.values()[code].getCode(), CountrySubdivisionCode.values()[code].getAbbreviation(), CountrySubdivisionCode.values()[code].getChineseName());
                    result.add(vo);
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static List<CountrySubdivisionCodeVo> getListByChineseName(String chineseName) {
        List<CountrySubdivisionCodeVo> result = new ArrayList<>();
        if (true == StringUtils.isEmpty(chineseName)) return result;
        for (CountrySubdivisionCode code : CountrySubdivisionCode.values()) {
            if(code.getChineseName().contains(chineseName)) {
                result.add(new CountrySubdivisionCodeVo(code.getCode(), code.getAbbreviation(), code.getChineseName()));
            }
        }
        return result;
    }

    public static List<CountrySubdivisionCodeVo> getListByAbbreviation(String abbreviation) {
        List<CountrySubdivisionCodeVo> result = new ArrayList<>();
        if (true == StringUtils.isEmpty(abbreviation)) return result;
        for (CountrySubdivisionCode code : CountrySubdivisionCode.values()) {
            if(code.getAbbreviation().contains(abbreviation.toUpperCase())) {
                result.add(new CountrySubdivisionCodeVo(code.getCode(), code.getAbbreviation(), code.getChineseName()));
            }
        }
        return result;
    }
}
