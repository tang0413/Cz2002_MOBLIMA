package modules.entity;

import modules.control.BaseController;
import modules.data.DataBase;
import modules.entity.movie.Movie;
import modules.entity.Cineplex;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class ShowTime extends BaseEntity {

    private int movieId;
    private int cineId;
    private String cineplex;
    private String time;
    private String date;
    private int id;
    private int mId;
    private int cId;
    private ArrayList<Integer> showTimeList;
    private static final String SHOWFILENAME = "ShowList.txt";

    public int MovieExtract(ArrayList<Movie> ms)
    {
        for(Movie m : ms) {
            mId = m.getId();
        }
        return mId;
    }

    public int CineExtract(String cs){
        Cineplex c = new Cineplex(cId,cineName);
        return cId;
    }

    public ShowTime(ArrayList<String> showList) {
        super(Integer.parseInt(showList.get(1)));
        this.movieId = Integer.parseInt(showList.get(2));
        this.cineId = Integer.parseInt(showList.get(3));
        this.time = showList.get(4);
        this.date = showList.get(5);
    }

    public int getShowTime(ArrayList<Integer> a){
        try{
            ArrayList<ShowTime> allShowTime = DataBase.readList(SHOWFILENAME,ShowTime.class);
            for(ShowTime st: allShowTime){
                if(st.movieId == this.mId && st.cineId == this.cId)
                {
                    a.add(st.id);
                }
            }
            return a;
        } catch (Exception e){

        }
    }
}
