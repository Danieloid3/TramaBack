// src/main/java/taller2/tramaback/DTOs/OpenListDTO.java
package taller2.tramaback.DTOs;

import java.util.List;

public class OpenListDTO {
    private Long listId;
    private String name;
    private String description;
    private Long userId;
    private List<MovieSummaryDTO> movies;

    // Getters y setters
    public Long getListId() { return listId; }
    public void setListId(Long listId) { this.listId = listId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public List<MovieSummaryDTO> getMovies() { return movies; }
    public void setMovies(List<MovieSummaryDTO> movies) { this.movies = movies; }
}