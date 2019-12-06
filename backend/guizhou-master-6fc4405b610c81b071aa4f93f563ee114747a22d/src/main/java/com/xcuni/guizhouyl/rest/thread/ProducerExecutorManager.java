package com.xcuni.guizhouyl.rest.thread;

import com.xcuni.guizhouyl.ApplicationSetup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerExecutorManager {
    private ExecutorService execService;
    private static ProducerExecutorManager instance = null;

    private ProducerExecutorManager() {
        int size = ApplicationSetup.ProducerThreadPoolSize;
        execService = Executors.newFixedThreadPool(size, Executors.defaultThreadFactory());
    }

    public static ProducerExecutorManager getInstance() {
        if (instance == null)
            instance = new ProducerExecutorManager();

        return instance;
    }

    public void shutdown() {
        execService.shutdown();
        instance = null;
    }

    /**
     * 异步执行第三方接口
     */
    public void doExecute(FetchUserByCityRunner runner) {
        execService.execute(runner);
    }

    public void doExecute(FetchUserListRunner runner) {
        execService.execute(runner);
    }

    public void doExecute(FetchUserDataManagerRunner runner) {
        execService.execute(runner);
    }

    public void doExecute(FetchUserDataRunner runner) {
        execService.execute(runner);
    }

    public void doExecute(VerificationProgressScanner runner) {
        execService.execute(runner);
    }

}
