package modules.entity.movie;

import modules.entity.BaseEntity;

import java.util.ArrayList;

public class Review extends BaseEntity {
    //TODO include the ratings as well
    // id=3|rating=5|review=good|movieId=1|userId=3
    private int rating;
    private String review;
    private int movieId;
    private int userId;
    public Review(int id, int rating, String review, int movieId, int userId) {
        super (id);
        this.rating = rating;
        this.review = review;
        this.movieId = movieId;
        this.userId = userId;
    }

    public Review(ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.rating = Integer.parseInt(paramList.get(1));
        this.review = paramList.get(2);
        this.movieId = Integer.parseInt(paramList.get(3));
        this.userId = Integer.parseInt(paramList.get(4));
    }

//    public ArrayList<String> getRatingWithReview() {
//    }

    public int getMovieId() { return movieId; }

    public int getUserId() { return userId; }

    public int getRating() {
        return this.rating;
    }

}
