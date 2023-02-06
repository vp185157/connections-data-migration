package com.ncr.connections.datamigration.car.withoutepss.model;

import com.google.common.base.Objects;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.util.Arrays;

public class CarArchive implements Serializable {

    private static final long serialVersionUID = -6842163326601053767L;

    private Long id;

    private String groupId;

    private String artifactId;

    private String version;

    private String hash;

    private String name;

    private String description;

    private Short signedStatus;

    private byte[] content;

    public CarArchive() {
    }

    public CarArchive(final Long id) {
        this.id = id;
    }

    public CarArchive(final ParsedArtifact parsedArtifact, final Short signedStatus, final byte[] content) {
        groupId = parsedArtifact.getGav().getGroupId();
        artifactId = parsedArtifact.getGav().getArtifactId();
        version = parsedArtifact.getGav().getVersion();
        name = parsedArtifact.getName();
        description = parsedArtifact.getDescription();
        this.signedStatus = signedStatus;
        if (content != null) {
            setContent(Arrays.copyOf(content, content.length));
        }
    }

    public CarArchive(final CarArchive carArchive) {
        id = carArchive.getId();
        groupId = carArchive.getGroupId();
        artifactId = carArchive.getArtifactId();
        version = carArchive.getVersion();
        name = carArchive.getName();
        description = carArchive.getDescription();
        hash = carArchive.getHash();
        signedStatus = carArchive.getSignedStatus();
        if (carArchive.getContent() != null) {
            setContent(Arrays.copyOf(carArchive.getContent(), carArchive.getContent().length));
        }
    }

    public CarArchive(final Long id, final String groupId, final String artifactId, final String version,
                      final String hash, final String name, final String description, final Short signedStatus) {
        this.id = id;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.hash = hash;
        this.name = name;
        this.description = description;
        this.signedStatus = signedStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(final String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(final byte[] content) {
        if (content != null) {
            setHash(DigestUtils.md5DigestAsHex(content));
        }
        this.content = content;
    }

    public String getHash() {
        return hash;
    }

    private void setHash(final String hash) {
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Short getSignedStatus() {
        return signedStatus;
    }

    public void setSignedStatus(final Short signedStatus) {
        this.signedStatus = signedStatus;
    }

    public GavCoordinates toGav() {
        return new GavCoordinates(groupId, artifactId, version);
    }

    @Override public boolean equals(final Object object) {
        if (object instanceof CarArchive) {
            final CarArchive that = (CarArchive) object;
            return Objects.equal(getId(), that.getId());
        }
        return false;
    }

    @Override public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override public String toString() {
        return Objects.toStringHelper(this).add("id", getId()).add("groupId", getGroupId())
                .add("artifactId", getArtifactId()).add("version", getVersion()).toString();
    }

}
