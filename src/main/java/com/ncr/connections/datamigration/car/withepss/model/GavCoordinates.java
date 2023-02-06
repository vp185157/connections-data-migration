package com.ncr.connections.datamigration.car.withepss.model;

import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public final class GavCoordinates implements Serializable {

    private static final String CS_CAR_EXTENSION = ".cs.car";

    private static final long serialVersionUID = 1L;

    // composite primary key consisting of artifactId, version and groupId
    private String groupId;
    private String artifactId;
    private String version;

    public GavCoordinates() {
    }

    public GavCoordinates(String consumerServiceFilename) {
        if (!validFileNameExtension(consumerServiceFilename)) {
            throw new IllegalArgumentException(consumerServiceFilename + " file extension not valid.");
        }
        if (!validGavCharacters(consumerServiceFilename)) {
            throw new IllegalArgumentException(consumerServiceFilename + " filename contains invalid characters");
        }
        final String[] gav = consumerServiceFilename.substring(0, consumerServiceFilename.indexOf(CS_CAR_EXTENSION)).split("_");
        if (gav.length < 3) {
            throw new IllegalArgumentException(consumerServiceFilename + " filename not valid.");
        }
        setGroupId(gav[0]);
        setArtifactId(gav[1]);
        setVersion(gav[2]);
    }

    public GavCoordinates(String groupId, String artifactId, String version) {
        if (!validGavCharacters(groupId, artifactId, version)) {
            String[] gav = new String[3];
            gav[0] = groupId;
            gav[1] = artifactId;
            gav[2] = version;
            throw new IllegalArgumentException(StringUtils.join(gav, "_") + " CS implementation identifier contains invalid characters");
        }
        setGroupId(groupId);
        setArtifactId(artifactId);
        setVersion(version);
    }
    
    public GavCoordinates(GavCoordinates template){
        this(template.groupId,template.artifactId,template.version);
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof GavCoordinates) {
            final GavCoordinates that = (GavCoordinates) object;
            return Objects.equal(this.groupId, that.groupId) && Objects.equal(this.artifactId, that.artifactId)
                    && Objects.equal(this.version, that.version);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.groupId, this.artifactId, this.version);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("groupId", this.groupId).add("artifactId", this.artifactId)
                .add("version", this.version).toString();
    }

    public String toFilename() {
        return this.groupId + "_" + this.artifactId + "_" + this.version + CS_CAR_EXTENSION;
    }

    public String toURI() {
        return "/" + this.groupId + "/" + this.artifactId + "/" + this.version + "/";
    }
    
    private boolean validFileNameExtension(String consumerServiceFilename) {
        final int endString = consumerServiceFilename.indexOf(CS_CAR_EXTENSION);
        return endString > 0;
    }
    
    private boolean validGavCharacters(String... gav) {
        for (String gavPart : gav) {
            if (!gavPart.matches("[A-Za-z0-9_\\-\\.]+")) {
                return false;
            }
        }
        return true;
    }
}
