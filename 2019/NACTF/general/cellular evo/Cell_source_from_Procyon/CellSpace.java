import java.io.Reader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

// 
// Decompiled by Procyon v0.5.36
// 

public class CellSpace
{
    private char nhbd;
    private char lattice;
    private int nrow;
    private int ncol;
    private int nC;
    private int nstates0;
    private int nstates1;
    private int generation;
    public char[] C0;
    public char[] C1;
    public String[] messages;
    private CellAutom CA;
    private Random R;
    private int min_row;
    private int min_col;
    private int max_row;
    private int max_col;
    private FileReader patterns_file;
    private BufferedReader patterns;
    private String[] lines;
    private int nlines;
    private int[] pats;
    private int npats;
    private int cur_pat;
    private boolean sand_trace;
    private char sand_max;
    
    public CellSpace(final char nhbd, final char lattice, final int nstates0, final int nstates2, final int n, final String[] messages) {
        this.R = new Random();
        this.nhbd = nhbd;
        this.lattice = lattice;
        this.nstates0 = nstates0;
        this.nstates1 = nstates2;
        this.messages = messages;
        if (this.lattice == '6') {
            if (n == 3) {
                this.nrow = 38;
                this.ncol = 44;
            }
            else {
                this.nrow = 132;
                this.ncol = 160;
            }
        }
        else if (n == 3) {
            this.nrow = 50;
            this.ncol = 50;
        }
        else if (n == 2) {
            this.nrow = 100;
            this.ncol = 100;
        }
        else {
            this.nrow = 250;
            this.ncol = 250;
        }
        this.nC = this.nrow * this.ncol;
        this.C0 = new char[this.nC];
        for (int i = 0; i < this.nC; ++i) {
            this.C0[i] = '\0';
        }
        this.C1 = new char[this.nC];
        for (int j = 0; j < this.nC; ++j) {
            this.C1[j] = '\0';
        }
        this.generation = 0;
        this.patterns_file = null;
        this.patterns = null;
        this.lines = new String[5000];
        this.pats = new int[100];
        final int n2 = 0;
        this.npats = n2;
        this.nlines = n2;
        this.cur_pat = 0;
    }
    
    public int nrow() {
        return this.nrow;
    }
    
    public int ncol() {
        return this.ncol;
    }
    
    public int ncell() {
        return this.nC;
    }
    
    public char nhbd() {
        return this.nhbd;
    }
    
    public char lattice() {
        return this.lattice;
    }
    
    public int nstates0() {
        return this.nstates0;
    }
    
    public int nstates1() {
        return this.nstates1;
    }
    
    public int gen() {
        return this.generation;
    }
    
    public char cell0(final int n, final int n2) {
        if (n >= 0 && n < this.nrow && n2 >= 0 && n2 < this.ncol) {
            return this.C0[n * this.ncol + n2];
        }
        return '\0';
    }
    
    public char cell1(final int n, final int n2) {
        if (n >= 0 && n < this.nrow && n2 >= 0 && n2 < this.ncol) {
            return this.C1[n * this.ncol + n2];
        }
        return '\0';
    }
    
    public void set_CA(final CellAutom ca) {
        this.CA = ca;
    }
    
    public void reset_gen() {
        this.generation = 0;
    }
    
    public void update(final char[] c0) {
        this.C0 = c0;
    }
    
    public void update(final char[] c0, final char[] c2) {
        this.C0 = c0;
        this.C1 = c2;
    }
    
    public void set(final int n, final int n2, final int n3) {
        if (n >= 0 && n < this.nrow && n2 >= 0 && n2 < this.ncol && n3 >= 0 && n3 < this.nstates0 * this.nstates1) {
            this.C0[n * this.ncol + n2] = (char)(n3 % this.nstates0);
            this.C1[n * this.ncol + n2] = (char)(n3 / this.nstates0);
        }
    }
    
