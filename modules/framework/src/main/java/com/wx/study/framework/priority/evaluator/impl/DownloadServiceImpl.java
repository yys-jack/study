package com.wx.study.framework.priority.evaluator.impl;

import com.wx.study.framework.priority.evaluator.AbstractService;
import com.wx.study.framework.priority.task.Task;

public class DownloadServiceImpl extends AbstractService implements Comparable<DownloadServiceImpl> {
    public DownloadServiceImpl(Task task) {
        super(task);
    }

    @Override
    protected void execute() {
        //execute download

    }


    @Override
    public int compareTo(DownloadServiceImpl o) {
        return getPriority() - o.getPriority();
    }
}
