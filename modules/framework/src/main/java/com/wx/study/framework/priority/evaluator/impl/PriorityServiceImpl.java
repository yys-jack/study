package com.wx.study.framework.priority.evaluator.impl;

import com.wx.study.framework.priority.evaluator.AbstractService;
import com.wx.study.framework.priority.task.Task;

public class PriorityServiceImpl extends AbstractService implements Comparable<PriorityServiceImpl> {
    public PriorityServiceImpl(Task task) {
        super(task);
    }

    @Override
    protected void execute() {
        //execute priority
    }


    @Override
    public int compareTo(PriorityServiceImpl o) {
        return getPriority() - o.getPriority();
    }
}
