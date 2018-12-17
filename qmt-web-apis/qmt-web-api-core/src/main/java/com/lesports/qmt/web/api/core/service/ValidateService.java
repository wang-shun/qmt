package com.lesports.qmt.web.api.core.service;

import com.lesports.model.ValidateReq;
import com.lesports.model.ValidateRes;

/**
 * Created by lufei1 on 2016/6/21.
 */
public interface ValidateService {

    ValidateRes authVodForDrm(ValidateReq validateReq);


    ValidateRes authLiveForDrm(ValidateReq validateReq);


    ValidateRes authCarouselForDrm(ValidateReq validateReq);

}
