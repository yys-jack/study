package com.wx.study.framework.priority.evaluator.impl;

import com.wx.study.framework.priority.evaluator.AbstractService;
import com.wx.study.framework.priority.task.Task;

public class DefaultServiceImpl extends AbstractService {
    public DefaultServiceImpl(Task task) {
        super(task);
    }

    @Override
    protected void execute() {
        //default execute
    }
}
