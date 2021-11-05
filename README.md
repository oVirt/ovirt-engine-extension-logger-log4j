oVirt Log4j Logger Extension
============================

This extension implements Log4j Logger to provide Log4j appenders for oVirt,
for example it's possible to pass log recors from oVirt engine to remote
syslog service.

CONFIGURATION
-------------

Copy configuration file from `/usr/share/ovirt-engine-extension-logger-log4j/examples/Log4jLogger.properties`
to `/etc/ovirt-engine/extensions.d/Log4jLogger.properties` and replace
'`localhost`' with proper FQDN or IP address of the host where your syslog
service is running.

For example if your syslog service is running on `syslog.example.com`, you need to update below line

```
  log4j.appender.myappender.SyslogHost=localhost
```

to

```
  log4j.appender.myappender.SyslogHost=syslog.example.com
```

After updating `/etc/ovirt-engine/extensions.d/Log4jLogger.properties` file
you need to restart ovirt-engine service to apply the update:

```bash
  systemctl restart ovirt-engine
```
