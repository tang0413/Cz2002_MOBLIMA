package modules.control;

/**
 * This is to be implemented by controllers that performs differently for admin and common user
 */
public interface withAdminEnter {
    public void enter(Boolean isAdmin);
}
