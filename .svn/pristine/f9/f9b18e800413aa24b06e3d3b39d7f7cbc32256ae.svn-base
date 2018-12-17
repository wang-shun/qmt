package com.lesports.qmt.config.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.config.param.SaveCopyrightParam;
import com.lesports.qmt.config.service.CopyrightWebService;
import com.lesports.qmt.config.vo.CopyrightVo;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by zhangxudong@le.com on 11/10/16.
 */
@RestController
@RequestMapping(value = "/copyrights")
@WebLogAnnotation(ID_TYPE = IdType.COPYRIGHT)
public class CopyrightController {
    @Resource
    private CopyrightWebService copyrightWebService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public QmtPage<CopyrightVo> getCopyrightVoPage(
            @RequestParam(value = "published", required = false) Boolean published,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "20") int pageSize
    ) {
        return copyrightWebService.getCopyrightVoPage(pageNumber, pageSize, published);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CopyrightVo getCopyrightVoPage(@PathVariable("id") long id) {
        return copyrightWebService.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
   @PreAuthorize("hasPermission('lesports.qmt.confManage.copyRightEdit', 'UPDATE')")
    public boolean modifyCopyrightStatus(@PathVariable("id") long id) {
        return copyrightWebService.publishCopyright(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.confManage.copyRightEdit', 'ADD')")
    public boolean getCopyrightVoPage(@ModelAttribute SaveCopyrightParam saveCopyrightParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return false;
        Long id = copyrightWebService.saveCopyrightParam(saveCopyrightParam);
        return LeNumberUtils.toLong(id) > 0 ? true : false;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public long checkreleasePackageNameIsSame(@RequestParam(value = "release_package_name", required = false, defaultValue = "") String releasePackageName) {
        if ("".equals(releasePackageName)) return -1l;
        return copyrightWebService.checkCopyrightByName(releasePackageName);
    }
}
