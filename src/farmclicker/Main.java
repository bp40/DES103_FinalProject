package farmclicker;

import farmclicker.uilogic.ClickPanel;
import farmclicker.uilogic.SaveManager;
import farmclicker.uilogic.UpgradesPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {

        //Sets look and feel to windows
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(720, 480));
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Farming clicker game");


        UpgradesPanel upgradesPanel = new UpgradesPanel();
        frame.add(upgradesPanel, BorderLayout.EAST);

        ClickPanel clickPanel = new ClickPanel();
        frame.add(clickPanel, BorderLayout.CENTER);

        SaveManager.loadSaveFromFile();

        //Save game progress every 10 seconds
        Timer saveTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveManager.saveProgress();
            }
        });
        saveTimer.setRepeats(true);
        saveTimer.start();

        frame.setVisible(true);
        frame.validate();

    }


}