    public void clear() {
        for (int i = 0; i < this.nC; ++i) {
            this.C0[i] = '\0';
        }
        for (int j = 0; j < this.nC; ++j) {
            this.C1[j] = '\0';
        }
        this.generation = 0;
    }
    
    public boolean init(final CellAutom cellAutom) {
        if (!cellAutom.autom_built) {
            this.messages[0] = "No automata specified.";
            return false;
        }
        this.generation = 0;
        if (!cellAutom.init(this)) {
            return false;
        }
        this.messages[0] = "Generation: " + this.generation;
        return true;
    }
    
    public boolean next_gen(final CellAutom cellAutom) {
        if (!cellAutom.autom_built) {
            this.messages[0] = "No automata specified.";
            return false;
        }
        if (!cellAutom.next_gen(this, this.generation % 2)) {
            return false;
        }
        ++this.generation;
        this.messages[0] = "Generation: " + this.generation;
        return true;
    }
    
    private void bound() {
        this.min_col = this.ncol;
        this.max_col = -1;
        this.min_row = this.nrow;
        this.max_row = -1;
        for (int i = 0; i < this.nrow; ++i) {
            for (int j = 0; j < this.ncol; ++j) {
                final int n = i * this.ncol + j;
                if (this.C0[n] != '\0' || this.C1[n] != '\0') {
                    if (i < this.min_row) {
                        this.min_row = i;
                    }
                    if (i > this.max_row) {
                        this.max_row = i;
                    }
                    if (j < this.min_col) {
                        this.min_col = j;
                    }
                    if (j > this.max_col) {
                        this.max_col = j;
                    }
                }
            }
        }
    }
    
    public void center() {
        this.bound();
        if (this.max_row == -1) {
            return;
        }
        int n = (this.nrow - this.max_row + this.min_row) / 2 - this.min_row;
        int n2 = (this.ncol - this.max_col + this.min_col) / 2 - this.min_col;
        if (this.nhbd != 'V') {
            n -= n % 2;
            n2 -= n2 % 2;
        }
        final char[] c0 = new char[this.nC];
        final char[] c2 = new char[this.nC];
        for (int i = 0; i < this.nC; ++i) {
            c0[i] = '\0';
        }
        for (int j = 0; j < this.nC; ++j) {
            c2[j] = '\0';
        }
        for (int k = this.min_row; k <= this.max_row; ++k) {
            for (int l = this.min_col; l <= this.max_col; ++l) {
                c0[(k + n) * this.ncol + l + n2] = this.C0[k * this.ncol + l];
                c2[(k + n) * this.ncol + l + n2] = this.C1[k * this.ncol + l];
            }
        }
        this.C0 = c0;
        this.C1 = c2;
    }
    
    public char display_state(final int n, final int n2, final boolean b, final boolean b2) {
        return this.CA.display_state(this.cell0(n, n2), this.cell1(n, n2), b, b2);
    }
    
    public boolean out_pattern() {
        this.bound();
        FileOutputStream out;
        PrintStream printStream;
        try {
            out = new FileOutputStream("outpattern.txt");
            printStream = new PrintStream(out);
        }
        catch (Exception ex) {
            this.messages[0] = "Error opening file outpattern.txt";
            return false;
        }
        try {
            if (this.max_row == -1) {
                printStream.println(".");
            }
            for (int i = this.min_row; i <= this.max_row; ++i) {
                String string = "";
                for (int j = this.min_col; j <= this.max_col; ++j) {
                    final char cell0 = this.cell0(i, j);
                    string = string + " " + ".123456789ABCDEF".substring(cell0, cell0 + '\u0001');
                }
                printStream.println(string);
            }
            printStream.close();
            out.close();
            this.messages[0] = "Pattern written into outpattern.txt";
            return true;
        }
        catch (Exception ex2) {
            this.messages[0] = "Error writing to file outpattern.txt";
            return false;
        }
    }
    
