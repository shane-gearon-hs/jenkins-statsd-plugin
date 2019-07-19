package jenkins.metrics.impl.statsd;

import jenkins.model.GlobalConfiguration;
import org.kohsuke.stapler.DataBoundConstructor;
import java.util.logging.Logger;

/**
 * @author Shane Gearon
 */
public class StatsdServerConfig extends GlobalConfiguration {

    private static final Logger LOGGER = Logger.getLogger(StatsdServerConfig.class.getName());
    private String statsdHost;
    private int statsdPort;
    private String statsdBucket;

    /**
     * Convenience method to get the configuration object
     *
     * @return the configuration object
     */
    public static StatsdServerConfig get() {
        return GlobalConfiguration.all().get(StatsdServerConfig.class);
    }

    @DataBoundConstructor
    public StatsdServerConfig(String hostname, int port, String prefix) {
        load();
        this.statsdHost = hostname;
        this.statsdPort = port;
        this.statsdBucket = prefix;
    }

    public String getHostname() {
        return statsdHost;
    }

    public int getPort() {
        return statsdPort;
    }

    public String getPrefix() {
        return statsdBucket;
    }

}
