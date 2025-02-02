package org.kohsuke.github;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The type GHHook.
 *
 * @author Kohsuke Kawaguchi
 */
@SuppressFBWarnings(value = { "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_FIELD", "NP_UNWRITTEN_FIELD" },
        justification = "JSON API")
public abstract class GHHook extends GHObject {
    String name;
    List<String> events;
    boolean active;
    Map<String, String> config;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets events.
     *
     * @return the events
     */
    public EnumSet<GHEvent> getEvents() {
        EnumSet<GHEvent> s = EnumSet.noneOf(GHEvent.class);
        for (String e : events) {
            if (e.equals("*"))
                s.add(GHEvent.ALL);
            else
                s.add(Enum.valueOf(GHEvent.class, e.toUpperCase(Locale.ENGLISH)));
        }
        return s;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Gets config.
     *
     * @return the config
     */
    public Map<String, String> getConfig() {
        return Collections.unmodifiableMap(config);
    }

    /**
     * Ping.
     *
     * @throws IOException
     *             the io exception
     * @see <a href="https://developer.github.com/v3/repos/hooks/#ping-a-hook">Ping hook</a>
     */
    public void ping() throws IOException {
        new Requester(getRoot()).method("POST").to(getApiRoute() + "/pings");
    }

    /**
     * Deletes this hook.
     *
     * @throws IOException
     *             the io exception
     */
    public void delete() throws IOException {
        new Requester(getRoot()).method("DELETE").to(getApiRoute());
    }

    /**
     * @deprecated This object has no HTML URL.
     */
    @Override
    public URL getHtmlUrl() {
        return null;
    }

    abstract GitHub getRoot();

    abstract String getApiRoute();
}
