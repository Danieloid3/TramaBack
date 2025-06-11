package taller2.tramaback.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taller2.tramaback.DTOs.ListDTO;
import taller2.tramaback.DTOs.OpenListDTO;
import taller2.tramaback.Models.List;
import taller2.tramaback.Services.IListService;

@RestController
@RequestMapping("trama/lists")
@CrossOrigin(value = "http://localhost:3000")
public class ListController {
    @Autowired
    private IListService listService;

    @PostMapping("/create")
    public ListDTO createList(@RequestBody ListDTO dto) {
        List list = listService.createList(dto);
        return new ListDTO(
                list.getId(),
                list.getName(),
                list.getDescription(),
                list.getUser().getId()
        );
    }
    @DeleteMapping("/remove-list/{id}")
    public void deleteList(@PathVariable Long id) {
        listService.deleteList(id);
    }

    @PostMapping("/add-movie")
    public void addMovieToList(@RequestBody ListDTO dto) {
        listService.addMovieToList(dto);
    }

    @PostMapping("/remove-movie")
    public void removeMovieFromList(@RequestBody ListDTO dto) {
        listService.removeMovieFromList(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpenListDTO> openList(@PathVariable Long id) {
        OpenListDTO dto = listService.openList(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/favorites/add")
    public ResponseEntity<Void> addMovieToFavorites(@RequestBody ListDTO dto) {
        if (dto.getUserId() == null || dto.getMovieId() == null) {
            return ResponseEntity.badRequest().build();
        }
        listService.addFavoriteMovie(dto.getUserId(), dto.getMovieId());
        return ResponseEntity.ok().build();
    }
}