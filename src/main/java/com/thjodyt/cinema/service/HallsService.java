package com.thjodyt.cinema.service;

import com.thjodyt.cinema.data.HallDTO;
import com.thjodyt.cinema.data.dao.HallsRepository;
import com.thjodyt.cinema.data.model.Hall;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HallsService {

  private final HallsRepository hallsRepository;

  public Collection<HallDTO> getAllHalls() {
    return hallsRepository.findAll().stream()
        .map(Mapper::map)
        .collect(Collectors.toList());
  }

  public Hall getHallById(long hallId) {
    return hallsRepository.findById(hallId)
        .orElseThrow(() -> new HallNotFoundException(hallId));
  }

  private static class Mapper {

    public static HallDTO map(Hall hall) {
      HallDTO hallDTO = new HallDTO();
      hallDTO.setId(hall.getId());
      hallDTO.setSymbol(hall.getSymbol());
      hallDTO.setRows(hall.getRows());
      hallDTO.setCols(hall.getCols());
      return hallDTO;
    }

  }

}
