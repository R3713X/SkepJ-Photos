package other;

import gui.MainWindowController;

/**
 * Stores access references to all the View GuiControllers
 */
public class GuiControllers
{
    private static MainWindowController mainWindowController;

    public static void setMainController(MainWindowController mainWindowController) {
        GuiControllers.mainWindowController = mainWindowController;
    }

    public static MainWindowController getMainController() {
        return mainWindowController;
    }

}