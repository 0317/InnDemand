package demand.inn.com.inndemand.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class PicassoThreadExecutor {
    private static ThreadPoolExecutor mThreadPoolExecutor;

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        if (mThreadPoolExecutor == null)
            mThreadPoolExecutor = new ThreadPoolExecutor(1, 2, 2, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(), new PicassoThreadFactory());
        return mThreadPoolExecutor;
    }

    static class PicassoThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            return new PicassoThread(r);
        }
    }

    private static class PicassoThread extends Thread {
        public static long mThreadCount = 0;

        public PicassoThread(Runnable r) {
            super(r);
            setName("PicassoEx" + mThreadCount);
            mThreadCount += 1;
        }

        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            super.run();
        }
    }
}
