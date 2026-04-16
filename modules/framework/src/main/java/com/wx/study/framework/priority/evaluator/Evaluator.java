package com.wx.study.framework.priority.evaluator;

import java.util.Queue;

/**
 * 评估接口
 */
public interface Evaluator {
    /**
     * 评估开关是否打开
     * @return
     */
    boolean isOpen();

    /**
     * 优先级评估
     */
    void estimate(EvaluatorService service);

    /**
     * 重估所有驻留任务时间长的任务
     */
    void reEstimate(Queue<EvaluatorService> services);

    /**
     * 重估开关是否打开
     * @return
     */
    boolean isReOpen();
}
