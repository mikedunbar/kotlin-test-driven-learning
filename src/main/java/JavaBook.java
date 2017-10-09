/**
 * @author Mike Dunbar
 */
public class JavaBook {
    private String title;
    private boolean isAvailableOnSafari;
    private String stringWithoutAccessor = "private stuff";

    public JavaBook(String title, boolean isAvailableOnSafari) {
        this.title = title;
        this.isAvailableOnSafari = isAvailableOnSafari;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailableOnSafari() {
        return isAvailableOnSafari;
    }

    public void setAvailableOnSafari(boolean availableOnSafari) {
        isAvailableOnSafari = availableOnSafari;
    }
}