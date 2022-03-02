# oVirt Engine Logger Log4j Extension


> IMPORTANT: This project has been dropped from oVirt starting with oVirt 4.4.10 release.
> See [BZ#2044277](https://bugzilla.redhat.com/show_bug.cgi?id=2044277).
>
> Keeping the following section only for reference.


[![Copr build status](https://copr.fedorainfracloud.org/coprs/ovirt/ovirt-master-snapshot/package/ovirt-engine-extension-logger-log4j/status_image/last_build.png)](https://copr.fedorainfracloud.org/coprs/ovirt/ovirt-master-snapshot/package/ovirt-engine-extension-logger-log4j/)

Welcome to the oVirt Engine Extension Logger Log4j source repository.
This repository is hosted on [GitHub:ovirt-engine-extension-logger-log4j](https://github.com/oVirt/ovirt-engine-extension-logger-log4j)

This repository contains the extension which allow to pass oVirt Engine log records to appender provided by log4j, for example syslog.

## Configuration

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


## How to contribute

All contributions are welcome - patches, bug reports, and documentation issues.

### Submitting patches

Please submit patches to [GitHub:ovirt-engine-extension-logger-log4j](https://github.com/oVirt/ovirt-engine-extension-logger-log4j)
 If you are not familiar with the process, you can read about [collaborating with pull requests](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests) on the GitHub website.

### Found a bug or documentation issue?
To submit a bug or suggest an enhancement for oVirt Engine Extension Logger Log4j please use
[oVirt Bugzilla for ovirt-engine-extension-logger-log4j product](https://bugzilla.redhat.com/enter_bug.cgi?product=ovirt-engine-extension-logger-log4j).

If you don't have a Bugzilla account, you can still report [issues](https://github.com/oVirt/ovirt-engine-extension-logger-log4j/issues).

## Still need help?

If you have any other questions or suggestions, you can join and contact us on the [oVirt Users forum / mailing list](https://lists.ovirt.org/admin/lists/users.ovirt.org/).

