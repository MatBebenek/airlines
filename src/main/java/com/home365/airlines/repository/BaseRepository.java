package com.home365.airlines.repository;

import com.home365.airlines.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

    default T getEntity(Long entityId) {
        return this.findById(entityId)
                .orElseThrow(() -> new ResourceNotFoundException("Entity", "id", entityId));
    }
}
