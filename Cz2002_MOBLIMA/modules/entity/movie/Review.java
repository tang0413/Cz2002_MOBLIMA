package modules.entity.movie;

import modules.entity.BaseEntity;

public class Review extends BaseEntity {
    //TODO include the ratings as well
    private String review;
    private int movieId;
    private int userId;
    public Review(int id, String review, int movieId, int userId) {
        super (id);
        this.review = review;
        this.movieId = movieId;
        this.userId = userId;
    }

    public String getReview() { return review; }

    public int getMovieId() { return movieId; }

    public int getUserId() { return userId; }

}
