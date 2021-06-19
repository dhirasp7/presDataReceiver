package com.asprsys.simulator.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asprsys.simulator.model.PresSensor;

public interface PresSensorRepository extends JpaRepository<PresSensor, Long> {

}
