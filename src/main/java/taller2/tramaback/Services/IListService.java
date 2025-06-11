package taller2.tramaback.Services;

import taller2.tramaback.DTOs.ListDTO;
import taller2.tramaback.DTOs.OpenListDTO;
import taller2.tramaback.Models.List;

public interface IListService {
    List createList(ListDTO dto);
    void deleteList(Long listId);
    void addMovieToList(ListDTO dto);
    void removeMovieFromList(ListDTO dto);
    OpenListDTO openList(Long listId);
    void addFavoriteMovie(Long userId, Long movieId);
    java.util.List<ListDTO> getListsByUserId(Long userId);
}