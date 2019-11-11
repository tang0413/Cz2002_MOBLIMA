package modules.control;

/**
 * This is to be implemented by controllers that performs differently for admin and common user
 */
public interface WithAdminEnter {
    /**
     * This is to enter different series of actions according to the request type: admin or common user
     * @param isAdmin true if it is an admin requrest
     */
    public void enter(Boolean isAdmin);
}
