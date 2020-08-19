package Paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingPaint extends JFrame implements ActionListener {

    JButton clearButton, undoButton, thicknessButton;
    public static DrawingArea drawingArea = new DrawingArea();
    // create a button
    JButton color = new JButton("Color");

    public void show(){

    }

    // Constructor
    SwingPaint()
    {
        //create new frame
        JFrame frame = new JFrame("Swing paint");
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        color.addActionListener(this);
        controlPanel.add(color);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        //add to panel
        controlPanel.add(clearButton);

        undoButton = new JButton("Undo");
        undoButton.addActionListener(this);
        controlPanel.add(undoButton);

        String[] thicknesses = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        final JComboBox<String> thickness_menu = new JComboBox<String>(thicknesses);
        thickness_menu.setVisible(true);
        thickness_menu.setEditable(true);
        controlPanel.add(thickness_menu);

        // Create an ActionListener for the JComboBox component.
        thickness_menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // Get the source of the component, which is our combo
                // box.
                JComboBox thickness_menu = (JComboBox) event.getSource();

                // Print the selected items and the action command.
                Object selected = thickness_menu.getSelectedItem();
                System.out.println("Selected Item  = " + selected);
                String command = event.getActionCommand();
                System.out.println("Action Command = " + command);

                // Detect whether the action command is "comboBoxEdited"
                // or "comboBoxChanged"
                if ("comboBoxEdited".equals(command)) {
                    System.out.println("User has typed a string in " +
                            "the combo box.");
                    drawingArea.thickness = Float.parseFloat(String.valueOf(selected));
                } else if ("comboBoxChanged".equals(command)) {
                    System.out.println("User has selected an item " +
                            "from the combo box.");
                    drawingArea.thickness = Float.parseFloat(String.valueOf(selected));
                }
            }
        });

        content.add(controlPanel, BorderLayout.NORTH);
        content.add(drawingArea, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == color){
            // color chooser Dialog Box
            Color color = JColorChooser.showDialog(this,
                    "Select a color", Color.BLACK);

            // set draw color
            drawingArea.setColor(color);
            DrawingArea.clear = false;
            DrawingArea.undo = false;
        }
        else if (e.getSource() == clearButton){
            DrawingArea.clear = true;
            drawingArea.repaint();
        }
        else if (e.getSource() == undoButton){
            DrawingArea.undo = true;
            drawingArea.repaint();
        }
        else if (e.getSource() == thicknessButton){
        }
    }


    public static void main(String[] args) {
        SwingPaint s = new SwingPaint();
        s.setSize(400, 400);
        s.setVisible(true);
        s.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
