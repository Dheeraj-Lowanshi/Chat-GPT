package com.votezylite.repository;

import com.votezylite.entity.Election;
import com.votezylite.enums.ElectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    List<Election> findByStatus(ElectionStatus status);
    List<Election> findByEndDateTimeBeforeAndStatusNot(LocalDateTime time, ElectionStatus status);
}
