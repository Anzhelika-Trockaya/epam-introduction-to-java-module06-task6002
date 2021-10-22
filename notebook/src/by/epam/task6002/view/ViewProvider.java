package by.epam.task6002.view;

public class ViewProvider {
    private static ViewProvider instance = new ViewProvider();

    private final UserActionView userActionView;

    private ViewProvider() {
        userActionView = new UserActionViewImpl();
    }

    public static ViewProvider getInstance() {
        return instance;
    }

    public UserActionView getUserActionView() {
        return userActionView;
    }
}
