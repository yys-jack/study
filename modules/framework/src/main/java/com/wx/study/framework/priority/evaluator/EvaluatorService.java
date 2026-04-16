package com.wx.study.framework.priority.evaluator;

import java.util.Date;

/**
 * 评估接口
 */
public interface EvaluatorService {
    int getComplexRate();

    int getTimeOutCount();

    void setPriority(int priority);

    int getPriority();

    Date getStartTime();
}
