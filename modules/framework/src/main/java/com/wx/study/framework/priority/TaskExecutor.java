package com.wx.priority;

import com.wx.study.framework.evaluator.DefaultEvaluator;
import com.wx.study.framework.evaluator.Evaluator;
import com.wx.study.framework.evaluator.EvaluatorService;
import com.wx.study.framework.task.Task;

import java.util.Calendar;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {
    private PriorityBlockingQueue<Runnable> queue;
    private Evaluator evaluator;
    private ThreadPoolExecutor pool;
    private ReEstimatorDaemon daemon;
    protected int minThread = 10;
    protected int maxThread = 100;
    protected int maxQueueSize = 500;
    protected long maxFreeTime = 30_000;
    protected long maxTimeOut = 30_000;

    public void start() {
        this.queue = new PriorityBlockingQueue<>(maxQueueSize);
        this.pool = new ThreadPoolExecutor(minThread, maxThread, maxFreeTime, TimeUnit.MILLISECONDS, queue
                , (r, executor) -> r = null);
        daemon.setTasks((Queue) queue);
        new Thread(daemon).start();

    }

    public void stop() {
        if (null != this.pool) {
            this.pool.shutdown();
        }
        if (null != this.daemon) {
            this.daemon.stop();
        }
    }

    public void dispatchTask(Task task) {
//        this.pool.execute();
    }
}

class ReEstimatorDaemon implements Runnable {
    private long retry = 100;

    private Queue<EvaluatorService> tasks;
    private TimeOut timeOut;
    private boolean stop;

    public ReEstimatorDaemon(Queue<EvaluatorService> tasks) {
        this.tasks = tasks;
    }

    public void setTasks(Queue<EvaluatorService> tasks) {
        this.tasks = tasks;
    }

    public void stop() {
        this.stop = true;
    }


    @Override
    public void run() {
        while (!stop) {
            try {
                if (!tasks.isEmpty() && null != timeOut) {
                    if (timeOut.isReOpen()) timeOut.reEstimate(tasks);
                    timeOut.removeTimeOutTask(tasks);
                }
                Thread.sleep(retry);
            } catch (InterruptedException e) {
            }
        }
    }
}

class TimeOut extends DefaultEvaluator {
    private long taskTimeOut = 30_000;

    public TimeOut() {
    }

    public long getTaskTimeOut() {
        return taskTimeOut;
    }

    public void setTaskTimeOut(long taskTimeOut) {
        this.taskTimeOut = taskTimeOut;
    }

    public void removeTimeOutTask(final Queue<EvaluatorService> tasks) {
        long now = System.currentTimeMillis();
        for (EvaluatorService defaultTask : tasks) {
            Calendar c = Calendar.getInstance();
            c.setTime(defaultTask.getStartTime());
            long start = c.getTimeInMillis();
            if (now - start > taskTimeOut) {
                tasks.remove(defaultTask);
            }
        }
    }
}
