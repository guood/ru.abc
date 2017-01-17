package ru.abc.common.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.abc.common.messages.Messages;

public class BaseService {

    @Autowired
    protected Messages messages;

    protected void logStartMethod(String methodName, Object... params) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("{}.{}({}) started", this.getClass().getName(), methodName, params);
    }

    protected void logEndMethod(String methodName, Object result) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Return {} from {}.{}) success", result, this.getClass().getName(), methodName);
    }
}
