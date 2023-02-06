package com.ncr.connections.datamigration.car.withoutepss;

import com.ncr.connections.datamigration.car.withoutepss.model.CarArchive;

import java.util.List;

public interface CarMigratorWithoutEpssService {

    List<CarArchive> findAllWithoutContent();

    CarArchive findDistinctById(CarArchive carArchive);
}
