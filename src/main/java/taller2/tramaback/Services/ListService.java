package taller2.tramaback.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taller2.tramaback.DTOs.ListDTO;
import taller2.tramaback.DTOs.OpenListDTO;
import taller2.tramaback.Models.List;
import taller2.tramaback.Models.User;
import taller2.tramaback.Repositories.ListRepository;
import taller2.tramaback.Repositories.UserRepository;
import taller2.tramaback.Models.Movie;
import taller2.tramaback.Repositories.MovieRepository;
import taller2.tramaback.Models.ListMovie;
import taller2.tramaback.Models.ListMovieId;
import taller2.tramaback.Repositories.ListMovieRepository;
import taller2.tramaback.DTOs.OpenListDTO;
import taller2.tramaback.DTOs.MovieSummaryDTO;
import java.util.stream.Collectors;
import taller2.tramaback.Models.Movie;


import java.util.stream.Collectors;


@Service
public class ListService implements IListService {
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ListMovieRepository listMovieRepository;

    @Override
    public List createList(ListDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        if (user == null) return null;
        List list = new List();
        list.setUser(user);
        list.setName(dto.getName());
        list.setDescription(dto.getDescription());
        list.setIsFavorites(false); // Siempre false para listas creadas por el usuario
        return listRepository.save(list);
    }

    @Override
    public void deleteList(Long listId) {
        listRepository.deleteById(listId);
    }

    @Override
    public void addMovieToList(ListDTO dto) {
        List list = listRepository.findById(dto.getListId()).orElse(null);
        Movie movie = movieRepository.findById(dto.getMovieId()).orElse(null);
        if (list != null && movie != null) {
            ListMovie listMovie = new ListMovie();
            ListMovieId id = new ListMovieId();
            id.setListId(list.getId());
            id.setMovieId(movie.getId());
            listMovie.setId(id);
            listMovie.setList(list);
            listMovie.setMovie(movie);
            listMovieRepository.save(listMovie);
        }
    }

    @Override
    public void removeMovieFromList(ListDTO dto) {
        ListMovieId id = new ListMovieId();
        id.setListId(dto.getListId());
        id.setMovieId(dto.getMovieId());
        listMovieRepository.deleteById(id);
    }

    @Override
    public OpenListDTO openList(Long listId) {
        List list = listRepository.findById(listId).orElse(null);
        if (list == null) return null;

        java.util.List<MovieSummaryDTO> movies = listMovieRepository.findAll().stream()
                .filter(lm -> lm.getList().getId().equals(listId))
                .map(lm -> {
                    Movie m = lm.getMovie();
                    MovieSummaryDTO dto = new MovieSummaryDTO();
                    dto.setId(m.getId());
                    dto.setTitle(m.getTitle());
                    dto.setPosterUrl(m.getPosterUrl());
                    // Agrega más campos si es necesario
                    return dto;
                })
                .collect(Collectors.toList());

        OpenListDTO dto = new OpenListDTO();
        dto.setListId(list.getId());
        dto.setName(list.getName());
        dto.setDescription(list.getDescription());
        dto.setUserId(list.getUser().getId());
        dto.setMovies(movies);
        return dto;
    }
    @Override
    public void addFavoriteMovie(Long userId, Long movieId) {
        // Buscar la lista Favoritas del usuario
        List favoritesList = listRepository.findByUserIdAndIsFavoritesTrue(userId)
                .orElseGet(() -> {
                    // Si no existe, crearla
                    List newList = new List();
                    newList.setUser(userRepository.findById(userId).orElseThrow());
                    newList.setName("Favoritas");
                    newList.setDescription("Películas favoritas");
                    newList.setIsFavorites(true);
                    return listRepository.save(newList);
                });

        // Agregar la película a la lista Favoritas
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        if (!listMovieRepository.existsByListAndMovie(favoritesList, movie)) {
            ListMovie listMovie = new ListMovie();
            ListMovieId id = new ListMovieId();
            id.setListId(favoritesList.getId());
            id.setMovieId(movie.getId());
            listMovie.setId(id);
            listMovie.setList(favoritesList);
            listMovie.setMovie(movie);
            listMovieRepository.save(listMovie);
        }
    }

}