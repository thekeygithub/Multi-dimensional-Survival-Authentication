package com.xcuni.guizhouyl.rest.thread;

import com.xcuni.guizhouyl.ApplicationSetup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 多线程业务处理调度器<br>
 * 说明：
 * 用于执行业务任务单元,由线程池负责调配<br>
 */
public class ConsumerExecutorManager {
    private ExecutorService execService;//业务线程核心处理器
    private static ConsumerExecutorManager instance = null;//new ConsumerExecutorManager();

    //final BlockingQueue<Runnable> threadQueue = new ArrayBlockingQueue<>(100);

    private ConsumerExecutorManager() {
        int size = ApplicationSetup.ConsumerThreadPoolSize;
        execService = Executors.newFixedThreadPool(size, Executors.defaultThreadFactory());
    }

    public static ConsumerExecutorManager getInstance() {
        if (instance == null)
            instance = new ConsumerExecutorManager();

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

    public void doExecute(SingleVerificationRunner runner) {
        execService.execute(runner);
    }

    public void doExecute(VerificationManagerRunner runner) {
        execService.execute(runner);
    }
//	 public void doExecute2(BussinessRunner2 runner){
//			execService.execute(runner);//new FetchUserByCityRunner(msg, svcNode)
//	 }

}
