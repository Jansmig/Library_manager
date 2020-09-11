package com.project.LibraryManager.repository;

import com.project.LibraryManager.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserReposiotry extends CrudRepository<User, Long> {

    @Override
    User save(User user);

    @Override
    Optional<User> findById(Long userId);
}
