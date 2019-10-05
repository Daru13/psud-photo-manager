package UI.Components;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class ToolSettingPanel extends AbstractToolBarPanel {

    private GroupLayout layout;
    private GroupLayout.SequentialGroup horizontalSettingGroup;
    private GroupLayout.SequentialGroup verticalSettingGroup;
    private GroupLayout.ParallelGroup labelGroup;
    private GroupLayout.ParallelGroup editorGroup;

    public ToolSettingPanel(PhotoFrame photoFrame) {
        super(photoFrame);
    }

    @Override
    protected void configure() {
        super.configure();

        layout = new GroupLayout(this);
        setLayout(layout);

        horizontalSettingGroup = layout.createSequentialGroup();
        verticalSettingGroup = layout.createSequentialGroup();
        labelGroup = layout.createParallelGroup();
        editorGroup = layout.createParallelGroup();

        horizontalSettingGroup.addGroup(labelGroup);
        horizontalSettingGroup.addGroup(editorGroup);

        layout.setHorizontalGroup(horizontalSettingGroup);
        layout.setVerticalGroup(verticalSettingGroup);
    }

    protected void createContent() {
        createTitle();

        createColorSelector();
        createThicknessEditor();

        createSeparator();

        createFontFamilySelector();
        createFontSizeEditor();

        revalidate();
    }

    private void createSetting(String labelText, JComponent settingEditor) {
        JLabel label = new JLabel(labelText);

        labelGroup.addComponent(label);
        editorGroup.addComponent(settingEditor);

        GroupLayout.ParallelGroup group = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        verticalSettingGroup.addGroup(group
                .addComponent(label)
                .addComponent(settingEditor)
        );
    }

    private void createTitle() {
        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font(settingsLabel.getFont().getName(), Font.BOLD, 16));
        settingsLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        add(settingsLabel);
    }

    private void createColorSelector() {
        //JLabel label = generateLabel("Color");
        //label.setLayout(new BorderLayout());

        JButton chooseColorButton = new JButton("Select color");
        chooseColorButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        chooseColorButton.addActionListener((e) -> {
            Color newColor = JColorChooser.showDialog(this, "Select the main color", Color.BLACK);

            if (newColor != null) {
                photoFrame.getToolSettings().setColor(newColor);
            }
        });

        //label.add(chooseColorButton, BorderLayout.EAST);
        //add(label);
        createSetting("Color", chooseColorButton);
    }

    private void createThicknessEditor() {
        //createSettingLabel("Line thickness");

        JSlider thicknessSlider = new JSlider(JSlider.HORIZONTAL,
                1, 20, 5);
        thicknessSlider.setAlignmentX(JSlider.CENTER_ALIGNMENT);
        thicknessSlider.setMajorTickSpacing(5);
        thicknessSlider.setMinorTickSpacing(1);
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        thicknessSlider.setOpaque(false);
        thicknessSlider.addChangeListener((e) -> {
            int newThickness = thicknessSlider.getValue();
            photoFrame.getToolSettings().setThickness(newThickness);
        });

        Hashtable thicknessSliderLabels = new Hashtable();
        thicknessSliderLabels.put(1, new JLabel(Integer.toString((1))));
        thicknessSliderLabels.put(10, new JLabel(Integer.toString((10))));
        thicknessSliderLabels.put(20, new JLabel(Integer.toString((20))));
        thicknessSlider.setLabelTable(thicknessSliderLabels);
        thicknessSlider.setPaintLabels(true);

        //add(thicknessSlider);
        createSetting("Line thickness", thicknessSlider);

    }

    private void createFontFamilySelector() {
        //createSettingLabel("Font family");

        String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontFamilyComboBox = new JComboBox<>(availableFonts);
        fontFamilyComboBox.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
        fontFamilyComboBox.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        //fontFamilyComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        fontFamilyComboBox.setOpaque(false);
        fontFamilyComboBox.addItemListener((e) -> {
            String newFontFamily = (String)e.getItem();
            photoFrame.getToolSettings().setFontFamily(newFontFamily);
        });

        //add(fontFamilyComboBox);
        createSetting("Font family", fontFamilyComboBox);
    }

    private void createFontSizeEditor() {
        //JLabel label = createSettingLabel("Font size");

        JSlider fontSizeSlider = new JSlider(JSlider.HORIZONTAL,
                8, 72, 16);
        fontSizeSlider.setAlignmentX(JSlider.CENTER_ALIGNMENT);
        fontSizeSlider.setMinorTickSpacing(4);
        fontSizeSlider.setPaintTicks(true);
        fontSizeSlider.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        fontSizeSlider.setOpaque(false);
        fontSizeSlider.addChangeListener((e) -> {
            int newFontSize = fontSizeSlider.getValue();
            photoFrame.getToolSettings().setFontSize(newFontSize);
        });

        Hashtable fontSizeSliderLabels = new Hashtable();
        fontSizeSliderLabels.put(8, new JLabel(Integer.toString((8))));
        fontSizeSliderLabels.put(24, new JLabel(Integer.toString((24))));
        fontSizeSliderLabels.put(40, new JLabel(Integer.toString((40))));
        fontSizeSliderLabels.put(56, new JLabel(Integer.toString((56))));
        fontSizeSliderLabels.put(72, new JLabel(Integer.toString((72))));
        fontSizeSlider.setLabelTable(fontSizeSliderLabels);
        fontSizeSlider.setPaintLabels(true);

        //add(fontSizeSlider);
        createSetting("Font size", fontSizeSlider);
    }


}
