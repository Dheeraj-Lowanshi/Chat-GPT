package com.votezylite.repository;

import com.votezylite.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {
    Optional<Party> findByNameIgnoreCase(String name);
}
