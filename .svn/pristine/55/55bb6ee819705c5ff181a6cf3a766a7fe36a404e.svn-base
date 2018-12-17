package com.lesports.sms.api.web.core.util;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.utils.LeConcurrentUtils;
import com.lesports.utils.http.RestTemplateFactory;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.util.List;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/7
 */
public class ParallelGetterTest extends TestCase {

    @Test
    public void testGet() throws Exception {
        List<Long> ids = Lists.newArrayList(1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l);
        final RestTemplate template = RestTemplateFactory.getTemplate();
        long start = System.currentTimeMillis();
        Function<Long, String> function = new Function<Long, String>() {
            @Nullable
            @Override
            public String apply(Long aLong) {
//                return template.getForObject("http://suggest.letv.cn/suggestion?p=mobile&t=all&q=f1&from=mobile_sport15", String.class);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "";
            }
        };
        List<String> result = LeConcurrentUtils.parallelApply(ids, function);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        Assert.assertTrue(result.size() == ids.size());
    }
}