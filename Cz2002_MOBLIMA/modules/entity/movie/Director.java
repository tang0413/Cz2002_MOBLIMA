package modules.entity.movie;

import java.util.ArrayList;

/**
 * Represents a specific type of MoviePeople: Director. Designed for future use.
 * In the future, may include more information such as specific role: Director of Photography or Voice
 */
public class Director extends MoviePeople {

    public Director(ArrayList<String> paramList) {
        super(paramList);
    }

    @Override
    public int getId() {
        return super.getId();
    }
}
