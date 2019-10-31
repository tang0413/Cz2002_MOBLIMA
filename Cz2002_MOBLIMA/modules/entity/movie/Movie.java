package modules.entity.movie;

import modules.data.DataBase;
import modules.entity.BaseEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Movie extends BaseEntity implements Comparable{
    private static final String CASTFILENAME = "ActorList.txt";
    private static final String REVIEWFILENAME = "ReviewList.txt";
    private static DecimalFormat df = new DecimalFormat("0.0");
    private String name;
    private String description;
    private String rating;
    private ArrayList<String> castList;
    private ArrayList<String> review = new ArrayList<String>();
//    public Movie(int id, String name, String description, Double totalScore, int numOfPeople ) {
//        super(id);
//        this.name = name;
//        this.description = description;
//        this.totalScore = totalScore;
//        this.numOfPeople = numOfPeople;
//        this.rating = df.format(totalScore/numOfPeople);
//        this.castList = findCast();
//    }

    public Movie(ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.name = paramList.get(1);
        this.description = paramList.get(2);
        this.rating = findRatingAndSetReview();
        this.castList = findCast();
    }

    //TODO: add set, add dependency to cast and directors.....
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getCast(){
        String res = String.join(",", this.castList);
        return res;
    }

    public ArrayList<String> getReview(){
        return this.review;
    }

    public boolean equals(Object o) {
        if(o.getClass() == this.getClass()) {
            Movie s = (Movie)o;
            return (this.rating.equals(s.getRating()) && this.name.equals(s.getName()));
        }
        return false;
    }
    public int compareTo(Object o) {
        if(o.getClass() == this.getClass()) {
            Movie s = (Movie)o;
            if(this.rating.equals(s.getRating()))
                return this.name.compareTo(s.getName());
            else
                return this.rating.compareTo(s.getRating());
        }
        return 2;
    }

    private ArrayList<String> findCast(){
        ArrayList<String> castList = new ArrayList<String>();
        try{
            ArrayList<Actor> wholeActorList = DataBase.readList(CASTFILENAME, Actor.class);
            for (Actor a: wholeActorList){
                if (a.getInMovie().contains(Integer.toString(this.id))){
                    castList.add(a.getName());
                }
            }
        } catch(Exception e){
        }
        return castList;
    }

    private String findRatingAndSetReview(){
        Double totalScore = 0.0;
        int numberOfPeople = 0;
        try{
            ArrayList<Review> wholeReviewList = DataBase.readList(REVIEWFILENAME, Review.class);
            for (Review a: wholeReviewList){
                if (a.getMovieId() == this.id){
                    this.review.add(a.getReview());
                    totalScore += Double.valueOf(a.getRating());
                    numberOfPeople ++;
                }
            }
        } catch(Exception e){
        }
        if (numberOfPeople > 1){
            return df.format(totalScore/numberOfPeople);
        } else {
            return "N.A.";
        }
    }

}
