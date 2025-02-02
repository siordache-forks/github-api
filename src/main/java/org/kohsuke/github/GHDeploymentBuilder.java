package org.kohsuke.github;

import java.io.IOException;
import java.util.List;

/**
 * The type GHDeploymentBuilder.
 */
// Based on https://developer.github.com/v3/repos/deployments/#create-a-deployment
public class GHDeploymentBuilder {
    private final GHRepository repo;
    private final Requester builder;

    /**
     * Instantiates a new Gh deployment builder.
     *
     * @param repo
     *            the repo
     */
    public GHDeploymentBuilder(GHRepository repo) {
        this.repo = repo;
        this.builder = new Requester(repo.root);
    }

    /**
     * Instantiates a new Gh deployment builder.
     *
     * @param repo
     *            the repo
     * @param ref
     *            the ref
     */
    public GHDeploymentBuilder(GHRepository repo, String ref) {
        this(repo);
        ref(ref);
    }

    /**
     * Ref gh deployment builder.
     *
     * @param branch
     *            the branch
     * @return the gh deployment builder
     */
    public GHDeploymentBuilder ref(String branch) {
        builder.with("ref", branch);
        return this;
    }

    /**
     * Task gh deployment builder.
     *
     * @param task
     *            the task
     * @return the gh deployment builder
     */
    public GHDeploymentBuilder task(String task) {
        builder.with("task", task);
        return this;
    }

    /**
     * Auto merge gh deployment builder.
     *
     * @param autoMerge
     *            the auto merge
     * @return the gh deployment builder
     */
    public GHDeploymentBuilder autoMerge(boolean autoMerge) {
        builder.with("auto_merge", autoMerge);
        return this;
    }

    /**
     * Required contexts gh deployment builder.
     *
     * @param requiredContexts
     *            the required contexts
     * @return the gh deployment builder
     */
    public GHDeploymentBuilder requiredContexts(List<String> requiredContexts) {
        builder.with("required_contexts", requiredContexts);
        return this;
    }

    /**
     * Payload gh deployment builder.
     *
     * @param payload
     *            the payload
     * @return the gh deployment builder
     */
    public GHDeploymentBuilder payload(String payload) {
        builder.with("payload", payload);
        return this;
    }

    /**
     * Environment gh deployment builder.
     *
     * @param environment
     *            the environment
     * @return the gh deployment builder
     */
    public GHDeploymentBuilder environment(String environment) {
        builder.with("environment", environment);
        return this;
    }

    /**
     * Description gh deployment builder.
     *
     * @param description
     *            the description
     * @return the gh deployment builder
     */
    public GHDeploymentBuilder description(String description) {
        builder.with("description", description);
        return this;
    }

    /**
     * Create gh deployment.
     *
     * @return the gh deployment
     * @throws IOException
     *             the io exception
     */
    public GHDeployment create() throws IOException {
        return builder.to(repo.getApiTailUrl("deployments"), GHDeployment.class).wrap(repo);
    }
}
