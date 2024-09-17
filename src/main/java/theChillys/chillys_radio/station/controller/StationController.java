package theChillys.chillys_radio.station.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import theChillys.chillys_radio.data.dto.ModifyResponseDto;
import theChillys.chillys_radio.exception.StationNotFoundException;
import theChillys.chillys_radio.station.dto.StationResponseDto;
import theChillys.chillys_radio.station.dto.StationUrlDto;
import theChillys.chillys_radio.station.entity.Station;
import theChillys.chillys_radio.station.service.IStationService;
import theChillys.chillys_radio.user.dto.UserResponseDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StationController {

    @Autowired
    private final IStationService service;

    @GetMapping("/stations")
    public ResponseEntity<List<StationResponseDto>> getAllStations() {
        return ResponseEntity.ok(service.getAllStations());
    }


    @GetMapping("/stations/top-clicks")
    public ResponseEntity<List<StationResponseDto>> getTopClickStations() {
        return ResponseEntity.ok(service.getAllStationsByTopClicks());
    }

    @GetMapping("/stations/top-votes")
    public ResponseEntity<List<StationResponseDto>> getTopVoteStations() {
        return ResponseEntity.ok(service.getAllStationsByTopVotes());
    }

    @GetMapping("/stations/{id}")
    public ResponseEntity<StationResponseDto> getStationById(@PathVariable("id") String stationuuid) {
        return ResponseEntity.ok(service.getStationByStationuuid(stationuuid));
    }
    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<String> handleStationNotFound(StationNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/stations/{id}/stream")
    public StationUrlDto getStreamUrl (@PathVariable String stationuuid){
        return service.getStreamUrl(stationuuid);
    }

    @PostMapping("/stations/{id}/vote")
    public Mono<ModifyResponseDto> vote(@PathVariable (name = "id") String stationuuid) {
        return service.vote(stationuuid); //Spring WebFlux сам обработает Mono и вернет результат клиенту асинхронно.
    }

  //  @GetMapping("/stations")
  //  public List<UserResponseDto> findStationsByGenreCountryLanguage (@RequestParam(value = "genre", required = false) String genre,
  //  @RequestParam(value = "country", required = false) String country,
 //   @RequestParam(value = "language", required = false) String language){
 //       List<StationResponseDto> stations = service.findStationsByGenreCountryLanguage( genre,country,language);
 //       return findStationsByGenreCountryLanguage(genre, country, language);

  //  }

}

