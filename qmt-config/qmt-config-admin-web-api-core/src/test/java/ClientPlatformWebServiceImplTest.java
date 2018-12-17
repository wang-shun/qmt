import com.lesports.qmt.config.api.common.CountrySubdivisionCode;
import com.lesports.qmt.config.client.QmtClientPlatformInternalApis;
import com.lesports.qmt.config.client.QmtCountryInternalApis;
import com.lesports.qmt.config.model.ClientPlatform;
import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeDateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 11/15/16.
 */
public class ClientPlatformWebServiceImplTest {

    @Test
    public void testGetClientPlatform() throws Exception {
        Pageable pageable = new PageRequest(0, 20, new Sort(new Sort.Order(Sort.Direction.DESC, "_id")));
        InternalQuery query = new InternalQuery().with(pageable);

//        long count = QmtClientPlatformInternalApis.countClientPlatformByQuery(query);
        List<ClientPlatform> data = QmtClientPlatformInternalApis.getClientPlatformsByQuery(query);
        Assert.assertNotEquals(null, new QmtPage(data, new QmtPageParam(), 0));
    }

    @Test
    public void testSaveCountry() throws Exception {
        for (CountrySubdivisionCode code : CountrySubdivisionCode.values()) {
            Country country = new Country();
            country.setId(Long.valueOf(country.getCode() + 1));
            country.setCode(country.getCode());
            country.setChineseName(country.getChineseName());
            country.setDeleted(false);
            country.setNationalFlag("");
            country.setCreateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
            country.setUpdateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
            QmtCountryInternalApis.saveCountry(country);
        }
    }
}
