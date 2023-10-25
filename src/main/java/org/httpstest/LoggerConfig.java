package org.httpstest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class LoggerConfig {
    
    public static void configureLogger(){

        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        builder.setStatusLevel(Level.INFO);
        // naming the logger configuration
        builder.setConfigurationName("DefaultLogger");

        // create a console appender
        AppenderComponentBuilder appenderBuilder = builder.newAppender("Console", "CONSOLE")
                        .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        // add a layout like pattern, json etc
        appenderBuilder.add(builder.newLayout("PatternLayout")
                        .addAttribute("pattern", "%d %p %c [%t] %m%n"));
        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.INFO);
        rootLogger.add(builder.newAppenderRef("Console"));

        builder.add(appenderBuilder);
        builder.add(rootLogger);
        Configurator.reconfigure(builder.build());

        System.out.println("Logger configured successfully");

    }

}
