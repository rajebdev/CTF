import java.awt.Polygon;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;

// 
// Decompiled by Procyon v0.5.36
// 

public class CellDisplay
{
    private int offx;
    private int offy;
    private int xinc;
    private int yinc;
    private int dx;
    private int dy;
    private int dw;
    private int large;
    private boolean plane0;
    private boolean plane1;
    private int color_chart_id;
    private int grids;
    private String[] messages;
    private Color[] color_chart;
    private int ncchart;
    private int[] counts;
    private int ncounts;
    private Color[] colors_standard;
    private Color[] colors_arrows;
    private Color[] colors_default;
    private Color[] colors_sand;
    
    public CellDisplay(final String[] messages, final int offx, final int offy, final int large, final int color_chart_id, final int ncounts) {
        this.colors_standard = new Color[] { Color.white, Color.black, Color.red, Color.green, Color.blue, Color.yellow, Color.magenta, Color.orange, new Color(250, 100, 200), new Color(100, 200, 250), new Color(200, 250, 100), new Color(250, 200, 100), new Color(200, 100, 250), new Color(100, 250, 200), new Color(200, 50, 50), new Color(50, 100, 50) };
        this.colors_arrows = new Color[] { Color.white, Color.black, Color.red, Color.green, Color.red, Color.red, Color.white, Color.white };
        this.colors_default = new Color[] { Color.white };
        this.colors_sand = new Color[] { new Color(224, 224, 224), new Color(192, 192, 192), new Color(160, 160, 160), new Color(128, 128, 128), new Color(96, 96, 96), new Color(64, 64, 64), new Color(32, 32, 32), new Color(0, 0, 0) };
        this.messages = messages;
        this.offx = offx;
        this.offy = offy;
        this.plane0 = true;
        this.plane1 = true;
        this.grids = 1;
        this.large = large;
        if (this.large == 3) {
            this.xinc = 10;
            this.yinc = 10;
        }
        else if (this.large == 2) {
            this.xinc = 5;
            this.yinc = 5;
        }
        else {
            this.xinc = 2;
            this.yinc = 2;
        }
        if (this.large == 3) {
            this.dx = 3;
            this.dy = 7;
            this.dw = 8;
        }
        else {
            this.dx = 1;
            this.dy = 2;
            this.dw = 2;
        }
        this.color_chart_id = color_chart_id;
        if (this.color_chart_id == 0) {
            this.color_chart = this.colors_standard;
            this.ncchart = 16;
        }
        else if (this.color_chart_id == 1) {
            this.color_chart = this.colors_arrows;
            this.ncchart = 8;
        }
        else {
            this.color_chart = this.colors_default;
        }
        this.ncchart = this.color_chart.length;
        this.ncounts = ncounts;
        this.counts = new int[this.ncounts];
    }
    
    public void set_options(final boolean plane0, final boolean plane2, final int grids) {
        this.plane0 = plane0;
        this.plane1 = plane2;
        this.grids = grids;
    }
    
    public void set_colors(final Color[] color_chart) {
        if (color_chart == null) {
            return;
        }
        if (color_chart.length == 0) {
            return;
        }
        this.ncchart = color_chart.length;
        this.color_chart = color_chart;
    }
    
