package org.kohsuke.github;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

/**
 * Builder pattern for creating a new blob. Based on https://developer.github.com/v3/git/blobs/#create-a-blob
 */
public class GHBlobBuilder {
    private final GHRepository repo;
    private final Requester req;

    GHBlobBuilder(GHRepository repo) {
        this.repo = repo;
        req = new Requester(repo.root);
    }

    /**
     * Configures a blob with the specified text {@code content}.
     *
     * @param content
     *            string text of the blob
     * @return a GHBlobBuilder
     */
    public GHBlobBuilder textContent(String content) {
        req.with("content", content);
        req.with("encoding", "utf-8");
        return this;
    }

    /**
     * Configures a blob with the specified binary {@code content}.
     *
     * @param content
     *            byte array of the blob
     * @return a GHBlobBuilder
     */
    public GHBlobBuilder binaryContent(byte[] content) {
        String base64Content = Base64.encodeBase64String(content);
        req.with("content", base64Content);
        req.with("encoding", "base64");
        return this;
    }

    private String getApiTail() {
        return String.format("/repos/%s/%s/git/blobs", repo.getOwnerName(), repo.getName());
    }

    /**
     * Creates a blob based on the parameters specified thus far.
     *
     * @return a GHBlob
     * @throws IOException
     *             if the blob cannot be created.
     */
    public GHBlob create() throws IOException {
        return req.method("POST").to(getApiTail(), GHBlob.class);
    }
}
