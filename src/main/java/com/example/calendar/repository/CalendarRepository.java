package com.example.calendar.repository;

import com.example.calendar.entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository <CalendarEntity, Long> {
    Optional<CalendarEntity> findByYear(int year);
}
