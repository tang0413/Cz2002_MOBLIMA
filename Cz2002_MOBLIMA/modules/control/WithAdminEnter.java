package modules.control;

/**
 * This is to be implemented by controllers that performs differently for admin and common user
 */
public interface WithAdminEnter {
    public void enter(Boolean isAdmin);
}
