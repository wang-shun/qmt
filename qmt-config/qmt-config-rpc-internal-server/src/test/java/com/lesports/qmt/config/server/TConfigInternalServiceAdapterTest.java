package com.lesports.qmt.config.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.CountryCode;
import com.lesports.qmt.config.api.common.CountrySubdivisionCode;
import com.lesports.qmt.config.api.service.TConfigInternalService;
import com.lesports.qmt.config.model.ClientPlatform;
import com.lesports.qmt.config.model.Copyright;
import com.lesports.qmt.config.model.Country;
import com.lesports.qmt.config.server.support.AbstractIntegrationTest;
import com.lesports.qmt.config.service.ClientPlatformService;
import com.lesports.qmt.config.service.CountryService;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.transcoders.CachedData;
import com.lesports.utils.transcoders.SerializingTranscoder;
import com.lesports.utils.transcoders.Transcoder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by denghui on 2016/11/7.
 */
public class TConfigInternalServiceAdapterTest extends AbstractIntegrationTest {

    @Resource
    private TConfigInternalService.Iface configInternalService;
    @Resource
    private ClientPlatformService clientPlatformService;
    @Resource
    private CountryService countryService;

    private Transcoder transcoder = new SerializingTranscoder();

    private static <T> ByteBuffer serialize(T entity) {
        return ByteBuffer.wrap(TRANSCODER.encode(entity).getFullData());
    }

    private static <T> T unserialize(ByteBuffer bf) {
        if (bf == null) {
            return null;
        }
        return TRANSCODER.decode(new CachedData(bf.array()));
    }

    private static final Transcoder TRANSCODER = new SerializingTranscoder();

    @Test
    public void testSaveClientPlatform() throws IOException {
        List<String> lines = FileUtils.readLines(new File("d:/xxx"));
        for (String line : lines) {
            String[] split = line.split(" ");
            String id = split[0];
            InternalQuery internalQuery = new InternalQuery();
            internalQuery.addCriteria(new InternalCriteria("client_id", "eq", id));
            ClientPlatform clientPlatform = clientPlatformService.findByQuery(internalQuery).get(0);
            clientPlatform.setCountry(CountryCode.US);
            clientPlatformService.save(clientPlatform);
        }
    }

    @Test
    public void testGetClientPlatform() throws IOException {
        InternalQuery internalQuery = new InternalQuery();
        List<ClientPlatform> clientPlatform = clientPlatformService.findByIds(Lists.newArrayList(101060L, 201060L, 301060L, 401060L, 501060L, 601060L, 701060L, 801060L, 901060L, 1001060L, 1101060L, 1201060L, 1301060L, 1401060L, 1501060L, 1601060L, 1701060L, 1801060L, 1901060L, 2001060L, 2101060L, 2201060L, 2301060L, 2401060L, 2501060L, 2601060L, 2701060L, 2801060L, 2901060L, 3001060L, 3101060L, 3201060L, 3301060L, 3401060L, 3501060L, 3601060L, 3701060L, 3801060L, 3901060L, 4001060L, 4101060L, 4201060L, 4301060L, 4401060L, 4501060L, 4601060L, 4701060L, 4801060L, 4901060L, 5001060L, 5101060L, 5201060L, 5301060L, 5401060L, 5501060L, 5601060L, 5701060L, 5801060L, 5901060L, 6001060L, 6101060L, 6201060L, 6301060L, 6401060L, 6501060L, 6601060L, 6701060L, 6801060L, 6901060L, 7001060L, 7101060L, 7201060L, 7301060L, 7401060L, 7501060L, 7601060L));
        Assert.assertTrue(CollectionUtils.isNotEmpty(clientPlatform));
    }

    @Test
    public void testGetClientPlatformByQuery() throws Exception {
        Pageable pageable = new PageRequest(0, 20, new Sort(new Sort.Order(Sort.Direction.DESC, "_id")));
        InternalQuery query = new InternalQuery().with(pageable);
        long bf = configInternalService.countByQuery(serialize(query), serialize(ClientPlatform.class));
        List<Long> ids = configInternalService.getEntityIdsByQuery(serialize(query), serialize(ClientPlatform.class));
        ByteBuffer res = configInternalService.getEntityBytesByIds(ids, serialize(ClientPlatform.class));
        List<ClientPlatform> result = unserialize(res);
        Assert.assertNotNull(result);
    }

    @Test
    public void testSaveCopyrightByQuery() throws Exception {
        Copyright copyright = new Copyright();
        copyright.setReleasePackageName("test release package 1");
//        copyright.setPlayType("直播");
        Set<Long> clientPlatformId = Sets.newHashSet();
        clientPlatformId.add(101060L);
        clientPlatformId.add(201060L);
        clientPlatformId.add(301060L);
        copyright.setClientPlatformId(clientPlatformId);
        copyright.setShieldAreaType(1);
        Set<String> countryId = Sets.newHashSet();
        countryId.add("CN");
        countryId.add("US");
        copyright.setShieldCountry(countryId);
        long bf = configInternalService.saveEntity(serialize(copyright), serialize(Copyright.class), true);
        Assert.assertNotNull(bf);
    }

    @Test
    public void testGetCopyrightById() throws Exception {
        ByteBuffer bf = configInternalService.getEntityBytesById(-1, serialize(Copyright.class));
        Copyright copyright = unserialize(bf);
        Assert.assertNotNull(copyright);
    }

    @Test
    public void testGetCopyrightByQuery() throws Exception {
        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, new Sort(new Sort.Order(Sort.Direction.DESC, "_id")));
        InternalQuery query = new InternalQuery().with(pageable);
        List<Long> ids = configInternalService.getEntityIdsByQuery(serialize(query), serialize(Copyright.class));
        ByteBuffer bf = configInternalService.getEntityBytesByIds(ids, serialize(Copyright.class));
        List<Copyright> copyrights = unserialize(bf);
        Assert.assertNotNull(copyrights);
    }

    @Test
    public void testSaveCountry() throws Exception {
        for (CountrySubdivisionCode code : CountrySubdivisionCode.values()) {
            Country country = new Country();
            country.setId(Long.valueOf(code.getCode() + 1));
            country.setCode(code.getAbbreviation());
            country.setChineseName(code.getChineseName());
            country.setDeleted(false);
            country.setNationalFlag("");
            country.setCreateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
            country.setUpdateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
            long bf = configInternalService.saveEntity(serialize(country), serialize(Country.class), true);
            System.out.println(bf);
//            QmtCountryInternalApis.saveCountry(country);
        }
    }

    @Test
    public void testGetCountryByIds() {
        countryService.findByIds(Lists.newArrayList(1L, 2L, 3L));
    }
}
