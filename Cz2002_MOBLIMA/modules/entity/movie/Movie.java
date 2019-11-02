package modules.entity.movie;

import modules.data.DataBase;
import modules.entity.BaseEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Movie extends BaseEntity implements Comparable{
    private static DecimalFormat df = new DecimalFormat("0.0");
    private String name;
    private String description;
    private String rating;
    private ArrayList<String> castList;
    private ArrayList<String> directorList;
    private ArrayList<String> review = new ArrayList<String>();
    private String status;
    private String type;
    private String cat;

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
        this.status = paramList.get(3);
        this.type = paramList.get(4);
        this.cat = paramList.get(5);
        this.rating = findRatingAndSetReview();
        this.castList = findPeople(CASTFILENAME, Actor.class);
        this.directorList =  findPeople(DIRECTORFILENAME, Director.class);
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

    public String getDirector(){
        String res = String.join(",", this.directorList);
        return res;
    }

    public ArrayList<String> getReview(){
        return this.review;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getCat() {
        return cat;
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
            else {
                if (this.rating.equals("N.A.")){
                    return -1;
                }
                return this.rating.compareTo(s.getRating());
            }
        }
        return 2;
    }

    private ArrayList<String> findPeople(String fileName, Class<? extends MoviePeople> classObj){
        ArrayList<String> peopleList = new ArrayList<String>();
        try{
            ArrayList<MoviePeople> wholeMoviePeopleList = DataBase.readList(fileName, classObj);
            for (MoviePeople a: wholeMoviePeopleList){
                if (a.getInMovie().contains(Integer.toString(this.id))){
                    peopleList.add(a.getName());
                }
            }
        } catch(Exception e){
        }
        return peopleList;
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

    public void setId(int id){
        this.id = id;
        try{
            DataBase.setData(MOVIEFILENAME, this);
        } catch (Exception e){
        }    //TODO should have setID. this one for test only, need to be deleted
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|name=" + this.name + "|description=" + this.description + "|status=" + this.status + "|type=" + this.type + "|cat=" + this.cat;
        //TODO make it a standard method
    }
}
