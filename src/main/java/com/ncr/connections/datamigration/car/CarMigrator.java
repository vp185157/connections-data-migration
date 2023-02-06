package com.ncr.connections.datamigration.car;

import com.ncr.connections.datamigration.DataMigrator;
import com.ncr.connections.datamigration.MigrationResult;
import com.ncr.connections.datamigration.car.withepss.repository.CarArchiveWithEpssRepository;
import com.ncr.connections.datamigration.car.withoutepss.CarMigratorWithoutEpssService;
import com.ncr.connections.datamigration.car.withoutepss.model.CarArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarMigrator implements DataMigrator {

    private static final String CAR_ARCHIVE = "CAR Archive";

    private final CarArchiveWithEpssRepository carArchiveWithEpssRepository;

    private final CarMigratorWithoutEpssService carMigratorWithoutEpssService;

    Logger logger = LoggerFactory.getLogger(CarMigrator.class);

    @Autowired
    public CarMigrator(
            final CarArchiveWithEpssRepository carArchiveWithEpssRepository,
            final CarMigratorWithoutEpssService carMigratorWithoutEpssService) {
        this.carArchiveWithEpssRepository = carArchiveWithEpssRepository;
        this.carMigratorWithoutEpssService = carMigratorWithoutEpssService;
    }

    @Override
    public String getDataName() {
        return CAR_ARCHIVE;
    }

    @Override
    @Transactional("connections3xTransactionManager")
    public MigrationResult migrate() {
        List<CarArchive> carArchivesWithoutEpss = carMigratorWithoutEpssService.findAllWithoutContent();
        MigrationResult migrationResult = new MigrationResult();
        try {
            carArchivesWithoutEpss.forEach(carArchive -> {
                        CarArchive carArchiveFromDB =
                                carMigratorWithoutEpssService.findDistinctById(carArchive);

                        com.ncr.connections.datamigration.car.withepss.model.CarArchive carArchiveWithEpss =
                                new com.ncr.connections.datamigration.car.withepss.model.CarArchive();

                        carArchiveWithEpss.setId(carArchiveFromDB.getId());
                        carArchiveWithEpss.setGroupId(carArchiveFromDB.getGroupId());
                        carArchiveWithEpss.setArtifactId(carArchiveFromDB.getArtifactId());
                        carArchiveWithEpss.setVersion(carArchiveFromDB.getVersion());
                        carArchiveWithEpss.setContent(carArchiveFromDB.getContent());
                        carArchiveWithEpss.setName(carArchiveFromDB.getName());
                        carArchiveWithEpss.setDescription(carArchiveFromDB.getDescription());
                        carArchiveWithEpss.setSignedStatus(carArchiveFromDB.getSignedStatus());
                        carArchiveWithEpssRepository.save(carArchiveWithEpss);
                    }
            );
            migrationResult.setResult(true);
            migrationResult.setName(CAR_ARCHIVE);
        } catch (Exception exception) {
            logger.error("Exception in Car Migration {}", exception);
            migrationResult.setResult(false);
            migrationResult.setName(CAR_ARCHIVE);
        }
        return migrationResult;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
