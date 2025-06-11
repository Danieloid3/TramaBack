package taller2.tramaback.DTOs;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class ReviewResponseDTO {
    private Long userId;
    private String userName;
    private Long movieId;
    private String movieTitle;
    private String movieDirector;
    private LocalDate movieReleaseDate;
    private String content;
    private Double rating;
    private LocalDate publishedDate;
    private String imageUrl;
    private OffsetDateTime updatedAt;

    // Getters y setters

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public String getMovieDirector() { return movieDirector; }
    public void setMovieDirector(String movieDirector) { this.movieDirector = movieDirector; }

    public LocalDate getMovieReleaseDate() { return movieReleaseDate; }
    public void setMovieReleaseDate(LocalDate movieReleaseDate) { this.movieReleaseDate = movieReleaseDate; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public LocalDate getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
