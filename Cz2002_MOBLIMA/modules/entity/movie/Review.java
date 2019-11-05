package modules.entity.movie;

import modules.data.DataBase;
import modules.entity.BaseEntity;
import modules.entity.MovieGoner;

import java.util.ArrayList;

/**
 * Represents a Review by a movie-goner that contains rating and comments
 * A Review is specific to one movie
 */
public class Review extends BaseEntity {
    //id=3|rating=5|review=good|movieId=2|userId=3
    /**
     * This is the whole movie-goner list so that Reviews can get names and emails by userId
     */
    private static ArrayList<MovieGoner> wholeUserList; //TODO in other similar place, do it in this way;
    /**
     * This is an integer value ranging from 1 to 5. 5 means the bast
     */
    private int rating;
    /**
     * This is the user's comment
     */
    private String review;
    /**
     * This is the Id of the movie that the user rated on
     */
    private int movieId;
    /**
     * This is the Id of the user who did the rating
     */
    private int userId;
    /**
     * This is the name of the user. It's got from the wholeUserList
     */
    private String userName;

//    public Review(int id, int rating, String review, int movieId, int userId) {
//        super (id);
//        this.rating = rating;
//        this.review = review;
//        this.movieId = movieId;
//        this.userId = userId;
//    }

    /**
     * Constructor used by DataBase to instantiate using txt file info
     * It calls getReviewer() to get the userName attribute by userId
     * @param paramList ArrayList of String with contains the id, rating, review, movieId, and userId in sequence
     */
    public Review(ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.rating = Integer.parseInt(paramList.get(1));
        this.review = paramList.get(2);
        this.movieId = Integer.parseInt(paramList.get(3));
        this.userId = Integer.parseInt(paramList.get(4));
        this.userName = getReviewer();
    }

    /**
     * Load the whole Movie-goner info from txt using database and get the user's name by userId
     * @return the name of the user who did rating
     */
    private String getReviewer(){
        try{
            wholeUserList= DataBase.readList(MovieGoner.class);
            for (MovieGoner a: wholeUserList){
                if (a.getId() == this.userId){
                    return a.getName();
                }
            }
        } catch(Exception e){
            System.out.println("Failed to load the user data!");
        }
        return "";
    }

//    public ArrayList<String> getRatingWithReview() {
//    }

    /**
     * @return the movie Id
     */
    public int getMovieId() { return movieId; }

    /**
     * @return the user ID
     */
    public int getUserId() { return userId; }

    /**
     * @return the rating (1-5)
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * This is by ListMovieReviewController to display the review information in block
     * @return A string contains the username, rating and comment information, with '|' as separator
     */
    public String getReview() {
        String complexRecord = "username: " + this.userName + "|" + "rating: " + this.rating + "|" + "review: " + this.review;
        return complexRecord;
    }

    /**
     * This is used in DataBase when setData/deleteData is called
     * @return the Stringlized properties to store back to txt file. e.g. id=3|rating=5|review=good|movieId=2|userId=3
     */
    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|rating=" + this.rating + "|review=" + this.review + "|movieId=" + this.movieId + "|userId=" + this.userId;
    }
}
