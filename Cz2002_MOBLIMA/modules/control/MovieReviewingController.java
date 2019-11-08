package modules.control;

import modules.boundary.ConsoleUI;
import modules.data.DataBase;
import modules.entity.MovieGoner;
import modules.entity.movie.Movie;
import modules.entity.movie.Review;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Represents a type of controller that is able to get new review from the user and store them back to txt file
 */
public class MovieReviewingController extends BaseController implements GeneralEnter {
    //TODO only the users that have watched can do review. take email to find userID, and then find book record, check movie id
    //TODO see if he made a review before
    //enter email to get user, rate, review;
    /**
     * This is the movie that the user is going to comment on
     */
    private Movie movie;
    /**
     * This is the user who is making review
     */
    private MovieGoner user;
    /**
     * This is the newly made review
     */
    private Review newReview;

    /**
     * This is to instantiate a controller that is specifically for recording new reviews from the user
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     * @param mv the specific movie that uer chose to make review on
     */
    public MovieReviewingController(ConsoleUI inheritedConsoleUI, Movie mv) {
        super(inheritedConsoleUI);
        this.movie = mv;
        this.logText = "Please fill in the following information to make review";
    }

    /**
     * This is to enter a series of process to get and store new reviews
     * It includes a sub-process to get the user's name by userId
     */
    public void enter() {
        this.consoleUI.logText(logText);
        while (true){
            String email = this.consoleUI.getEmail();
            ArrayList<MovieGoner> wholeUserList = new ArrayList<>();
            MovieGoner user ;
            try {
                wholeUserList = DataBase.readList(MovieGoner.class);
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
                        try{
                            this.consoleUI.logSuccess();
                        } catch (Exception e){
                            consoleUI.logWarning("Failed to sleep!");
                        }
                        return;
                    } else {
                        this.consoleUI.log("");
                        this.consoleUI.logWarning("Failed to comment! Returning to previous page now...");
                        try{
                            TimeUnit.SECONDS.sleep(2);
                        } catch (Exception e){
                            consoleUI.logWarning("Failed to sleep!");
                        } //TODO can make into another controller
                        return;
                    }
                } else {
                    this.consoleUI.logWarning("No such user found!"); //TODO refine language
                    String choice = this.consoleUI.getStr("Try again?[y/n]");
                    if (!choice.equals("y")){
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("exception");
                consoleUI.logWarning(e.getMessage());
                consoleUI.logWarning("Failed to load the movie reviews!");
                return;
            }
        }
    }

    /**
     * This is to get the reviews from the user which consisting of rating score and comments
     * @return true if the data is stored correctly. false if not.
     */
    private Boolean makeReview() {
        int rating = this.consoleUI.getInt("Please enter your rating (1~5)", 1, 5);
        String review;
        while (true) {
            review = this.consoleUI.getStr("Please enter your review. Press 'Enter' to submit");
            if (review.length() <= 1){
                this.consoleUI.logWarning("Too Short!");
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
            DataBase.setData(this.newReview);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