    public boolean in_pattern() {
        this.nlines = 0;
        this.npats = 0;
        try {
            this.patterns_file = new FileReader("inpattern.txt");
            this.patterns = new BufferedReader(this.patterns_file);
        }
        catch (Exception ex) {
            this.messages[0] = "Error opening file inpattern.txt";
            this.patterns_file = null;
            this.patterns = null;
            return false;
        }
        while (true) {
            String line;
            try {
                line = this.patterns.readLine();
            }
            catch (Exception ex2) {
                this.messages[0] = "Error reading inpattern.txt.";
                this.patterns_file = null;
                this.patterns = null;
                return false;
            }
            if (line == null) {
                break;
            }
            line.trim();
            if (line.equals("")) {
                continue;
            }
            this.lines[this.nlines++] = line;
        }
        try {
            this.patterns.close();
            this.patterns_file.close();
            this.patterns_file = null;
            this.patterns = null;
        }
        catch (Exception ex3) {
            this.messages[0] = "Error closing patterns.txt";
            this.patterns_file = null;
            this.patterns = null;
            final int n = 0;
            this.npats = n;
            this.nlines = n;
            return false;
        }
        int n2 = 0;
        int n3 = -1;
        for (int i = 0; i < this.nlines; ++i) {
            if (this.lines[i].charAt(0) == '#') {
                if (n2 < 0) {
                    n2 = i;
                }
                n3 = i;
            }
            else if (n2 >= 0) {
                if (n3 != this.nlines - 1) {
                    if (n2 == 0 || n3 >= n2 + 2) {
                        this.pats[this.npats++] = n2;
                    }
                    n3 = (n2 = -1);
                }
            }
        }
        this.cur_pat = 0;
        return this.load_pattern(0);
    }
    
    public boolean load_pattern(final int n) {
        this.messages[0] = "";
        this.messages[1] = "";
        this.messages[2] = "";
        if (this.nlines == 0) {
            this.messages[0] = "Must open pattern file with InPat command.";
            return false;
        }
        if (n == -1 && this.cur_pat == 0) {
            this.messages[0] = "At the first pattern.";
            return false;
        }
        if (n == 1 && this.cur_pat == this.npats - 1) {
            this.messages[0] = "At the last pattern.";
            return false;
        }
        this.cur_pat += n;
        if (this.cur_pat < 0 || this.cur_pat >= this.npats) {
            this.messages[0] = "Error: cannot load patterns.";
            return false;
        }
        final int n2 = this.pats[this.cur_pat];
        final int n3 = (this.cur_pat == this.npats - 1) ? this.nlines : this.pats[this.cur_pat + 1];
        for (int i = n2; i < n3; ++i) {
            final String s = this.lines[i];
            if (i >= n2 + 3) {
                break;
            }
            if (s.charAt(0) != '#') {
                break;
            }
            this.messages[i - n2] = s;
        }
        this.clear();
        int n4 = 0;
        for (int j = n2; j < n3; ++j) {
            final String s2 = this.lines[j];
            if (s2.charAt(0) != '#') {
                int n5 = 0;
                for (int k = 0; k < s2.length(); ++k) {
                    final char char1 = s2.charAt(k);
                    if (char1 != ' ') {
                        char c;
                        if (char1 == '.') {
                            c = '\0';
                        }
                        else if (char1 >= '0' && char1 <= '9') {
                            c = (char)(char1 - '0');
                        }
                        else if (char1 >= 'a' && char1 <= 'f') {
                            c = (char)(char1 - 'W');
                        }
                        else if (char1 >= 'A' && char1 <= 'F') {
                            c = (char)(char1 - '7');
                        }
                        else {
                            c = '\u0001';
                        }
                        if (c >= this.nstates0 * this.nstates1) {
                            c = '\u0001';
                        }
                        this.C0[n4 * this.ncol + n5] = c;
                        ++n5;
                    }
                }
                ++n4;
            }
        }
        return true;
    }
}
