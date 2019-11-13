package modules.entity.movie;

import java.util.ArrayList;

/**
 * Represents a specific type of MoviePeople: Actor or Actress. Designed for future use.
 * In the future, may include more information such as SAG-AFTRA member ID
 */
public class Actor extends MoviePeople {

    public Actor(ArrayList<String> paramList) {
        super(paramList);
    }

    @Override
    public int getId() {
        return super.getId();
    }
}
