package modules.entity.movie;

import modules.data.DataBase;
import modules.entity.BaseEntity;
import modules.entity.MovieGoner;

import java.util.ArrayList;

public class Review extends BaseEntity {
    //id=3|rating=5|review=good|movieId=2|userId=3
    private static ArrayList<MovieGoner> wholeUserList; //TODO in other similar place, do it in this way;
    private int rating;
    private String review;
    private int movieId;
    private int userId;
    private String userName;

//    public Review(int id, int rating, String review, int movieId, int userId) {
//        super (id);
//        this.rating = rating;
//        this.review = review;
//        this.movieId = movieId;
//        this.userId = userId;
//    }

    public Review(ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.rating = Integer.parseInt(paramList.get(1));
        this.review = paramList.get(2);
        this.movieId = Integer.parseInt(paramList.get(3));
        this.userId = Integer.parseInt(paramList.get(4));
        this.userName = getReviewer();
    }

    private String getReviewer(){
        try{
            wholeUserList= DataBase.readList(MOVIEGONERFILENAME, MovieGoner.class);
            for (MovieGoner a: wholeUserList){
                if (a.getId() == this.userId){
                    return a.getName();
                }
            }
        } catch(Exception e){
        }
        return "";
    }

//    public ArrayList<String> getRatingWithReview() {
//    }

    public int getMovieId() { return movieId; }

    public int getUserId() { return userId; }

    public int getRating() {
        return this.rating;
    }

    public String getReview() {
        String complexRecord = "username: " + this.userName + "|" + "rating: " + this.rating + "|" + "review: " + this.review;
        return complexRecord;
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|rating=" + this.rating + "|review=" + this.review + "|movieId=" + this.movieId + "|userId=" + this.userId;
    }
}
