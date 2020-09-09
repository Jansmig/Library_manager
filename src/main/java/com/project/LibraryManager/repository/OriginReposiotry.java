package com.project.LibraryManager.repository;

import com.project.LibraryManager.domain.Origin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OriginReposiotry extends CrudRepository<Origin, Long> {

    @Override
    Origin save(Origin origin);

    @Override
    Optional<Origin> findById(Long id);

    @Override
    List<Origin> findAll();

    List<Origin> findAllByTitleContainingIgnoreCase(String title);


}
