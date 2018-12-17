package com.lesports.qmt.op.web.api.core.util;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * lesports-projects.
 * get transform result in parallel, parameter {@link com.google.common.base.Function} define how to transform one item.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/1
 */
public class ParallelGetter<I, O> {
    private static final Logger LOG = LoggerFactory.getLogger(ParallelGetter.class);
    private static final int BATCH_SIZE = 20;
    private static final ExecutorService service = Executors.newCachedThreadPool();

    public List<O> get(List<I> inItems, final Function<I, O> function) {
        if (CollectionUtils.isNotEmpty(inItems)) {
            List<O> outItems = new ArrayList<>(inItems.size());
            List<List<I>> parts = Lists.partition(inItems, BATCH_SIZE);

            for (List<I> part : parts) {
                List<FutureTask<O>> futures = new ArrayList<>(part.size());
                for (final I item : part) {
                    FutureTask<O> subjectItemFutureTask = new FutureTask<>(new Callable<O>() {
                        @Override
                        public O call() {
                            return function.apply(item);
                        }
                    });
                    try {
                        service.execute(subjectItemFutureTask);
                        futures.add(subjectItemFutureTask);
                    } catch (Exception e) {
                        LOG.error(e.getMessage(), e);
                    }
                }
                for (FutureTask<O> future : futures) {
                    O outItem = null;
                    try {
                        outItem = future.get();
                    } catch (InterruptedException e) {
                        LOG.error(e.getMessage(), e);
                    } catch (ExecutionException e) {
                        LOG.error(e.getMessage(), e);
                    }
                    if (null != outItem) {
                        outItems.add(outItem);
                    }
                }
            }

            return outItems;
        }
        return Collections.emptyList();
    }
}
