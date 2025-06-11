package taller2.tramaback.DTOs;

public class ListDTO {
    private Long userId;
    private String name;
    private String description;
    private Boolean isFavorites;
    private Long listId;
    private Long movieId;

    // Getters y setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getIsFavorites() { return isFavorites; }
    public void setIsFavorites(Boolean isFavorites) { this.isFavorites = isFavorites; }
    public Long getListId() { return listId; }
    public void setListId(Long listId) { this.listId = listId; }
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public ListDTO(Long listId, String name, String description, Long userId) {
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.userId = userId;
    }
}