// 
// Decompiled by Procyon v0.5.36
// 

public class CellAutomProg extends CellAutom
{
    public boolean tables_built;
    private String program;
    private String unparsed;
    private String cur_line;
    private CellParse P0;
    private CellParse P0_init;
    private CellParse P1;
    private CellParse P1_init;
    
    public CellAutomProg(final CellSpace cellSpace) {
        super(cellSpace);
        this.P0 = new CellParse(cellSpace, this.nstates0, false);
        this.P0_init = new CellParse(cellSpace, this.nstates0, true);
        this.P1 = new CellParse(cellSpace, this.nstates1, false);
        this.P1_init = new CellParse(cellSpace, this.nstates1, true);
        this.reset();
    }
    
    @Override
    public void reset() {
        this.autom_built = false;
        this.tables_built = false;
        this.P0.reset();
        this.P0_init.reset();
        this.P1.reset();
        this.P1_init.reset();
    }
    
    @Override
    public char display_state(final int n, final int n2, final boolean b, final boolean b2) {
        if (b2) {
            return (char)(b ? (this.nstates0 * n2 + n) : n2);
        }
        return (char)(b ? n : 0);
    }
    
    @Override
    public boolean init(final CellSpace cellSpace) {
        if (!this.autom_built) {
            this.messages[0] = "No automata built";
            return false;
        }
        this.P0_init.setup_planes(cellSpace.C0, cellSpace.C1);
        this.P1_init.setup_planes(cellSpace.C1, cellSpace.C0);
        this.messages[0] = "Initialization complete.";
        this.messages[1] = this.P0_init.init();
        this.messages[2] = this.P1_init.init();
        return true;
    }
    
    @Override
    public boolean next_gen(final CellSpace cellSpace, final int n) {
        if (!this.autom_built) {
            this.messages[0] = "No automata built";
            return false;
        }
        this.P0.setup_planes(cellSpace.C0, cellSpace.C1);
        this.P1.setup_planes(cellSpace.C1, cellSpace.C0);
        char[] array;
        if (this.tables_built) {
            array = this.P0.next_gen(n);
        }
        else {
            array = this.P0.next_gen_NT(n);
        }
        if (array == null) {
            this.messages[0] = this.P0.next_gen_error;
            return false;
        }
        char[] array2;
        if (this.tables_built) {
            array2 = this.P1.next_gen(n);
        }
        else {
            array2 = this.P1.next_gen_NT(n);
        }
        if (array2 == null) {
            this.messages[0] = this.P1.next_gen_error;
            return false;
        }
        cellSpace.update(array, array2);
        return true;
    }
    
    public boolean parse(String s) {
        this.program = s;
        this.unparsed = s;
        int i = 0;
        this.reset();
        int n = 0;
        int n2 = 0;
        CellParse cellParse = this.P0;
        cellParse.start_parse();
        while (!this.unparsed.equals("")) {
            final int index = this.unparsed.indexOf(10);
            if (index >= 0 && index < this.unparsed.length()) {
                s = this.unparsed.substring(0, index);
                this.unparsed = this.unparsed.substring(index + 1);
            }
            else {
                s = this.unparsed;
                this.unparsed = "";
            }
            ++i;
            final int index2 = s.indexOf(35);
            if (index2 >= 0) {
                s = s.substring(0, index2);
            }
            s = s.trim();
            if (s.equals("")) {
                continue;
            }
            s = s.replace('\u2019', '\'');
            s = s.replace('\u2013', '-');
            if (s.startsWith("---")) {
                if (n2 != 0) {
                    this.messages[0] = "Line + " + i + ": Unexpected ----- line.";
                    return false;
                }
                cellParse.end_parse();
                n2 = 1;
                cellParse = ((n != 0) ? this.P1_init : this.P1);
                cellParse.start_parse();
            }
            else if (s.startsWith("===")) {
                if (n != 0) {
                    this.messages[0] = "Line + " + i + ": Unexpected ===== line.";
                    return false;
                }
                cellParse.end_parse();
                if (n2 == 0) {
                    this.P1.start_parse();
                    this.P1.end_parse();
                }
                n2 = 0;
                n = 1;
                cellParse = this.P0_init;
                cellParse.start_parse();
            }
            else {
                final int index3 = s.indexOf(58);
                String substring;
                String substring2;
                if (index3 >= 0 && index3 < s.length()) {
                    substring = s.substring(0, index3);
                    substring2 = s.substring(index3 + 1);
                }
                else {
                    substring = "";
                    substring2 = s;
                }
                if (!cellParse.parse(substring, substring2)) {
                    this.messages[0] = "Line " + i + ": " + this.messages[0];
                    return false;
                }
                continue;
            }
        }
        cellParse.end_parse();
        if (n2 == 0 && n == 0) {
            this.P1.start_parse();
            this.P1.end_parse();
            n2 = 1;
        }
        if (n == 0) {
            this.P0_init.start_parse();
            this.P0_init.end_parse();
            n2 = 0;
        }
        if (n2 == 0) {
            this.P1_init.start_parse();
            this.P1_init.end_parse();
        }
        this.autom_built = true;
        this.messages[0] = "Program compiled successfully.";
        this.messages[1] = this.P0.code_to_string() + " :: " + this.P1.code_to_string();
        return true;
    }
    
    public boolean build_table() {
        this.tables_built = false;
        final boolean build_table = this.P0.build_table();
        this.messages[2] = "table0: " + this.P0.build_table_error;
        if (!build_table) {
            return false;
        }
        final boolean build_table2 = this.P1.build_table();
        final StringBuilder sb = new StringBuilder();
        final String[] messages = this.messages;
        final int n = 2;
        messages[n] = sb.append(messages[n]).append("  table1:").append(this.P1.build_table_error).toString();
        return build_table2 && (this.tables_built = true);
    }
}
