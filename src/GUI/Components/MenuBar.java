package GUI.Components;

import Events.EventManager;
import Events.ViewChangeEvent;
import GUI.Views.ViewID;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * The main menu bar of the GUI.
 * It offers menus with different options depending on the view.
 */
public class MenuBar extends JMenuBar {

    private EventManager eventManager;


    public MenuBar(EventManager eventManager) {
        super();

        this.eventManager = eventManager;

        createFileMenu();
        createViewMenu();
    }

    private void createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        add(fileMenu);

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
        add(viewMenu);

        JRadioButtonMenuItem viewerRadioButton = new JRadioButtonMenuItem("Photo viewer");
        viewerRadioButton.addActionListener(
                (e) -> eventManager.emit(new ViewChangeEvent(ViewID.SinglePhoto))
        );
        viewerRadioButton.setSelected(true);
        viewerRadioButton.setMnemonic(KeyEvent.VK_W);
        viewMenu.add(viewerRadioButton);

        JRadioButtonMenuItem browerRadioButton = new JRadioButtonMenuItem("Browser");
        browerRadioButton.addActionListener(
                (e) -> eventManager.emit(new ViewChangeEvent(ViewID.PhotoBrowser))
        );
        browerRadioButton.setMnemonic(KeyEvent.VK_B);
        viewMenu.add(browerRadioButton);

        ButtonGroup viewButtonGroup = new ButtonGroup();
        viewButtonGroup.add(viewerRadioButton);
        viewButtonGroup.add(browerRadioButton);
    }
}
