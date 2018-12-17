package com.lesports;

import com.lesports.qmt.sbd.model.Match;
import com.lesports.qmt.sbd.model.Partner;
import com.lesports.qmt.sbd.model.PartnerType;
import com.lesports.qmt.sbd.thrift.TSbdInternalServiceAdapter;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.transcoders.CachedData;
import com.lesports.utils.transcoders.SerializingTranscoder;
import com.lesports.utils.transcoders.Transcoder;
import org.apache.thrift.TException;
import org.junit.Test;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/12/5
 */
public class MatchTest extends AbstractIntegrationTest {
    @Resource
    TSbdInternalServiceAdapter adapter;

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
    public void testA() throws TException {
        Partner partner = new Partner();
        partner.setId("1695710");
        partner.setType(PartnerType.STATS);
        InternalQuery query = new InternalQuery();
        InternalCriteria partnerCriteria = new InternalCriteria("partners");
        partnerCriteria.elemMatch(new InternalCriteria("_id", "eq", partner.getId()).andOperator(new InternalCriteria("type", "eq", partner.getType())));
        query.addCriteria(partnerCriteria);
        query.addCriteria(new InternalCriteria("deleted", "eq", false));
        List<Long> match = adapter.getEntityIdsByQuery(serialize(query), serialize(Match.class));
    }
}
