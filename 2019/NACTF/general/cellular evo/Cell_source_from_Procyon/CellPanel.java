import java.awt.event.ActionEvent;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

class CellPanel extends JPanel implements ActionListener
{
    private CellPlotPanel canvas;
    private JTextArea progarea;
    private String[] reset;
    private JRadioButton rb_colors;
    private JRadioButton rb_medium;
    private JRadioButton rb_on;
    
    public CellPanel() {
        this.setLayout(new BorderLayout());
        this.add(this.canvas = new CellPlotPanel(), "Center");
        final Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createHorizontalStrut(1));
        final JLabel comp = new JLabel("Neighborhoods:");
        final Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(comp);
        horizontalBox.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox);
        final Box horizontalBox2 = Box.createHorizontalBox();
        final ButtonGroup buttonGroup = new ButtonGroup();
        final JRadioButton radioButton = new JRadioButton("Square", true);
        buttonGroup.add(radioButton);
        horizontalBox2.add(radioButton);
        radioButton.addActionListener(this);
        final JRadioButton radioButton2 = new JRadioButton("Marg", false);
        buttonGroup.add(radioButton2);
        horizontalBox2.add(radioButton2);
        radioButton2.addActionListener(this);
        final JRadioButton radioButton3 = new JRadioButton("Hex", false);
        buttonGroup.add(radioButton3);
        horizontalBox2.add(radioButton3);
        radioButton3.addActionListener(this);
        verticalBox.add(horizontalBox2);
        final Box horizontalBox3 = Box.createHorizontalBox();
        horizontalBox3.add(new JLabel("Cell Size:"));
        horizontalBox3.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox3);
        final Box horizontalBox4 = Box.createHorizontalBox();
        final ButtonGroup buttonGroup2 = new ButtonGroup();
        final JRadioButton radioButton4 = new JRadioButton("Small", false);
        buttonGroup2.add(radioButton4);
        horizontalBox4.add(radioButton4);
        radioButton4.addActionListener(this);
        final JRadioButton radioButton5 = new JRadioButton("Medium", false);
        buttonGroup2.add(this.rb_medium = radioButton5);
        horizontalBox4.add(radioButton5);
        radioButton5.addActionListener(this);
        final JRadioButton radioButton6 = new JRadioButton("Large", true);
        buttonGroup2.add(radioButton6);
        horizontalBox4.add(radioButton6);
        radioButton6.addActionListener(this);
        verticalBox.add(horizontalBox4);
        final Box horizontalBox5 = Box.createHorizontalBox();
        horizontalBox5.add(new JLabel("Cell Display:"));
        horizontalBox5.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox5);
        final Box horizontalBox6 = Box.createHorizontalBox();
        final ButtonGroup buttonGroup3 = new ButtonGroup();
        final JRadioButton radioButton7 = new JRadioButton("Colors", true);
        buttonGroup3.add(this.rb_colors = radioButton7);
        horizontalBox6.add(radioButton7);
        radioButton7.addActionListener(this);
        final JRadioButton radioButton8 = new JRadioButton("Arrows", false);
        buttonGroup3.add(radioButton8);
        horizontalBox6.add(radioButton8);
        radioButton8.addActionListener(this);
        verticalBox.add(horizontalBox6);
        final Box horizontalBox7 = Box.createHorizontalBox();
        horizontalBox7.add(new JLabel("Plane Type:"));
        horizontalBox7.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox7);
        final ButtonGroup buttonGroup4 = new ButtonGroup();
        final Box horizontalBox8 = Box.createHorizontalBox();
        final JRadioButton radioButton9 = new JRadioButton("2x1", true);
        buttonGroup4.add(radioButton9);
        horizontalBox8.add(radioButton9);
        radioButton9.addActionListener(this);
        final JRadioButton radioButton10 = new JRadioButton("4x1", false);
        buttonGroup4.add(radioButton10);
        horizontalBox8.add(radioButton10);
        radioButton10.addActionListener(this);
        final JRadioButton radioButton11 = new JRadioButton("8x1", false);
        buttonGroup4.add(radioButton11);
        horizontalBox8.add(radioButton11);
        radioButton11.addActionListener(this);
        final JRadioButton radioButton12 = new JRadioButton("16x1", false);
        buttonGroup4.add(radioButton12);
        horizontalBox8.add(radioButton12);
        radioButton12.addActionListener(this);
        verticalBox.add(horizontalBox8);
        final Box horizontalBox9 = Box.createHorizontalBox();
        final JRadioButton radioButton13 = new JRadioButton("2x2", false);
        buttonGroup4.add(radioButton13);
        horizontalBox9.add(radioButton13);
        radioButton13.addActionListener(this);
        final JRadioButton radioButton14 = new JRadioButton("4x2", false);
        buttonGroup4.add(radioButton14);
        horizontalBox9.add(radioButton14);
        radioButton14.addActionListener(this);
        final JRadioButton radioButton15 = new JRadioButton("8x2", false);
        buttonGroup4.add(radioButton15);
        horizontalBox9.add(radioButton15);
        radioButton15.addActionListener(this);
        final JRadioButton radioButton16 = new JRadioButton("4x4", false);
        buttonGroup4.add(radioButton16);
        horizontalBox9.add(radioButton16);
        radioButton16.addActionListener(this);
        verticalBox.add(horizontalBox9);
        final Box horizontalBox10 = Box.createHorizontalBox();
        horizontalBox10.add(new JLabel("Display Options:"));
        horizontalBox10.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox10);
        final Box horizontalBox11 = Box.createHorizontalBox();
        final ButtonGroup buttonGroup5 = new ButtonGroup();
        final JRadioButton radioButton17 = new JRadioButton("Pln 0", false);
        buttonGroup5.add(radioButton17);
        horizontalBox11.add(radioButton17);
        radioButton17.addActionListener(this);
        final JRadioButton radioButton18 = new JRadioButton("Pln 1", false);
        buttonGroup5.add(radioButton18);
        horizontalBox11.add(radioButton18);
        radioButton18.addActionListener(this);
        final JRadioButton radioButton19 = new JRadioButton("Both", true);
        buttonGroup5.add(radioButton19);
        horizontalBox11.add(radioButton19);
        radioButton19.addActionListener(this);
        verticalBox.add(horizontalBox11);
        final Box horizontalBox12 = Box.createHorizontalBox();
        horizontalBox12.add(new JLabel("Grid Options:"));
        horizontalBox12.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox12);
        final Box horizontalBox13 = Box.createHorizontalBox();
        final ButtonGroup buttonGroup6 = new ButtonGroup();
        final JRadioButton radioButton20 = new JRadioButton("On", true);
        buttonGroup6.add(this.rb_on = radioButton20);
        horizontalBox13.add(radioButton20);
        radioButton20.addActionListener(this);
        final JRadioButton radioButton21 = new JRadioButton("Off", false);
        buttonGroup6.add(radioButton21);
        horizontalBox13.add(radioButton21);
        radioButton21.addActionListener(this);
        final JRadioButton radioButton22 = new JRadioButton("Alt", false);
        buttonGroup6.add(radioButton22);
        horizontalBox13.add(radioButton22);
        radioButton22.addActionListener(this);
        verticalBox.add(horizontalBox13);
        final Box horizontalBox14 = Box.createHorizontalBox();
        horizontalBox14.add(new JLabel("Display Commands:"));
        horizontalBox14.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox14);
        final Box horizontalBox15 = Box.createHorizontalBox();
        final JButton comp2 = new JButton("Clear");
        comp2.addActionListener(this);
        horizontalBox15.add(comp2);
        final JButton comp3 = new JButton("Center");
        comp3.addActionListener(this);
        horizontalBox15.add(comp3);
        verticalBox.add(horizontalBox15);
        final Box horizontalBox16 = Box.createHorizontalBox();
        horizontalBox16.add(new JLabel("Pattern Commands:"));
        horizontalBox16.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox16);
        final Box horizontalBox17 = Box.createHorizontalBox();
        final JButton comp4 = new JButton("InPat");
        comp4.addActionListener(this);
        horizontalBox17.add(comp4);
        final JButton comp5 = new JButton("Prev");
        comp5.addActionListener(this);
        horizontalBox17.add(comp5);
        final JButton comp6 = new JButton("Next");
        comp6.addActionListener(this);
        horizontalBox17.add(comp6);
        final JButton comp7 = new JButton("OutPat");
        comp7.addActionListener(this);
        horizontalBox17.add(comp7);
        verticalBox.add(horizontalBox17);
        final Box horizontalBox18 = Box.createHorizontalBox();
        horizontalBox18.add(new JLabel("Toggle Value:"));
        horizontalBox18.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox18);
        final Box horizontalBox19 = Box.createHorizontalBox();
        final ButtonGroup buttonGroup7 = new ButtonGroup();
        for (int i = 0; i < 8; ++i) {
            final JRadioButton radioButton23 = new JRadioButton("" + (char)(48 + i), i == 1);
            buttonGroup7.add(radioButton23);
            horizontalBox19.add(radioButton23);
            radioButton23.addActionListener(this);
        }
        verticalBox.add(horizontalBox19);
        final Box horizontalBox20 = Box.createHorizontalBox();
        for (int j = 0; j < 8; ++j) {
            final JRadioButton radioButton24 = new JRadioButton("89ABCDEF".substring(j, j + 1), false);
            buttonGroup7.add(radioButton24);
            horizontalBox20.add(radioButton24);
            radioButton24.addActionListener(this);
        }
        verticalBox.add(horizontalBox20);
        verticalBox.add(Box.createHorizontalStrut(1));
        final Box horizontalBox21 = Box.createHorizontalBox();
        horizontalBox21.add(new JLabel("Program:"));
        horizontalBox21.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox21);
        (this.progarea = new JTextArea("", 15, 20)).setColumns(20);
        this.progarea.setRows(8);
        this.progarea.setLineWrap(false);
        verticalBox.add(new JScrollPane(this.progarea));
        verticalBox.add(Box.createHorizontalStrut(1));
        final Box horizontalBox22 = Box.createHorizontalBox();
        horizontalBox22.add(new JLabel("Program Commands:"));
        horizontalBox22.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBox22);
        final Box horizontalBox23 = Box.createHorizontalBox();
        final JButton comp8 = new JButton("Read");
        comp8.addActionListener(this);
        horizontalBox23.add(comp8);
        horizontalBox23.add(Box.createVerticalStrut(1));
        final JButton comp9 = new JButton("Parse");
        comp9.addActionListener(this);
        horizontalBox23.add(comp9);
        horizontalBox23.add(Box.createVerticalStrut(1));
        final JButton comp10 = new JButton("Write");
        comp10.addActionListener(this);
        horizontalBox23.add(comp10);
        horizontalBox23.add(Box.createVerticalStrut(1));
        verticalBox.add(horizontalBox23);
        final Box horizontalBox24 = Box.createHorizontalBox();
        final JButton comp11 = new JButton("Init");
        comp11.addActionListener(this);
        horizontalBox24.add(comp11);
        horizontalBox24.add(Box.createVerticalStrut(1));
        final JButton comp12 = new JButton("ResetGen");
        comp12.addActionListener(this);
        horizontalBox24.add(comp12);
        horizontalBox24.add(Box.createVerticalStrut(1));
        final JButton comp13 = new JButton("Step");
        comp13.addActionListener(this);
        horizontalBox24.add(comp13);
        verticalBox.add(horizontalBox24);
        verticalBox.add(Box.createHorizontalStrut(1));
        this.add(verticalBox, "East");
        this.reset = new String[3];
    }
    
    private String read_program() {
        try {
            final FileReader in = new FileReader("program.txt");
            final BufferedReader bufferedReader = new BufferedReader(in);
            String string = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                string = string + line + '\n';
            }
            in.close();
            bufferedReader.close();
            return string;
        }
        catch (Exception ex) {
            return "";
        }
    }
    
    private void reset_buttons() {
        for (int i = 0; i < 3; ++i) {
            if (this.reset[i].equals("Colors")) {
                this.rb_colors.setSelected(true);
            }
            if (this.reset[i].equals("Medium")) {
                this.rb_medium.setSelected(true);
            }
            if (this.reset[i].equals("On")) {
                this.rb_on.setSelected(true);
            }
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        final String actionCommand = actionEvent.getActionCommand();
        String text = "";
        if (actionCommand.equals("Parse") || actionCommand.equals("Write")) {
            text = this.progarea.getText();
        }
        else if (actionCommand.equals("Read")) {
            text = this.read_program();
            this.progarea.setText(text);
        }
        this.canvas.execute(actionCommand, text);
        this.canvas.update_buttons(this.reset);
        this.reset_buttons();
    }
}
