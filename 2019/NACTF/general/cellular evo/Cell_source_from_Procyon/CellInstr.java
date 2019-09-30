// 
// Decompiled by Procyon v0.5.36
// 

public class CellInstr
{
    private String instr_opcode;
    private int instr_val;
    
    public CellInstr() {
        this.instr_opcode = "";
        this.instr_val = 0;
    }
    
    public CellInstr(final String instr_opcode) {
        this.instr_opcode = instr_opcode;
        this.instr_val = 0;
    }
    
    public CellInstr(final String instr_opcode, final int instr_val) {
        this.instr_opcode = instr_opcode;
        this.instr_val = instr_val;
    }
    
    public void set(final String instr_opcode) {
        this.instr_opcode = instr_opcode;
        this.instr_val = 0;
    }
    
    public void set(final String instr_opcode, final int instr_val) {
        this.instr_opcode = instr_opcode;
        this.instr_val = instr_val;
    }
    
    public String opcode() {
        return this.instr_opcode;
    }
    
    public int val() {
        return this.instr_val;
    }
}
