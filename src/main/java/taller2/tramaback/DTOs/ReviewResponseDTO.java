package taller2.tramaback.DTOs;

import java.time.OffsetDateTime;
import java.util.Date;

public class ReviewResponseDTO {
    private Long id; // ID de la Review
    private Long userId;
    private String userName;
    private Long movieId;
    private String movieTitle;
    private String movieDirector;
    private Date movieReleaseDate;
    private String content;
    private Integer rating;
    private OffsetDateTime publishedDate;
    private String imageUrl;
    private OffsetDateTime updatedAt;

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public Date getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getContent() {
        return content;
    }

    public Integer getRating() {
        return rating;
    }

    public OffsetDateTime getPublishedDate() {
        return publishedDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public void setMovieReleaseDate(Date movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setPublishedDate(OffsetDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}