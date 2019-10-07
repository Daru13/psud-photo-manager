package GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Vector;

public class ToolSettingPanel extends ToolBarPanel {

    private JPanel toolSettingsContainer;


    public ToolSettingPanel(PhotoFrame photoFrame) {
        super(photoFrame);

        toolSettingsContainer = new JPanel();

        configure();
        createContent();
    }

    @Override
    protected void configure() {
        super.configure();

        GridBagLayout layout = new GridBagLayout();
        toolSettingsContainer.setLayout(layout);
        toolSettingsContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
        toolSettingsContainer.setOpaque(false);
    }

    @Override
    protected void createContent() {
        createTitle();
        createSettingsEditors();

        revalidate();
    }

    private void createTitle() {
        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font(settingsLabel.getFont().getName(), Font.BOLD, TITLE_SIZE));
        settingsLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        settingsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        add(settingsLabel);
    }

    private void createSettingsEditors() {
        createColorSelector();
        createThicknessEditor();
        createFontEditor();

        add(toolSettingsContainer);
    }

    private void createColorSelector() {
        // Color choice
        JButton chooseColorButton = new JButton("Select color");
        chooseColorButton.setFocusPainted(false);
        chooseColorButton.setBackground(photoFrame.getToolSettings().getColor());
        chooseColorButton.addActionListener((e) -> {
            Color newColor = JColorChooser.showDialog(this, "Select the main color", Color.BLACK);

            if (newColor != null) {
                photoFrame.getToolSettings().setColor(newColor);
                updateColorSelector(chooseColorButton);
            }
        });

        updateColorSelector(chooseColorButton);

        // Grid constraints and appending
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        toolSettingsContainer.add(new JLabel("COLOR"), constraints);

        constraints.gridy = 1;
        constraints.insets = new Insets(10, 0, 30, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        toolSettingsContainer.add(chooseColorButton, constraints);
    }

    private void updateColorSelector(JButton chooseColorButton) {
        Color backgroundColor = photoFrame.getToolSettings().getColor();
        chooseColorButton.setBackground(backgroundColor);

        // Basic heuristic to adapt the text color to the background color
        int r = backgroundColor.getRed();
        int g = backgroundColor.getGreen();
        int b = backgroundColor.getBlue();

        chooseColorButton.setForeground(r + g + b < 400 ? Color.WHITE : Color.BLACK);
    }

    private void createThicknessEditor() {
        // Line thickness
        JSlider thicknessSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 3);
        thicknessSlider.setMajorTickSpacing(2);
        thicknessSlider.setMinorTickSpacing(1);
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.setOpaque(false);
        thicknessSlider.addChangeListener((e) -> {
            int newThickness = thicknessSlider.getValue() * 2;
            photoFrame.getToolSettings().setThickness(newThickness);
        });

        Hashtable<Integer, JComponent> thicknessSliderLabels = new Hashtable<>();
        thicknessSliderLabels.put(1, new JLabel("Thin"));
        thicknessSliderLabels.put(5, new JLabel("Thick"));
        thicknessSlider.setLabelTable(thicknessSliderLabels);
        thicknessSlider.setPaintLabels(true);

        // Grid constraints and appending
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_START;
        toolSettingsContainer.add(new JLabel("THICKNESS"), constraints);

        constraints.gridy = 3;
        constraints.insets = new Insets(10, 0, 30, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        toolSettingsContainer.add(thicknessSlider, constraints);
    }

    private void createFontEditor() {
        // Font family
        String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontFamilyComboBox = new JComboBox<>(availableFonts);
        fontFamilyComboBox.setOpaque(false);
        fontFamilyComboBox.addItemListener((e) -> {
            String newFontFamily = (String)e.getItem();
            photoFrame.getToolSettings().setFontFamily(newFontFamily);
        });

        // Font size
        Vector<Integer> fontSizes = new Vector<>();
        for (int size = 8; size <= 72; size += 4) {
            fontSizes.add(size);
        }

        JComboBox<Integer> fontSizesComboBox = new JComboBox<>(fontSizes);
        fontSizesComboBox.setOpaque(false);
        fontSizesComboBox.addItemListener((e) -> {
            int newFontSize = (Integer) fontSizesComboBox.getSelectedItem();
            photoFrame.getToolSettings().setFontSize(newFontSize);
        });

        // Grid constraints and appending
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.LINE_START;
        toolSettingsContainer.add(new JLabel("FONT"), constraints);

        constraints.gridy = 5;
        constraints.insets = new Insets(10, 0, 10, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        toolSettingsContainer.add(fontFamilyComboBox, constraints);

        constraints.gridy = 6;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        toolSettingsContainer.add(fontSizesComboBox, constraints);
    }
}
