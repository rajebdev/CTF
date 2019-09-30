import java.awt.Component;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

class CellFrame extends JFrame
{
    public CellFrame() {
        this.setTitle("Cellular Automata");
        this.setSize(900, 720);
        this.setDefaultCloseOperation(3);
        this.addWindowListener(new WindowAdapter() {
            public void WindowClosing(final WindowEvent windowEvent) {
                CellFrame.this.dispose();
                System.exit(0);
            }
        });
        this.getContentPane().add(new CellPanel());
    }
}