    public void draw(final Graphics2D graphics2D, final CellSpace cellSpace, final String s) {
        final BasicStroke stroke = new BasicStroke(1.0f);
        graphics2D.setStroke(new BasicStroke(3.0f));
        graphics2D.setColor(Color.black);
        graphics2D.setFont(new Font("SansSerif", 1, 18));
        graphics2D.drawString(s, 130, 35);
        graphics2D.setStroke(stroke);
        graphics2D.setColor(Color.black);
        graphics2D.setFont(new Font("SansSerif", 1, 12));
        graphics2D.drawString(this.messages[0], 30, 600);
        graphics2D.drawString(this.messages[1], 30, 620);
        graphics2D.drawString(this.messages[2], 30, 640);
        for (int i = 0; i < this.ncounts; ++i) {
            this.counts[i] = 0;
        }
        if (cellSpace.lattice() == '4') {
            for (int j = 0; j < cellSpace.nrow(); ++j) {
                for (int k = 0; k < cellSpace.ncol(); ++k) {
                    int display_state = cellSpace.display_state(j, k, this.plane0, this.plane1);
                    if (display_state >= this.ncounts) {
                        display_state = this.ncounts - 1;
                    }
                    graphics2D.setColor(this.color_chart[display_state]);
                    graphics2D.fillRect(this.offx + k * this.xinc, this.offy + j * this.yinc, this.xinc, this.yinc);
                    if (display_state < this.ncounts) {
                        final int[] counts = this.counts;
                        final int n = display_state;
                        ++counts[n];
                    }
                    if (this.color_chart_id == 1) {
                        this.draw_arrow(graphics2D, display_state, this.offx + k * this.xinc, this.offy + j * this.yinc, this.large);
                    }
                    if (this.grids == 1) {
                        graphics2D.setColor(Color.black);
                        graphics2D.drawRect(this.offx + k * this.xinc, this.offy + j * this.yinc, this.xinc, this.yinc);
                    }
                }
            }
            if (this.grids == 2) {
                graphics2D.setColor(Color.black);
                for (int l = cellSpace.gen() % 2; l <= cellSpace.nrow(); l += 2) {
                    graphics2D.drawLine(this.offx, this.offy + l * this.yinc, this.offx + cellSpace.ncol() * this.xinc, this.offy + l * this.yinc);
                }
                for (int n2 = cellSpace.gen() % 2; n2 <= cellSpace.ncol(); n2 += 2) {
                    graphics2D.drawLine(this.offx + n2 * this.xinc, this.offy, this.offx + n2 * this.xinc, this.offy + cellSpace.nrow() * this.yinc);
                }
            }
        }
        else if (cellSpace.lattice() == '6') {
            for (int n3 = 0; n3 < cellSpace.nrow(); ++n3) {
                for (int n4 = 0; n4 < cellSpace.ncol(); ++n4) {
                    final int x = this.offx + (this.dx + this.dw) * n4;
                    int n5 = this.offy + 2 * this.dy * n3;
                    if (n4 % 2 == 1) {
                        n5 += this.dy;
                    }
                    final Polygon polygon = new Polygon();
                    polygon.addPoint(x, n5);
                    polygon.addPoint(x + this.dx, n5 + this.dy);
                    polygon.addPoint(x + this.dx + this.dw, n5 + this.dy);
                    polygon.addPoint(x + this.dx + this.dw + this.dx, n5);
                    polygon.addPoint(x + this.dx + this.dw, n5 - this.dy);
                    polygon.addPoint(x + this.dx, n5 - this.dy);
                    int display_state2 = cellSpace.display_state(n3, n4, this.plane0, this.plane1);
                    if (display_state2 >= this.ncounts) {
                        display_state2 = this.ncounts - 1;
                    }
                    graphics2D.setColor(this.color_chart[display_state2]);
                    final int[] counts2 = this.counts;
                    final int n6 = display_state2;
                    ++counts2[n6];
                    graphics2D.fillPolygon(polygon);
                    graphics2D.setColor(Color.black);
                    if (this.grids == 1) {
                        graphics2D.drawPolygon(polygon);
                    }
                }
            }
        }
        if (this.color_chart_id == 0 || this.color_chart_id == 1) {
            graphics2D.setFont(new Font("SansSerif", 1, 12));
            graphics2D.setColor(Color.black);
            for (int n7 = 0; n7 < 8 && n7 < this.ncounts; ++n7) {
                graphics2D.drawString("" + (char)(48 + n7), 30 + 70 * n7, 660);
                graphics2D.setColor(this.color_chart[n7]);
                graphics2D.fillRect(40 + 70 * n7, 650, 10, 10);
                if (this.color_chart == this.colors_arrows) {
                    this.draw_arrow(graphics2D, n7, 40 + 70 * n7, 650, 3);
                }
                graphics2D.setColor(Color.black);
                graphics2D.drawRect(40 + 70 * n7, 650, 10, 10);
                graphics2D.drawString("" + this.counts[n7], 55 + 70 * n7, 660);
            }
            if (this.color_chart_id == 0 && this.ncounts == 16) {
                for (int beginIndex = 0; beginIndex < 8 && beginIndex < this.ncounts; ++beginIndex) {
                    graphics2D.drawString("89ABCDEF".substring(beginIndex, beginIndex + 1), 30 + 70 * beginIndex, 680);
                    graphics2D.setColor(this.color_chart[beginIndex + 8]);
                    graphics2D.fillRect(40 + 70 * beginIndex, 670, 10, 10);
                    graphics2D.setColor(Color.black);
                    graphics2D.drawRect(40 + 70 * beginIndex, 670, 10, 10);
                    graphics2D.drawString(Integer.toString(this.counts[beginIndex + 8]), 55 + 70 * beginIndex, 680);
                }
            }
        }
    }
    
