package com.ncr.connections.datamigration.car.withepss.repository;

import com.ncr.connections.datamigration.car.withepss.model.CarArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CarArchiveWithEpssRepository extends JpaRepository<CarArchive, Long> {

    /**
     * Returns entity with the given ID.
     *
     * @param id
     * @return
     */
    Optional<CarArchive> findDistinctById(Long id);

    CarArchive findByGroupIdAndArtifactIdAndVersion(final String groupId, final String artifactId,
                                                    final String version);

    /**
     * The @Query based method causes error messages on WebSphere when it is called by a scheduled job
     * The @Transactional(readOnly = true) is needed to let Spring and WebSphere handle the connection properly
     *
     * @return all CarArchive without the content
     */
    @Transactional(readOnly = true)
    @Query("SELECT new CarArchive(c.id, c.groupId, c.artifactId, c.version, c.hash, c.name, c.description, c.signedStatus) FROM CarArchive c")
    List<CarArchive> findAllWithoutContent();

    /**
     * The @Query based method causes error messages on WebSphere when it is called by a scheduled job
     * The @Transactional(readOnly = true) is needed to let Spring and WebSphere handle the connection properly
     *
     * @param pageable is a pageable object
     * @return all CarArchive based on the pageable object without the content
     */
    @Transactional(readOnly = true)
    @Query("SELECT new CarArchive(c.id, c.groupId, c.artifactId, c.version, c.hash, c.name, c.description, c.signedStatus) FROM CarArchive c")
    Page<CarArchive> findAllWithoutContent(Pageable pageable);
}
