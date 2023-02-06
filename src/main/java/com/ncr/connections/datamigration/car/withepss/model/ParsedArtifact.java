package com.ncr.connections.datamigration.car.withepss.model;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Models details for a ParsedArtifact.
 */
public final class ParsedArtifact implements Serializable {

    private static final long serialVersionUID = 1L;

    private final GavCoordinates gav;
    private final String name;
    private final String description;

    /**
     * Instantiates a new parsed artifact.
     * 
     * @param gav
     *            the GAV co-ordinates
     * @param name
     *            the name
     * @param description
     *            the description
     */
    public ParsedArtifact(GavCoordinates gav, String name, String description) {
        this.gav = gav;
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the GAV co-ordinates.
     * 
     * @return the gav
     */
    public GavCoordinates getGav() {
        return gav;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ParsedArtifact) {
            final ParsedArtifact that = (ParsedArtifact) object;
            return Objects.equal(this.getGav(), that.getGav()) && Objects.equal(this.getName(), that.getName())
                    && Objects.equal(this.getDescription(), that.getDescription());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getGav(), this.getName(), this.getDescription());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("gav", this.getGav()).add("name", this.getName())
                .add("description", this.getDescription()).toString();
    }

}
