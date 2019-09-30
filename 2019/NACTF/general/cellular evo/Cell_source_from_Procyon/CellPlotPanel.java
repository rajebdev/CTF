import java.awt.event.MouseEvent;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

class CellPlotPanel extends JPanel implements MouseListener
{
    public String[] messages;
    private String title;
    private int toggle;
    private char nhbd;
    private char lattice;
    private int nstates0;
    private int nstates1;
    private int large;
    private int color_chart_id;
    private boolean plane0;
    private boolean plane1;
    private int grids;
    private String prog;
    private String[] button_reset;
    private CellDisplay CD;
    private CellSpace CS;
    private CellAutomProg CA;
    
    public CellPlotPanel() {
        this.title = "Cellular Automata Simulation";
        this.toggle = 1;
        this.addMouseListener(this);
        this.nhbd = 'V';
        this.lattice = '4';
        this.nstates0 = 2;
        this.nstates1 = 1;
        this.large = 3;
        this.color_chart_id = 0;
        this.plane0 = true;
        this.plane1 = true;
        this.grids = 1;
        (this.messages = new String[3])[0] = "";
        this.messages[1] = "";
        this.messages[2] = "";
        (this.button_reset = new String[3])[0] = "";
        this.button_reset[1] = "";
        this.button_reset[2] = "";
        this.CS = new CellSpace(this.nhbd, this.lattice, this.nstates0, this.nstates1, this.large, this.messages);
        (this.CD = new CellDisplay(this.messages, 30, 50, this.large, this.color_chart_id, this.nstates0 * this.nstates1)).set_options(this.plane0, this.plane1, this.grids);
        this.CA = new CellAutomProg(this.CS);
    }
    
    public void paintComponent(final Graphics g) {
        final BasicStroke stroke = new BasicStroke(3.0f);
        super.paintComponent(g);
        final Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setStroke(stroke);
        graphics2D.setColor(Color.blue);
        graphics2D.drawLine(610, 0, 610, 720);
        this.CD.draw(graphics2D, this.CS, this.title);
    }
    
