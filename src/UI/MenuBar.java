package UI;

import Events.EventManager;
import UI.Events.ViewChangeEvent;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * The main menu bar of the GUI.
 * It offers menus with different options depending on the view.
 */
class MenuBar {
    private EventManager eventManager;
    private JMenuBar container;

    JMenuBar getContainer() {
        return container;
    }

    MenuBar(EventManager eventManager) {
        this.eventManager = eventManager;
        container = new JMenuBar();

        createFileMenu();
        createViewMenu();
    }

    private void createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        container.add(fileMenu);

        JMenuItem importItem = new JMenuItem("Import");
        importItem.addActionListener(
                (e) -> eventManager.emit("file:import:begin")
        );
        importItem.setMnemonic(KeyEvent.VK_I);
        fileMenu.add(importItem);

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(
                (e) -> eventManager.emit("file:delete")
        );
        deleteItem.setMnemonic(KeyEvent.VK_D);
        fileMenu.add(deleteItem);

        fileMenu.addSeparator();

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(
                (e) -> eventManager.emit("app:exit")
        );
        quitItem.setMnemonic(KeyEvent.VK_Q);
        fileMenu.add(quitItem);
    }

    private void createViewMenu() {
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        container.add(viewMenu);

        JRadioButtonMenuItem viewerRadioButton = new JRadioButtonMenuItem("Photo viewer");
        viewerRadioButton.addActionListener(
                (e) -> eventManager.emit(new ViewChangeEvent("viewer"))
        );
        viewerRadioButton.setSelected(true);
        viewerRadioButton.setMnemonic(KeyEvent.VK_W);
        viewMenu.add(viewerRadioButton);

        JRadioButtonMenuItem browerRadioButton = new JRadioButtonMenuItem("Browser");
        browerRadioButton.addActionListener(
                (e) -> eventManager.emit(new ViewChangeEvent("browser"))
        );
        browerRadioButton.setMnemonic(KeyEvent.VK_B);
        viewMenu.add(browerRadioButton);

        ButtonGroup viewButtonGroup = new ButtonGroup();
        viewButtonGroup.add(viewerRadioButton);
        viewButtonGroup.add(browerRadioButton);
    }
}
