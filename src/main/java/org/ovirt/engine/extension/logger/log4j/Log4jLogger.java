/*
 * Copyright 2014 Red Hat Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package org.ovirt.engine.extension.logger.log4j;

import org.ovirt.engine.api.extensions.Base;
import org.ovirt.engine.api.extensions.ExtMap;
import org.ovirt.engine.api.extensions.Extension;
import org.ovirt.engine.api.extensions.logger.Logger;
import org.apache.log4j.Appender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Hierarchy;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.RootLogger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Log4jLogger implements Extension {

    private static final Map<Level, org.apache.log4j.Level> logLevelMap = new HashMap<>();
    private org.apache.log4j.Logger logger = new RootLogger(org.apache.log4j.Level.ALL);

    static {
        logLevelMap.put(Level.ALL, org.apache.log4j.Level.ALL);
        logLevelMap.put(Level.CONFIG, org.apache.log4j.Level.INFO);
        logLevelMap.put(Level.FINE, org.apache.log4j.Level.DEBUG);
        logLevelMap.put(Level.FINER, org.apache.log4j.Level.TRACE);
        logLevelMap.put(Level.FINEST, org.apache.log4j.Level.TRACE);
        logLevelMap.put(Level.INFO, org.apache.log4j.Level.INFO);
        logLevelMap.put(Level.OFF, org.apache.log4j.Level.OFF);
        logLevelMap.put(Level.SEVERE, org.apache.log4j.Level.ERROR);
        logLevelMap.put(Level.WARNING, org.apache.log4j.Level.WARN);
    }

    @Override
    public void invoke(ExtMap input, ExtMap output) {
        try {
            if (input.get(Base.InvokeKeys.COMMAND).equals(Base.InvokeCommands.LOAD)) {
                doLoad(input, output);
            } else if (input.get(Base.InvokeKeys.COMMAND).equals(Base.InvokeCommands.INITIALIZE)) {
                doInit(input, output);
            } else if (input.get(Base.InvokeKeys.COMMAND).equals(Logger.InvokeCommands.PUBLISH)) {
                publish(input.<LogRecord>get(Logger.InvokeKeys.LOG_RECORD));
            } else if (input.get(Base.InvokeKeys.COMMAND).equals(Logger.InvokeCommands.FLUSH)) {
                // no flush method
            } else if (input.get(Base.InvokeKeys.COMMAND).equals(Logger.InvokeCommands.CLOSE)) {
                // no close method
            } else {
                output.put(Base.InvokeKeys.RESULT, Base.InvokeResult.UNSUPPORTED);
            }
            output.putIfAbsent(Base.InvokeKeys.RESULT, Base.InvokeResult.SUCCESS);
        } catch (Exception ex) {
            output.mput(Base.InvokeKeys.RESULT, Base.InvokeResult.FAILED).
                    mput(Base.InvokeKeys.MESSAGE, ex.getMessage());
        }
    }

    private void doLoad(ExtMap inputMap, ExtMap outputMap) {
        ExtMap context = inputMap.<ExtMap> get(Base.InvokeKeys.CONTEXT).mput(
                Base.ContextKeys.AUTHOR,
                "The oVirt Project"
        ).mput(
                Base.ContextKeys.EXTENSION_NAME,
                "ovirt-engine-extension-logger-log4j"
        ).mput(
                Base.ContextKeys.LICENSE,
                "ASL 2.0"
        ).mput(
                Base.ContextKeys.HOME_URL,
                "http://www.ovirt.org"
        ).mput(
                Base.ContextKeys.VERSION,
                Config.PACKAGE_VERSION
        ).mput(
                Base.ContextKeys.EXTENSION_NOTES,
                String.format(
                    "Display name: %s",
                    Config.PACKAGE_DISPLAY_NAME
                )
        ).mput(
                Base.ContextKeys.BUILD_INTERFACE_VERSION,
                Base.INTERFACE_VERSION_CURRENT
        );
    }

    private void doInit(ExtMap inputMap, ExtMap outputMap) {
        LoggerRepository hierarchy = new Hierarchy(logger);
        (new PropertyConfigurator()).doConfigure(
            inputMap.<ExtMap>get(
                    Base.InvokeKeys.CONTEXT
            ).<Properties>get(Base.ContextKeys.CONFIGURATION),
            hierarchy
        );
    }

    public void publish(LogRecord record) {
        logger.callAppenders(new LoggingEvent(record.getSourceClassName(),
            org.apache.log4j.Logger.getLogger(record.getLoggerName()),
            logLevelMap.get(record.getLevel()),
            record.getMessage(),
            record.getThrown()));
    }

}