    public void execute(final String s, final String s2) {
        boolean b = false;
        this.button_reset[0] = "";
        this.button_reset[1] = "";
        this.button_reset[2] = "";
        if (!s.equals("Center")) {
            this.messages[0] = s;
            this.messages[1] = "";
            this.messages[2] = "";
        }
        if (s.equals("Square")) {
            this.nhbd = 'V';
            this.lattice = '4';
            b = true;
            this.messages[0] = "Square cells, von Neumann/Moore neighborhoods";
        }
        else if (s.equals("Marg")) {
            this.nhbd = 'M';
            this.lattice = '4';
            b = true;
            this.messages[0] = "Margoles 2x2 neighborhoods";
        }
        else if (s.equals("Hex")) {
            this.nhbd = 'H';
            this.lattice = '6';
            b = true;
            this.messages[0] = "Hexagonal cells";
            if (this.large == 1) {
                this.large = 2;
                this.button_reset[0] = "Medium";
                this.messages[1] = "(Cell size changed to Medium.)";
            }
            if (this.color_chart_id == 1) {
                this.color_chart_id = 0;
                this.button_reset[1] = "Colors";
                this.messages[1] = "(Cell Display changed from Arrows to Colors.)";
            }
        }
        else if (s.equals("2x1")) {
            this.nstates0 = 2;
            this.nstates1 = 1;
            b = true;
            this.messages[0] = "Two colors, one plane";
        }
        else if (s.equals("4x1")) {
            this.nstates0 = 4;
            this.nstates1 = 1;
            b = true;
            this.messages[0] = "Four colors, one plane";
        }
        else if (s.equals("8x1")) {
            this.nstates0 = 8;
            this.nstates1 = 1;
            b = true;
            this.messages[0] = "Eight colors, one plane";
        }
        else if (s.equals("16x1")) {
            this.nstates0 = 16;
            this.nstates1 = 1;
            b = true;
            this.messages[0] = "Sixteen colors, one plane";
        }
        else if (s.equals("2x2")) {
            this.nstates0 = 2;
            this.nstates1 = 2;
            b = true;
            this.messages[0] = "Two colors for plane 0 and plane 1";
        }
        else if (s.equals("4x2")) {
            this.nstates0 = 4;
            this.nstates1 = 2;
            b = true;
            this.messages[0] = "Four colors for plane 0, two colors for plane 1";
        }
        else if (s.equals("8x2")) {
            this.nstates0 = 8;
            this.nstates1 = 2;
            b = true;
            this.messages[0] = "Eight colors for plane 0, two colors for plane 1";
        }
        else if (s.equals("4x4")) {
            this.nstates0 = 4;
            this.nstates1 = 4;
            b = true;
            this.messages[0] = "Four colors for plane 0 and plane 1";
        }
        else if (s.equals("Small")) {
            if (this.lattice == '6') {
                this.large = 2;
                b = true;
                this.messages[0] = "Medium cells, 132 rows, 160 columns";
                this.messages[1] = "(No Small cells for Hex neighborhood.)";
                this.button_reset[0] = "Medium";
            }
            else {
                this.large = 1;
                b = true;
                this.messages[0] = "Small cells, 250 rows, 250 columns";
                if (this.color_chart_id == 1) {
                    this.messages[1] = "(Cell Display Arrow changed to Colors.)";
                    this.button_reset[0] = "Colors";
                }
            }
        }
        else if (s.equals("Medium")) {
            this.large = 2;
            b = true;
            if (this.lattice == '4') {
                this.messages[0] = "Medium cells, 100 rows, 100 columns";
            }
            else {
                this.messages[0] = "Small cells, 132 rows, 160 columns";
            }
        }
        else if (s.equals("Large")) {
            this.large = 3;
            b = true;
            if (this.lattice == '4') {
                this.messages[0] = "Large cells, 50 rows, 50 columns";
            }
            else {
                this.messages[0] = "Large cells, 38 rows, 44 columns";
            }
        }
        else if (s.equals("Colors")) {
            this.color_chart_id = 0;
            b = true;
            this.messages[0] = "display: standard colors.";
        }
        else if (s.equals("Arrows")) {
            if (this.lattice == '4' && this.nstates0 == 8 && this.nstates1 == 1 && this.large > 1) {
                this.color_chart_id = 1;
                b = true;
                this.messages[0] = "display: colors and arrows.";
            }
            else {
                this.messages[0] = "arrow display only for square lattice, 8x1 plane, medium or large cells.";
                this.button_reset[0] = "Colors";
            }
        }
        else if (s.equals("Pln 0")) {
            this.plane0 = true;
            this.plane1 = false;
            this.CD.set_options(this.plane0, this.plane1, this.grids);
            this.messages[0] = "Display plane 0 only";
        }
        else if (s.equals("Pln 1")) {
            this.plane0 = false;
            this.plane1 = true;
            this.CD.set_options(this.plane0, this.plane1, this.grids);
            this.messages[0] = "Display plane 1 only";
        }
        else if (s.equals("Both")) {
            this.plane0 = true;
            this.plane1 = true;
            this.CD.set_options(this.plane0, this.plane1, this.grids);
            this.messages[0] = "Display plane 0 and plane 1";
        }
        else if (s.equals("On")) {
            this.grids = 1;
            this.CD.set_options(this.plane0, this.plane1, this.grids);
            this.messages[0] = "Grid lines displayed";
        }
        else if (s.equals("Off")) {
            this.grids = 0;
            this.CD.set_options(this.plane0, this.plane1, this.grids);
            this.messages[0] = "Grid lines not displayed";
        }
        else if (s.equals("Alt")) {
            if (this.nhbd != 'M') {
                this.grids = 1;
                this.CD.set_options(this.plane0, this.plane1, this.grids);
                this.messages[0] = "Alternate grid lines for Margoles neighborhoods only.";
                this.button_reset[0] = "On";
            }
            else {
                this.grids = 2;
                this.CD.set_options(this.plane0, this.plane1, this.grids);
                this.messages[0] = "Alternate grid lines displayed (for Margoles neighborhoods)";
            }
        }
        else if (s.equals("Clear")) {
            this.CS.clear();
        }
        else if (s.equals("Center")) {
            this.CS.center();
        }
        else if (s.equals("InPat")) {
            this.CS.in_pattern();
        }
        else if (s.equals("Prev")) {
            this.CS.load_pattern(-1);
        }
        else if (s.equals("Next")) {
            this.CS.load_pattern(1);
        }
        else if (s.equals("OutPat")) {
            this.CS.out_pattern();
        }
        else if (s.equals("0")) {
            this.toggle = 0;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("1")) {
            this.toggle = 1;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("2")) {
            this.toggle = 2;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("3")) {
            this.toggle = 3;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("4")) {
            this.toggle = 4;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("5")) {
            this.toggle = 5;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("6")) {
            this.toggle = 6;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("7")) {
            this.toggle = 7;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("8")) {
            this.toggle = 8;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("9")) {
            this.toggle = 9;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("A")) {
            this.toggle = 10;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("B")) {
            this.toggle = 11;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("C")) {
            this.toggle = 12;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("D")) {
            this.toggle = 13;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("E")) {
            this.toggle = 14;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("F")) {
            this.toggle = 15;
            this.messages[0] = "Toggle: " + s;
        }
        else if (s.equals("Read")) {
            this.CA.reset();
            this.CS.reset_gen();
            if (s2.equals("")) {
                this.messages[0] = "Cannot read program.txt";
            }
            else {
                this.messages[0] = "Program read in.";
            }
        }
        else if (s.equals("Parse")) {
            this.CA.reset();
            this.CS.reset_gen();
            if (this.CA.parse(s2) && !this.CA.build_table()) {
                final StringBuilder sb = new StringBuilder();
                final String[] messages = this.messages;
                final int n = 0;
                messages[n] = sb.append(messages[n]).append(" -- no table.").toString();
            }
        }
        else if (s.equals("Write")) {
            if (this.write_program(s2)) {
                this.messages[0] = "Program written to outprogram.txt.";
            }
            else {
                this.messages[0] = "Error writing the program into outprogram.txt.";
            }
        }
        else if (s.equals("Init")) {
            if (!this.CA.autom_built) {
                this.messages[0] = "Automata not built.";
            }
            else {
                this.CS.clear();
                if (this.CS.init(this.CA)) {
                    this.messages[0] = "Initialized.";
                }
            }
        }
        else if (s.equals("ResetGen")) {
            this.CS.reset_gen();
        }
        else if (s.equals("Step")) {
            if (!this.CA.autom_built) {
                this.messages[0] = "Automata not built.";
            }
            else {
                this.CS.next_gen(this.CA);
            }
        }
        if (b) {
            this.CS = new CellSpace(this.nhbd, this.lattice, this.nstates0, this.nstates1, this.large, this.messages);
            (this.CD = new CellDisplay(this.messages, 30, 50, this.large, this.color_chart_id, this.nstates0 * this.nstates1)).set_options(this.plane0, this.plane1, this.grids);
            this.CA = new CellAutomProg(this.CS);
        }
        this.repaint();
    }
    
    private boolean write_program(final String x) {
        try {
            final FileOutputStream out = new FileOutputStream("outprogram.txt");
            final PrintStream printStream = new PrintStream(out);
            printStream.println(x);
            printStream.close();
            out.close();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public void update_buttons(final String[] array) {
        array[0] = this.button_reset[0];
        array[1] = this.button_reset[1];
        array[2] = this.button_reset[2];
        this.button_reset[0] = "";
        this.button_reset[1] = "";
        this.button_reset[2] = "";
    }
    
    @Override
    public void mouseClicked(final MouseEvent mouseEvent) {
        this.CD.toggle(mouseEvent.getX(), mouseEvent.getY(), this.toggle, this.CS);
        this.repaint();
    }
    
    @Override
    public void mouseEntered(final MouseEvent mouseEvent) {
    }
    
    @Override
    public void mouseExited(final MouseEvent mouseEvent) {
    }
    
    @Override
    public void mousePressed(final MouseEvent mouseEvent) {
    }
    
    @Override
    public void mouseReleased(final MouseEvent mouseEvent) {
    }
}
