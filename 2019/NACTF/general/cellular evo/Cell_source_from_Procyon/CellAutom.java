// 
// Decompiled by Procyon v0.5.36
// 

public class CellAutom
{
    protected char nhbd;
    protected int nstates0;
    protected int nstates1;
    protected int nrow;
    protected int ncol;
    public boolean autom_built;
    public String[] messages;
    
    public CellAutom(final CellSpace cellSpace) {
        this.nhbd = cellSpace.nhbd();
        this.nstates0 = cellSpace.nstates0();
        this.nstates1 = cellSpace.nstates1();
        this.nrow = cellSpace.nrow();
        this.ncol = cellSpace.ncol();
        this.autom_built = false;
        this.messages = cellSpace.messages;
        cellSpace.set_CA(this);
    }
    
    public void reset() {
        this.autom_built = false;
    }
    
    public char display_state(final int n, final int n2, final boolean b, final boolean b2) {
        return (char)n;
    }
    
    public boolean init(final CellSpace cellSpace) {
        this.messages[0] = "No automata built";
        return false;
    }
    
    public boolean next_gen(final CellSpace cellSpace, final int n) {
        this.messages[0] = "No automata built";
        return false;
    }
}
