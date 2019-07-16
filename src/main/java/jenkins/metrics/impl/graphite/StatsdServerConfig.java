package jenkins.metrics.impl.graphite;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import jenkins.model.GlobalConfiguration;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Stephen Connolly
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

    // @Override
    // public String toString() {
    //     final StringBuilder sb = new StringBuilder("GraphiteServer{");
    //     sb.append("hostname='").append(hostname).append('\'');
    //     sb.append(", port=").append(port);
    //     sb.append(", prefix='").append(prefix).append('\'');
    //     sb.append('}');
    //     return sb.toString();
    // }

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) {
    //         return true;
    //     }
    //     if (o == null || getClass() != o.getClass()) {
    //         return false;
    //     }

    //     GraphiteServer that = (GraphiteServer) o;

    //     if (port != that.port) {
    //         return false;
    //     }
    //     if (hostname != null ? !hostname.equals(that.hostname) : that.hostname != null) {
    //         return false;
    //     }
    //     if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) {
    //         return false;
    //     }

    //     return true;
    // }

    // @Override
    // public int hashCode() {
    //     int result = hostname != null ? hostname.hashCode() : 0;
    //     result = 31 * result + port;
    //     result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
    //     return result;
    // }

    // @Extension
    // public static class DescriptorImpl extends Descriptor<StatsdServer> {
    //     @GuardedBy("this")
    //     private List<StatsdServer> servers;

    //     public DescriptorImpl() {
    //         super();
    //         load();
    //     }

    //     @Override
    //     public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
    //         setServers(req.bindJSONToList(StatsdServer.class, json.get("servers")));
    //         return true;
    //     }

    //     public synchronized List<StatsdServer> getServers() {
    //         return servers == null
    //                 ? Collections.<StatsdServer>emptyList()
    //                 : Collections.unmodifiableList(new ArrayList<StatsdServer>(servers));
    //     }

    //     public synchronized void setServers(@Nonnull List<StatsdServer> servers) {
    //         this.servers = servers;
    //         save();
    //         // try {
    //         //     Jenkins.getActiveInstance().getPlugin(PluginImpl.class).updateReporters();
    //         // } catch (URISyntaxException e) {
    //         //     LOGGER.log(Level.WARNING, "Could not update Statsd reporters", e);
    //         // }
    //     }
    // }

}
