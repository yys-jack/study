package com.wx.study.framework.priority.evaluator;

import com.wx.study.framework.priority.message.Message;
import com.wx.study.framework.priority.task.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

public abstract class AbstractService implements Runnable, Callable<Boolean>, EvaluatorService {
    protected Task task;
    protected Message message;
    protected int priority = 1;
    protected int complexRate = 100;
    protected int timeOutCount = 0;
    protected Date startTime;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int getComplexRate() {
        return complexRate;
    }

    public void setComplexRate(int complexRate) {
        this.complexRate = complexRate;
    }

    @Override
    public int getTimeOutCount() {
        return timeOutCount;
    }

    public void setTimeOutCount(int timeOutCount) {
        this.timeOutCount = timeOutCount;
    }

    @Override
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public AbstractService(Task task) {
        this.task = task;
        this.message = task.getMessage();
        this.startTime = Calendar.getInstance().getTime();
    }
    protected abstract void execute();

    @Override
    public void run() {
        //task running
        //execute();
        //task succ;
    }

    @Override
    public Boolean call() throws Exception {
        return true;
    }
}