    private void draw_arrow(final Graphics2D graphics2D, final int n, final int n2, final int n3, final int n4) {
        if (n < 4) {
            return;
        }
        graphics2D.setColor(Color.black);
        if (n4 == 3) {
            if (n == 4 || n == 6) {
                graphics2D.drawLine(n2 + 5, n3, n2 + 5, n3 + 10);
                graphics2D.drawLine(n2 + 2, n3 + 7, n2 + 5, n3 + 10);
                graphics2D.drawLine(n2 + 8, n3 + 7, n2 + 5, n3 + 10);
            }
            else {
                graphics2D.drawLine(n2, n3 + 5, n2 + 10, n3 + 5);
                graphics2D.drawLine(n2 + 7, n3 + 2, n2 + 10, n3 + 5);
                graphics2D.drawLine(n2 + 7, n3 + 8, n2 + 10, n3 + 5);
            }
        }
        else if (n == 4 || n == 6) {
            graphics2D.drawLine(n2 + 0, n3 + 1, n2 + 2, n3 + 4);
            graphics2D.drawLine(n2 + 4, n3 + 1, n2 + 2, n3 + 4);
        }
        else {
            graphics2D.drawLine(n2 + 1, n3 + 0, n2 + 4, n3 + 2);
            graphics2D.drawLine(n2 + 1, n3 + 4, n2 + 4, n3 + 2);
        }
    }
    
    public void toggle(final int n, final int n2, final int n3, final CellSpace cellSpace) {
        this.messages[0] = "";
        this.messages[1] = "";
        this.messages[2] = "";
        if (n < this.offx || n2 < this.offy) {
            return;
        }
        if (!(n3 >= 0 & n3 < 16)) {
            return;
        }
        if (n3 >= this.ncounts) {
            this.messages[0] = "Improper toggle value: " + "0123456789ABCDEF".substring(n3, n3 + 1);
            return;
        }
        int i;
        int j;
        if (cellSpace.lattice() == '6') {
            final int n4 = n - this.offx;
            if (n4 < 0) {
                return;
            }
            if (n4 % (this.dx + this.dw) < this.dx) {
                return;
            }
            i = n4 / (this.dx + this.dw);
            if (i >= cellSpace.ncol()) {
                return;
            }
            final int n5 = n2 - this.offy + ((i % 2 == 0) ? this.dy : 0);
            if (n5 < 0) {
                return;
            }
            j = n5 / (2 * this.dy);
            if (j >= cellSpace.nrow()) {
                return;
            }
        }
        else {
            if (n < this.offx || n2 < this.offy) {
                return;
            }
            i = (n - this.offx) / this.xinc;
            j = (n2 - this.offy) / this.yinc;
            if (j >= cellSpace.nrow() || i >= cellSpace.ncol()) {
                return;
            }
        }
        cellSpace.set(j, i, n3);
        this.messages[0] = "Toggled (" + j + "," + i + ")  to " + "0123456789ABCDEF".substring(n3, n3 + 1);
    }
}
