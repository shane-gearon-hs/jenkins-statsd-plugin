# Metrics StatsD Plugin

This plugin streams [Metrics](http://wiki.jenkins-ci.org/display/JENKINS/Metrics+Plugin) to
a StatsD server.

See also this [plugin's wiki page][wiki]

# Environment

The following build environment is required to build this plugin

* `java-1.6` and `maven-3.0.5`

# Build

To build the plugin locally:

    mvn install -Dmaven.javadoc.skip=true -Dmaven.test.skip=true

# Release

To release the plugin:

    mvn release:prepare release:perform -B

# Test local instance

To test in a local Jenkins instance

    mvn hpi:run

