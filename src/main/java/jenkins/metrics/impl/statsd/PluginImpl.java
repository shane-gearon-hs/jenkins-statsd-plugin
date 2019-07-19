package jenkins.metrics.impl.statsd;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;

import jenkins.metrics.api.Metrics;
import hudson.Plugin;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.readytalk.metrics.StatsDReporter;
/**
 * @author Stephen Connolly
 */

public class PluginImpl extends Plugin {
    private static final Logger LOGGER = Logger.getLogger(PluginImpl.class.getName());

    public PluginImpl() {}

    @Override
    public void start() throws Exception {}

    @Override
    public synchronized void postInitialize() throws Exception {
        MetricRegistry registry = Metrics.metricRegistry();
        String statsd_udp_host = "service-statsite1.metrics.us-east-1.hootops.com";
        String statsd_prefix = "service.jenkins-kubernetes.staging.metrics";
        int statsd_udp_port = 8125;

        StatsDReporter r = StatsDReporter.forRegistry(registry)
                .prefixedWith(statsd_prefix)
                .convertRatesTo(TimeUnit.MINUTES)
                .convertDurationsTo(TimeUnit.SECONDS)
                .filter(MetricFilter.ALL)
                .build(statsd_udp_host, statsd_udp_port);
        r.start(10, TimeUnit.SECONDS);
    }
}
