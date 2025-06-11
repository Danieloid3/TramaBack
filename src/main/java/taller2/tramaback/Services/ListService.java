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
import taller2.tramaback.DTOs.MovieSummaryDTO;

import java.util.Collections;
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
                    MovieSummaryDTO movieDto = new MovieSummaryDTO();
                    movieDto.setId(m.getId());
                    movieDto.setTitle(m.getTitle());
                    movieDto.setPosterUrl(m.getPosterUrl());
                    // Agrega más campos si es necesario
                    return movieDto;
                })
                .collect(Collectors.toList());

        OpenListDTO openListDto = new OpenListDTO();
        openListDto.setListId(list.getId());
        openListDto.setName(list.getName());
        openListDto.setDescription(list.getDescription());
        openListDto.setUserId(list.getUser().getId());
        openListDto.setMovies(movies);
        return openListDto;
    }
    @Override
    public void addFavoriteMovie(Long userId, Long movieId) {
        // Buscar la lista Favoritas del usuario, crear si no existe
        List favoritesList = listRepository.findByUserIdAndIsFavoritesTrue(userId)
                .orElseGet(() -> {
                    List newList = new List();
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
                    newList.setUser(user);
                    newList.setName("Favoritas");
                    newList.setDescription("Películas favoritas");
                    newList.setIsFavorites(true);
                    return listRepository.save(newList);
                });

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        if (listMovieRepository.existsByListAndMovie(favoritesList, movie)) {
            // Si ya está, eliminar (toggle off)
            ListMovieId id = new ListMovieId();
            id.setListId(favoritesList.getId());
            id.setMovieId(movie.getId());
            listMovieRepository.deleteById(id);
        } else {
            // Si no está, agregar (toggle on)
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

    @Override
    public java.util.List<ListDTO> getListsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            // Considera lanzar una excepción UserNotFoundException o devolver una lista vacía
            // dependiendo de cómo quieras manejar este caso en el controlador.
            return Collections.emptyList();
        }
        return listRepository.findByUser(user).stream()
                .map(list -> new ListDTO(
                        list.getId(),
                        list.getName(),
                        list.getDescription(),
                        list.getUser().getId()
                        // Asegúrate de que ListDTO no espera movieId aquí,
                        // ya que estas son listas generales, no ítems de lista.
                ))
                .collect(Collectors.toList());
    }
}