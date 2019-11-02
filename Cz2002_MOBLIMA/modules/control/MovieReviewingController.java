package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Admin;
import modules.entity.MovieGoner;
import modules.entity.movie.Movie;
import modules.entity.movie.Review;
import root.App;
import root.RunApp;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MovieReviewingController extends BaseController{
    //TODO only the users that have watched can do review. take email to find userID, and then find book record, check movie id
    //TODO see if he made a review before
    //enter email to get user, rate, review;
    private Movie movie;
    private MovieGoner user;
    private Review newReview;

    public MovieReviewingController(Console inheritedConsole, Movie mv) {
        super(inheritedConsole);
        this.movie = mv;
        this.logText = "Please fill in the following information to make review";
    }

    @Override
    public void enter() {
        this.console.logText(logText);
        while (true){
            String email = this.console.getEmail();
            ArrayList<MovieGoner> wholeUserList = new ArrayList<>();
            MovieGoner user ;
            try {
                wholeUserList = DataBase.readList(MOVIEGONERFILENAME, MovieGoner.class);
            } catch (Exception e) {
                System.out.println("exception");
            }
            Boolean hasThisUser = false;
            if (wholeUserList.size() >= 1){
                for (MovieGoner go: wholeUserList){
                    if (go.getEmail().equals(email)){
                        this.user = go;
                        hasThisUser = true;
                        break;
                    }
                }
            }
            if (hasThisUser){
                if(makeReview()){
//                    AppRestartController restart = new AppRestartController(this.console);
//                    restart.enter();
                    this.console.logReminder("Updated successfully! Returning to the previous page...");
                    try{
                        TimeUnit.SECONDS.sleep((long)2.5);
                    } catch (Exception e){
                    }
                    return;
                } else {
                    this.console.log("");
                    this.console.logWarning("Failed to comment! Returning to previous page now...");
                    try{
                        TimeUnit.SECONDS.sleep((long)2.5);
                    } catch (Exception e){
                    } //TODO can make into another controller
                    return;
                }
            } else {
                this.console.logWarning("No such user found!"); //TODO refine language
                String choice = this.console.getStr("Try again?[y/n]");
                if (!choice.equals("y")){
                    return;
                }
            }
        }
    }

    private Boolean makeReview() {
        int rating = this.console.getInt("Please enter your rating (1~5)", 1, 5);
        String review;
        while (true) {
            review = this.console.getStr("Please enter your review. Press 'Enter' to submit");
            if (review.length() <= 1){
                this.console.logWarning("Too Short!");
            } else {
                break;
            }
        }
        //TODO recover all normal constructor for entities. passing string only is stupid.
        ArrayList<String> reviewData = new ArrayList<>();
        reviewData.add(Integer.toString(DataBase.getNewId(Review.class)));
        reviewData.add(Integer.toString(rating));
        reviewData.add(review);
        reviewData.add(Integer.toString(this.movie.getId()));
        reviewData.add(Integer.toString(this.user.getId()));
        this.newReview = new Review(reviewData);
        try{
            DataBase.setData(REVIEWFILENAME, this.newReview);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
