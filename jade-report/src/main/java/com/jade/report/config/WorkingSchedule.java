package com.jade.report.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class WorkingSchedule {

    private static final Logger log = LogManager.getLogger(WorkingSchedule.class);

    // ... 省略其他部分 ...
}
