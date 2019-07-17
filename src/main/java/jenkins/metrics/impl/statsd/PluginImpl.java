package jenkins.metrics.impl.statsd;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;

import com.readytalk.metrics.StatsD;
import jenkins.metrics.api.Metrics;
import hudson.Plugin;
import jenkins.model.Jenkins;
import jenkins.model.JenkinsLocationConfiguration;
import jenkins.model.GlobalConfiguration;
import org.apache.commons.lang.StringUtils;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Time;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.readytalk.metrics.StatsDReporter;
/**
 * @author Stephen Connolly
 */

public class PluginImpl extends Plugin {
    private static final Logger LOGGER = Logger.getLogger(PluginImpl.class.getName());

    // private transient Map<GraphiteServer, StatsDReporter> reporters;

    // private StatsDReporter envReporter;

    static final String SAMPLING_INTERVAL_PROPERTY = "graphite.metrics.intervalSeconds";

    /** Return the sampling interval to use, in seconds
     */
    static int getSamplingIntervalSeconds() {
        // Safe to directly fetch property because only invoked when reporters are updated
        return Integer.getInteger((SAMPLING_INTERVAL_PROPERTY), 60);
    }

    public PluginImpl() {
        // this.reporters = new LinkedHashMap<GraphiteServer, StatsDReporter>();
    }

    @Override
    public void start() throws Exception {
    }

    // @Override
    // public synchronized void stop() throws Exception {
    //     if (reporters != null) {
    //         for (StatsDReporter r : reporters.values()) {
    //             r.stop();
    //         }
    //         reporters.clear();
    //     }
    //     if (envReporter != null) {
    //         envReporter.stop();
    //     }
    // }

    @Override
    public synchronized void postInitialize() throws Exception {
        MetricRegistry registry = Metrics.metricRegistry();
        StatsdServerConfig statsdConfig = StatsdServerConfig.get();
        String statsd_udp_host = statsdConfig.getHostname();
        String statsd_prefix = statsdConfig.getPrefix();
        int statsd_udp_port = statsdConfig.getPort();

        StatsDReporter r = StatsDReporter.forRegistry(registry)
                .prefixedWith(statsd_prefix)
                .convertRatesTo(TimeUnit.MINUTES)
                .convertDurationsTo(TimeUnit.SECONDS)
                .filter(MetricFilter.ALL)
                .build(statsd_udp_host, statsd_udp_port);
        r.start(10, TimeUnit.SECONDS);

        // updateReporters();
    }

    // public synchronized void updateReporters() throws URISyntaxException {
    //     if (reporters == null) {
    //         private List<StatsDReporter> reporters;
    //     }
    //     MetricRegistry registry = Metrics.metricRegistry();
    //     GraphiteServer.DescriptorImpl descriptor =
    //             Jenkins.getActiveInstance().getDescriptorByType(GraphiteServer.DescriptorImpl.class);
    //     if (descriptor == null) {
    //         return;
    //     }
    //     JenkinsLocationConfiguration locationConfiguration = JenkinsLocationConfiguration.get();
    //     String url = null;
    //     if (locationConfiguration != null) {
    //         url = locationConfiguration.getUrl();
    //     }
    //     URI uri = url == null ? null : new URI(url);
    //     String hostname = uri == null ? "localhost" : uri.getHost();
    //     Set<GraphiteServer> toStop = new HashSet<GraphiteServer>(reporters.keySet());
    //     for (GraphiteServer s : descriptor.getServers()) {
    //         toStop.remove(s);
    //         if (reporters.containsKey(s)) continue;
    //         String statsd_udp_host = s.getHostname();
    //         int statsd_udp_port = s.getPort();
    //         String prefix = StringUtils.isBlank(s.getPrefix()) ? hostname : s.getPrefix();

    //         StatsDReporter r = StatsDReporter.forRegistry(registry)
    //                 .prefixedWith(prefix)
    //                 .convertRatesTo(TimeUnit.MINUTES)
    //                 .convertDurationsTo(TimeUnit.SECONDS)
    //                 .filter(MetricFilter.ALL)
    //                 .build(statsd_udp_host, statsd_udp_port);

    //         reporters.put(s, r);
    //         r.start(10, TimeUnit.SECONDS);

    //         LOGGER.log(Level.INFO, "Started statsd reporter");
    //     }
    //     for (GraphiteServer s: toStop) {
    //         StatsDReporter r = reporters.get(s);
    //         reporters.remove(s);
    //         r.stop();
    //         LOGGER.log(Level.INFO, "Stopped Graphite reporter to {0}:{1} with prefix {2}", new Object[]{
    //                 s.getHostname(), s.getPort(), StringUtils.isBlank(s.getPrefix()) ? hostname : s.getPrefix()
    //         });
    //     }
    // }
}
