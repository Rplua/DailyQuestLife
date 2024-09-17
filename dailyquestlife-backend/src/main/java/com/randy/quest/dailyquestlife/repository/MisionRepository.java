package com.randy.quest.dailyquestlife.repository;

import com.randy.quest.dailyquestlife.model.Mision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MisionRepository extends JpaRepository<Mision, Integer> {

}
