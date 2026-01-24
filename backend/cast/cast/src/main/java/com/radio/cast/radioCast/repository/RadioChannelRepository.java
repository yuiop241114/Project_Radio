package com.radio.cast.radioCast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radio.cast.radioCast.entity.RadioChannel;

import lombok.RequiredArgsConstructor;


@Repository
// @RequiredArgsConstructor
public interface RadioChannelRepository extends JpaRepository<RadioChannel, Long>{
}
