package com.lesports.qmt.play.server;

import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/* ------------------------------------------------------------ */

/**
 * Jetty ThreadPool using java 5 ThreadPoolExecutor
 * This class wraps a {@link java.util.concurrent.ExecutorService} as a {@link org.eclipse.jetty.util.thread.ThreadPool} and
 * {@link org.eclipse.jetty.util.component.LifeCycle} interfaces so that it may be used by the Jetty <code>org.eclipse.jetty.server.Server</code>
 */
public class LeJettyThreadPool extends AbstractLifeCycle implements ThreadPool, LifeCycle {
    private static final Logger LOG = LoggerFactory.getLogger(LeJettyThreadPool.class);
    private static final int queueSize = 0;
    private final ExecutorService _executor;

    /* ------------------------------------------------------------ */

    /**
     * Wraps an {@link java.util.concurrent.ThreadPoolExecutor}.
     * Max pool size is 256, pool thread timeout after 60 seconds, and core pool size is 32 when queueSize >= 0.
     * queueSize can be -1 for using an unbounded {@link java.util.concurrent.LinkedBlockingQueue}, 0 for using a
     * {@link java.util.concurrent.SynchronousQueue}, greater than 0 for using a {@link java.util.concurrent.ArrayBlockingQueue} of the given size.
     */
    public LeJettyThreadPool() {
        _executor = queueSize < 0 ? new ThreadPoolExecutor(256, 256, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()) :
                queueSize == 0 ? new ThreadPoolExecutor(32, 256, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()) :
                        new ThreadPoolExecutor(32, 256, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));

        LOG.info("create LeJettyThreadPool. _executor : {}", _executor);
    }


    /* ------------------------------------------------------------ */
    @Override
    public void execute(Runnable job) {
        _executor.execute(job);
    }

    /* ------------------------------------------------------------ */
    public boolean dispatch(Runnable job) {
        try {
            _executor.execute(job);
            return true;
        } catch (RejectedExecutionException e) {
            LOG.warn("fail to dispatch", e);
            return false;
        }
    }

    /* ------------------------------------------------------------ */
    public int getIdleThreads() {
        if (_executor instanceof ThreadPoolExecutor) {
            final ThreadPoolExecutor tpe = (ThreadPoolExecutor) _executor;
            return tpe.getPoolSize() - tpe.getActiveCount();
        }
        return -1;
    }

    /* ------------------------------------------------------------ */
    public int getThreads() {
        if (_executor instanceof ThreadPoolExecutor) {
            final ThreadPoolExecutor tpe = (ThreadPoolExecutor) _executor;
            return tpe.getPoolSize();
        }
        return -1;
    }

    /* ------------------------------------------------------------ */
    public boolean isLowOnThreads() {
        if (_executor instanceof ThreadPoolExecutor) {
            final ThreadPoolExecutor tpe = (ThreadPoolExecutor) _executor;
            // getActiveCount() locks the thread pool, so execute it last
            return tpe.getPoolSize() == tpe.getMaximumPoolSize() &&
                    tpe.getQueue().size() >= tpe.getPoolSize() - tpe.getActiveCount();
        }
        return false;
    }

    /* ------------------------------------------------------------ */
    public void join() throws InterruptedException {
        _executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    /* ------------------------------------------------------------ */
    @Override
    protected void doStop() throws Exception {
        super.doStop();
        _executor.shutdownNow();
    }
}

