package other;

import gui.MainWindowController;
import gui.ShowPhotoLocationController;

/**
 * Stores access references to all the View GuiControllers
 */
public class GuiControllers
{
    private static MainWindowController mainWindowController;
    private static ShowPhotoLocationController showPhotoLocationController;

    public static void setMainController(MainWindowController mainWindowController) {
        GuiControllers.mainWindowController = mainWindowController;
    }

    public static MainWindowController getMainController() {
        return mainWindowController;
    }

    public static void setMainController(ShowPhotoLocationController showPhotoLocationController) {
        GuiControllers.showPhotoLocationController = showPhotoLocationController;
    }

    public static ShowPhotoLocationController getShowPhotoLocationController() {
        return showPhotoLocationController;
    }

}