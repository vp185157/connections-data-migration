package com.ncr.connections.datamigration.car.withoutepss;

import com.ncr.connections.datamigration.car.withoutepss.model.CarArchive;
import com.ncr.connections.datamigration.car.withoutepss.repository.CarArchiveWithoutEpssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarMigratorWithoutEpssServiceImpl implements CarMigratorWithoutEpssService {

    private final CarArchiveWithoutEpssRepository carArchiveWithoutEpssRepository;

    @Autowired
    public CarMigratorWithoutEpssServiceImpl(
            final CarArchiveWithoutEpssRepository carArchiveWithoutEpssRepository) {
        this.carArchiveWithoutEpssRepository = carArchiveWithoutEpssRepository;
    }

    @Override
    public List<CarArchive> findAllWithoutContent() {
        return carArchiveWithoutEpssRepository.findAllWithoutContent();
    }

    @Override
    @Transactional("connections210xTransactionManager")
    public CarArchive findDistinctById(final CarArchive carArchive) {
        return carArchiveWithoutEpssRepository.findDistinctById(carArchive.getId()).orElseThrow(RuntimeException::new);
    }
}
