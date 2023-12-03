package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.Hall;
import com.thjodyt.cinema.data.dao.HallsRepository;
import com.thjodyt.cinema.data.model.HallEntity;
import com.thjodyt.cinema.service.exception.HallNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
public class HallsService {

  private final HallsRepository hallsRepository;

  public Collection<Hall> getAllHalls() {
    return hallsRepository.findAll().stream()
        .map(Mapper::map)
        .collect(Collectors.toList());
  }

  public HallEntity getHallById(long hallId) {
    return hallsRepository.findById(hallId)
        .orElseThrow(() -> new HallNotFoundException(hallId));
  }

  private static class Mapper {

    public static Hall map(HallEntity hallEntity) {
      Hall hall = new Hall();
      hall.setId(hallEntity.getId());
      hall.setSymbol(HtmlUtils.htmlEscape(hallEntity.getSymbol()));
      hall.setRows(hallEntity.getRows());
      hall.setCols(hallEntity.getCols());
      return hall;
    }

  }

}
