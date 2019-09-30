// 
// Decompiled by Procyon v0.5.36
// 

public class CellParse
{
    private char nhbd;
    private int nstates;
    private int nrow;
    private int ncol;
    private boolean init;
    private String[] vars;
    private int[] bounds;
    private int[] num_bits;
    private int[] values;
    private int num_vars;
    private String cur_line;
    private String[] messages;
    private CellInstr[] code;
    private int ncode;
    private char[] table;
    private int ntable;
    private CellRandom[] rng;
    private int nrng;
    private char[] C;
    private char[] Copp;
    public String eval_error;
    public String build_table_error;
    private String[] opnames;
    public String next_gen_error;
    
    public CellParse(final CellSpace cellSpace, final int nstates, final boolean init) {
        this.opnames = new String[] { "+", "-", "*", "/", "%", "==", "<=", ">=", "<", ">", "!=", "and", "or", "xor" };
        this.nhbd = cellSpace.nhbd();
        this.nstates = nstates;
        this.nrow = cellSpace.nrow();
        this.ncol = cellSpace.ncol();
        this.init = init;
        this.messages = cellSpace.messages;
        this.vars = new String[50];
        this.bounds = new int[50];
        this.num_bits = new int[50];
        this.values = new int[50];
        this.num_vars = 0;
        this.code = new CellInstr[1000];
        this.ncode = 0;
        this.ntable = 0;
        this.rng = new CellRandom[50];
        this.nrng = 0;
    }
    
    public void reset() {
        this.num_vars = 0;
        this.ncode = 0;
        this.ntable = 0;
        this.nrng = 0;
    }
    
    public void setup_planes(final char[] c, final char[] copp) {
        this.C = c;
        this.Copp = copp;
    }
    
    public int eval(final boolean b, final int n, final int n2, final int n3) {
        final int[] array = new int[50];
        int i = 0;
        this.eval_error = "";
        if (this.ncode == 0) {
            return 0;
        }
        for (int j = 0; j < this.ncode; ++j) {
            final String opcode = this.code[j].opcode();
            final int val = this.code[j].val();
            if (opcode.equals("end")) {
                return 0;
            }
            if (opcode.equals("push_num")) {
                array[i] = val;
                ++i;
            }
            else if (opcode.equals("push_var")) {
                if (b) {
                    array[i] = this.values[val];
                }
                else {
                    array[i] = this.get_value(this.vars[val], n, n2, n3);
                    if (array[i] == -1) {
                        this.eval_error = "bad variable: " + opcode;
                        return -1;
                    }
                }
                ++i;
            }
            else if (opcode.startsWith("ran")) {
                if (b) {
                    array[i] = this.values[val];
                }
                else {
                    final int k = opcode.charAt(3) - 'a';
                    if (k < 0 || k >= this.nrng) {
                        this.eval_error = "Bad rng number: " + k;
                        return -1;
                    }
                    array[i] = this.rng[k].next();
                }
                ++i;
            }
            else {
                if (i == 0) {
                    this.eval_error = "starting with an op";
                    return -1;
                }
                if (opcode.equals("pop")) {
                    --i;
                    return array[i];
                }
                if (opcode.equals("~")) {
                    array[i - 1] = ((array[i - 1] != 1) ? 1 : 0);
                }
                else if (opcode.equals("skip")) {
                    if (array[i - 1] != 0) {
                        --i;
                    }
                    else {
                        while (j < this.ncode && !this.code[j].opcode().equals("pop")) {
                            ++j;
                        }
                        if (j == this.ncode) {
                            this.eval_error = "Error in condition";
                            return -1;
                        }
                    }
                }
                else {
                    if (i <= 1) {
                        this.eval_error = "binary op, one value";
                        return -1;
                    }
                    if (opcode.equals("+")) {
                        final int[] array2 = array;
                        final int n4 = i - 2;
                        array2[n4] += array[i - 1];
                        --i;
                    }
                    else if (opcode.equals("-")) {
                        final int[] array3 = array;
                        final int n5 = i - 2;
                        array3[n5] -= array[i - 1];
                        --i;
                    }
                    else if (opcode.equals("*")) {
                        final int[] array4 = array;
                        final int n6 = i - 2;
                        array4[n6] *= array[i - 1];
                        --i;
                    }
                    else if (opcode.equals("/")) {
                        final int[] array5 = array;
                        final int n7 = i - 2;
                        array5[n7] /= array[i - 1];
                        --i;
                    }
                    else if (opcode.equals("%")) {
                        final int[] array6 = array;
                        final int n8 = i - 2;
                        array6[n8] %= array[i - 1];
                        --i;
                    }
                    else if (opcode.equals("==")) {
                        if (array[i - 2] == array[i - 1]) {
                            array[i - 2] = 1;
                        }
                        else {
                            array[i - 2] = 0;
                        }
                        --i;
                    }
                    else if (opcode.equals("!=")) {
                        if (array[i - 2] != array[i - 1]) {
                            array[i - 2] = 1;
                        }
                        else {
                            array[i - 2] = 0;
                        }
                        --i;
                    }
                    else if (opcode.equals("<")) {
                        if (array[i - 2] < array[i - 1]) {
                            array[i - 2] = 1;
                        }
                        else {
                            array[i - 2] = 0;
                        }
                        --i;
                    }
                    else if (opcode.equals("<=")) {
                        if (array[i - 2] <= array[i - 1]) {
                            array[i - 2] = 1;
                        }
                        else {
                            array[i - 2] = 0;
                        }
                        --i;
                    }
                    else if (opcode.equals(">")) {
                        if (array[i - 2] > array[i - 1]) {
                            array[i - 2] = 1;
                        }
                        else {
                            array[i - 2] = 0;
                        }
                        --i;
                    }
                    else if (opcode.equals(">=")) {
                        if (array[i - 2] >= array[i - 1]) {
                            array[i - 2] = 1;
                        }
                        else {
                            array[i - 2] = 0;
                        }
                        --i;
                    }
                    else if (opcode.equals("and")) {
                        if (array[i - 2] != 0 && array[i - 1] != 0) {
                            array[i - 2] = 1;
                        }
                        else {
                            array[i - 2] = 0;
                        }
                        --i;
                    }
                    else if (opcode.equals("or")) {
                        if (array[i - 2] != 0 || array[i - 1] != 0) {
                            array[i - 2] = 1;
                        }
                        else {
                            array[i - 2] = 0;
                        }
                        --i;
                    }
                    else if (opcode.equals("xor")) {
                        if (array[i - 2] != array[i - 1]) {
                            array[i - 2] = 1;
                        }
                        else {
                            array[i - 2] = 0;
                        }
                        --i;
                    }
                    else {
                        if (!opcode.equals("sel")) {
                            this.eval_error = "bad opcode " + opcode;
                            return -1;
                        }
                        if (i < val) {
                            this.eval_error = "sel nst: " + i + " v:" + val;
                            return -1;
                        }
                        int n9 = array[i - val];
                        if (n9 < 0 || n9 >= val - 1) {
                            n9 = 0;
                        }
                        array[i - val] = array[i - val + 1 + n9];
                        i -= val - 1;
                    }
                }
            }
        }
        if (i == 0 || i > 1) {
            return 0;
        }
        return array[0];
    }
    
    public int get_value(final String s, final int n, final int n2, final int n3) {
        if (s.equals("ROWP")) {
            return n % 2;
        }
        if (s.equals("COLP")) {
            return n2 % 2;
        }
        if (s.equals("ROW")) {
            return n;
        }
        if (s.equals("COL")) {
            return n2;
        }
        if (s.equals("PHASE")) {
            return n3;
        }
        final int n4 = (n == 0) ? (this.nrow - 1) : (n - 1);
        final int n5 = (n4 == 0) ? (this.nrow - 1) : (n4 - 1);
        final int n6 = (n == this.nrow - 1) ? 0 : (n + 1);
        final int n7 = (n6 == this.nrow - 1) ? 0 : (n6 + 1);
        final int n8 = (n2 == 0) ? (this.ncol - 1) : (n2 - 1);
        final int n9 = (n8 == 0) ? (this.ncol - 1) : (n8 - 1);
        final int n10 = (n2 == this.ncol - 1) ? 0 : (n2 + 1);
        final int n11 = (n10 == this.ncol - 1) ? 0 : (n10 + 1);
        if (s.equals("C")) {
            return this.C[n * this.ncol + n2];
        }
        if (s.equals("N")) {
            return this.C[n4 * this.ncol + n2];
        }
        if (s.equals("S")) {
            return this.C[n6 * this.ncol + n2];
        }
        if (s.equals("E")) {
            return this.C[n * this.ncol + n10];
        }
        if (s.equals("W")) {
            return this.C[n * this.ncol + n8];
        }
        if (s.equals("C'")) {
            return this.Copp[n * this.ncol + n2];
        }
        if (s.equals("N'")) {
            return this.Copp[n4 * this.ncol + n2];
        }
        if (s.equals("S'")) {
            return this.Copp[n6 * this.ncol + n2];
        }
        if (s.equals("E'")) {
            return this.Copp[n * this.ncol + n10];
        }
        if (s.equals("W'")) {
            return this.Copp[n * this.ncol + n8];
        }
        if (s.equals("NN")) {
            return this.C[n5 * this.ncol + n2];
        }
        if (s.equals("SS")) {
            return this.C[n7 * this.ncol + n2];
        }
        if (s.equals("EE")) {
            return this.C[n * this.ncol + n11];
        }
        if (s.equals("WW")) {
            return this.C[n * this.ncol + n9];
        }
        if (s.equals("NN'")) {
            return this.Copp[n5 * this.ncol + n2];
        }
        if (s.equals("SS'")) {
            return this.Copp[n7 * this.ncol + n2];
        }
        if (s.equals("EE'")) {
            return this.Copp[n * this.ncol + n11];
        }
        if (s.equals("WW'")) {
            return this.Copp[n * this.ncol + n9];
        }
        if (this.nhbd == 'H') {
            if (s.equals("NE")) {
                return (n2 % 2 == 0) ? this.C[n4 * this.ncol + n10] : this.C[n * this.ncol + n10];
            }
            if (s.equals("NW")) {
                return (n2 % 2 == 0) ? this.C[n4 * this.ncol + n8] : this.C[n * this.ncol + n8];
            }
            if (s.equals("SE")) {
                return (n2 % 2 == 0) ? this.C[n * this.ncol + n10] : this.C[n6 * this.ncol + n10];
            }
            if (s.equals("SW")) {
                return (n2 % 2 == 0) ? this.C[n * this.ncol + n8] : this.C[n6 * this.ncol + n8];
            }
            if (s.equals("NE'")) {
                return (n2 % 2 == 0) ? this.C[n4 * this.ncol + n10] : this.C[n * this.ncol + n10];
            }
            if (s.equals("NW'")) {
                return (n2 % 2 == 0) ? this.C[n4 * this.ncol + n8] : this.C[n * this.ncol + n8];
            }
            if (s.equals("SE'")) {
                return (n2 % 2 == 0) ? this.C[n * this.ncol + n10] : this.C[n6 * this.ncol + n10];
            }
            if (s.equals("SW'")) {
                return (n2 % 2 == 0) ? this.C[n * this.ncol + n8] : this.C[n6 * this.ncol + n8];
            }
        }
        else {
            if (s.equals("NE")) {
                return this.C[n4 * this.ncol + n10];
            }
            if (s.equals("NW")) {
                return this.C[n4 * this.ncol + n8];
            }
            if (s.equals("SE")) {
                return this.C[n6 * this.ncol + n10];
            }
            if (s.equals("SW")) {
                return this.C[n6 * this.ncol + n8];
            }
            if (s.equals("NE'")) {
                return this.Copp[n4 * this.ncol + n10];
            }
            if (s.equals("NW'")) {
                return this.Copp[n4 * this.ncol + n8];
            }
            if (s.equals("SE'")) {
                return this.Copp[n6 * this.ncol + n10];
            }
            if (s.equals("SW'")) {
                return this.Copp[n6 * this.ncol + n8];
            }
        }
        if (s.equals("CW") || s.equals("CCW") || s.equals("OPP") || s.equals("CW'") || s.equals("CCW'") || s.equals("OPP'")) {
            int n12;
            int n13;
            int n14;
            int n15;
            if (n3 == 0) {
                n12 = n - n % 2;
                n13 = n2 - n2 % 2;
                n14 = n12 + 1;
                n15 = n13 + 1;
            }
            else {
                if (n == 0 || n == this.nrow - 1) {
                    n12 = this.nrow - 1;
                    n14 = 0;
                }
                else {
                    n12 = n - (n + 1) % 2;
                    n14 = n12 + 1;
                }
                if (n2 == 0 || n2 == this.ncol - 1) {
                    n13 = this.ncol - 1;
                    n15 = 0;
                }
                else {
                    n13 = n2 - (n2 + 1) % 2;
                    n15 = n13 + 1;
                }
            }
            if (s.equals("CW")) {
                return this.C[((n2 == n13) ? n12 : n14) * this.ncol + ((n == n12) ? n15 : n13)];
            }
            if (s.equals("CCW")) {
                return this.C[((n2 == n15) ? n12 : n14) * this.ncol + ((n == n12) ? n13 : n15)];
            }
            if (s.equals("OPP")) {
                return this.C[((n == n12) ? n14 : n12) * this.ncol + ((n2 == n13) ? n15 : n13)];
            }
            if (s.equals("CW'")) {
                return this.Copp[((n2 == n13) ? n12 : n14) * this.ncol + ((n == n12) ? n15 : n13)];
            }
            if (s.equals("CCW'")) {
                return this.Copp[((n2 == n15) ? n12 : n14) * this.ncol + ((n == n12) ? n13 : n15)];
            }
            if (s.equals("OPP'")) {
                return this.Copp[((n == n12) ? n14 : n12) * this.ncol + ((n2 == n13) ? n15 : n13)];
            }
        }
        if (s.equals("BN")) {
            return (n == 0) ? 1 : 0;
        }
        if (s.equals("BS")) {
            return (n == this.nrow - 1) ? 1 : 0;
        }
        if (s.equals("BW")) {
            return (n2 == 0) ? 1 : 0;
        }
        if (s.equals("BE")) {
            return (n2 == this.ncol - 1) ? 1 : 0;
        }
        if (s.equals("sum4") || s.equals("sum5")) {
            int n16 = this.C[n4 * this.ncol + n2] + this.C[n * this.ncol + n8] + this.C[n * this.ncol + n10] + this.C[n6 * this.ncol + n2];
            if (s.equals("sum5")) {
                n16 += this.C[n * this.ncol + n2];
            }
            return n16;
        }
        if (s.equals("sum6") || s.equals("sum7")) {
            int n17 = this.C[n4 * this.ncol + n2] + this.C[n6 * this.ncol + n2] + this.C[n * this.ncol + n8] + this.C[n * this.ncol + n10] + ((n2 % 2 == 0) ? (this.C[n4 * this.ncol + n8] + this.C[n4 * this.ncol + n10]) : (this.C[n6 * this.ncol + n8] + this.C[n6 * this.ncol + n10]));
            if (s.equals("sum7")) {
                n17 += this.C[n * this.ncol + n2];
            }
            return n17;
        }
        if (s.equals("sum8") || s.equals("sum9")) {
            int n18 = this.C[n4 * this.ncol + n8] + this.C[n4 * this.ncol + n2] + this.C[n4 * this.ncol + n10] + this.C[n * this.ncol + n8] + this.C[n * this.ncol + n10] + this.C[n6 * this.ncol + n8] + this.C[n6 * this.ncol + n2] + this.C[n6 * this.ncol + n10];
            if (s.equals("sum9")) {
                n18 += this.C[n * this.ncol + n2];
            }
            return n18;
        }
        if (s.startsWith("C6_") || s.startsWith("C7_")) {
            final int n19 = s.charAt(3) - '0';
            final int n20 = s.charAt(1) - '0';
            int n21 = 0;
            if (this.C[n4 * this.ncol + n2] == n19) {
                ++n21;
            }
            if (this.C[n6 * this.ncol + n2] == n19) {
                ++n21;
            }
            if (this.C[n * this.ncol + n8] == n19) {
                ++n21;
            }
            if (this.C[n * this.ncol + n10] == n19) {
                ++n21;
            }
            if (n2 % 2 == 0) {
                if (this.C[n4 * this.ncol + n8] == n19) {
                    ++n21;
                }
                if (this.C[n4 * this.ncol + n10] == n19) {
                    ++n21;
                }
            }
            else {
                if (this.C[n6 * this.ncol + n8] == n19) {
                    ++n21;
                }
                if (this.C[n6 * this.ncol + n10] == n19) {
                    ++n21;
                }
            }
            if (n20 == 7 && this.C[n * this.ncol + n2] == n19) {
                ++n21;
            }
            return n21;
        }
        if (s.charAt(0) != 'C' || s.charAt(2) != '_') {
            return -1;
        }
        final int n22 = s.charAt(3) - '0';
        final int n23 = s.charAt(1) - '0';
        int n24 = 0;
        if (this.C[n4 * this.ncol + n2] == n22) {
            ++n24;
        }
        if (this.C[n * this.ncol + n8] == n22) {
            ++n24;
        }
        if (this.C[n * this.ncol + n10] == n22) {
            ++n24;
        }
        if (this.C[n6 * this.ncol + n2] == n22) {
            ++n24;
        }
        if (n23 == 5 && this.C[n * this.ncol + n2] == n22) {
            ++n24;
        }
        if (n23 <= 5) {
            return n24;
        }
        if (this.C[n4 * this.ncol + n8] == n22) {
            ++n24;
        }
        if (this.C[n4 * this.ncol + n10] == n22) {
            ++n24;
        }
        if (this.C[n6 * this.ncol + n8] == n22) {
            ++n24;
        }
        if (this.C[n6 * this.ncol + n10] == n22) {
            ++n24;
        }
        if (n23 == 9 && this.C[n * this.ncol + n2] == n22) {
            ++n24;
        }
        return n24;
    }
    
    public boolean build_table() {
        if (this.nstates == 1) {
            return true;
        }
        for (int i = 0; i < this.num_vars; ++i) {
            int j = this.nstates - 1;
            final String s = this.vars[i];
            if (s.equals("sum4")) {
                j *= 4;
            }
            else if (s.equals("sum5")) {
                j *= 5;
            }
            else if (s.equals("sum6")) {
                j *= 6;
            }
            else if (s.equals("sum7")) {
                j *= 7;
            }
            else if (s.equals("sum8")) {
                j *= 8;
            }
            else if (s.equals("sum9")) {
                j *= 9;
            }
            else if (s.equals("BN")) {
                j = 1;
            }
            else if (s.equals("BS")) {
                j = 1;
            }
            else if (s.equals("BE")) {
                j = 1;
            }
            else if (s.equals("BW")) {
                j = 1;
            }
            else if (s.equals("ROWP")) {
                j = 1;
            }
            else if (s.equals("COLP")) {
                j = 1;
            }
            else if (s.equals("PHASE")) {
                j = 1;
            }
            else if (s.startsWith("C4")) {
                j = 4;
            }
            else if (s.startsWith("C5")) {
                j = 5;
            }
            else if (s.startsWith("C6")) {
                j = 6;
            }
            else if (s.startsWith("C7")) {
                j = 7;
            }
            else if (s.startsWith("C8")) {
                j = 8;
            }
            else if (s.startsWith("C9")) {
                j = 9;
            }
            else if (s.startsWith("ran")) {
                final int k = s.charAt(3) - 'a';
                if (k < 0 || k >= this.nrng) {
                    this.build_table_error = "Bad random number generator: " + k;
                    return false;
                }
                j = this.rng[k].nstates() - 1;
            }
            this.bounds[i] = 1;
            this.num_bits[i] = 0;
            while (j > 0) {
                final int[] bounds = this.bounds;
                final int n = i;
                bounds[n] <<= 1;
                final int[] num_bits = this.num_bits;
                final int n2 = i;
                ++num_bits[n2];
                j >>= 1;
            }
        }
        this.ntable = 1;
        int n3 = 0;
        for (int l = 0; l < this.num_vars; ++l) {
            this.ntable *= this.bounds[l];
            n3 += this.num_bits[l];
        }
        if (n3 > 14) {
            this.build_table_error = "Table too large.";
            return false;
        }
        this.table = new char[this.ntable];
        for (int n4 = 0; n4 < this.ntable; ++n4) {
            this.table[n4] = '\0';
        }
        for (int n5 = 0; n5 < this.num_vars; ++n5) {
            this.values[n5] = 0;
        }
        for (int n6 = 0; n6 < this.ntable; ++n6) {
            final int eval = this.eval(true, 0, 0, 0);
            if (eval == -1 || eval >= this.nstates) {
                this.build_table_error = "build table failed at ";
                for (int n7 = 0; n7 < this.num_vars; ++n7) {
                    this.build_table_error = this.build_table_error + this.vars[n7] + " " + this.values[n7] + " ";
                }
                return false;
            }
            this.table[n6] = (char)eval;
            int n8;
            for (n8 = this.num_vars - 1; n8 >= 0; --n8) {
                final int[] values = this.values;
                final int n9 = n8;
                ++values[n9];
                if (this.values[n8] < this.bounds[n8]) {
                    break;
                }
                this.values[n8] = 0;
            }
            if (n8 == 0 && this.values[n8] == 0) {
                this.build_table_error = "Build Table overflow.";
                return false;
            }
        }
        this.build_table_error = this.table_to_string();
        return true;
    }
    
    public String table_to_string() {
        String string = "";
        for (int i = 0; i < this.ntable; ++i) {
            string += (char)('0' + this.table[i]);
        }
        return string;
    }
    
    public String init() {
        if (this.ncode == 0) {
            return "";
        }
        for (int i = 0; i < this.nrow; ++i) {
            for (int j = 0; j < this.ncol; ++j) {
                final int eval = this.eval(false, i, j, 0);
                if (eval == -1) {
                    return "eval error at " + i + "," + j + this.eval_error;
                }
                this.C[i * this.ncol + j] = (char)eval;
            }
        }
        return "";
    }
    
    public void start_parse() {
        this.ncode = 0;
        this.num_vars = 0;
    }
    
    public void end_parse() {
        this.code[this.ncode] = new CellInstr("end");
        ++this.ncode;
    }
    
    public boolean parse(final String s, final String s2) {
        if (s.equals("")) {
            final String parse_line = this.parse_line(s2);
            if (!parse_line.equals("")) {
                this.messages[0] = parse_line;
                return false;
            }
            this.code[this.ncode] = new CellInstr("pop");
            ++this.ncode;
        }
        else {
            final String parse_line2 = this.parse_line(s);
            if (!parse_line2.equals("")) {
                this.messages[0] = parse_line2;
                return false;
            }
            this.code[this.ncode] = new CellInstr("skip");
            ++this.ncode;
            final String parse_line3 = this.parse_line(s2);
            if (!parse_line3.equals("")) {
                this.messages[0] = parse_line3;
                return false;
            }
            this.code[this.ncode] = new CellInstr("pop");
            ++this.ncode;
        }
        return true;
    }
    
    private String parse_line(String trim) {
        final String[] array = new String[50];
        trim = trim.trim();
        if (trim.equals("")) {
            return "Syntax error";
        }
        array[0] = "start";
        int i = 1;
        this.cur_line = trim;
        int n = 0;
        int n2 = -1;
        while (true) {
            this.cur_line = this.cur_line.trim();
            if (this.cur_line.equals("")) {
                if (n == 0) {
                    return "syntax error";
                }
                if (i == 0) {
                    return "syntax error";
                }
                while (i > 1) {
                    final String s = array[i - 1];
                    if (s.equals("(") || s.equals(")") || s.equals(",")) {
                        return "parenthesis error";
                    }
                    this.code[this.ncode] = new CellInstr(array[i - 1]);
                    ++this.ncode;
                    --i;
                }
                if (!array[0].equals("start")) {
                    return "syntax error";
                }
                return "";
            }
            else if (n == 0) {
                final int get_num;
                if ((get_num = this.get_num()) >= 0) {
                    this.code[this.ncode] = new CellInstr("push_num", get_num);
                    ++this.ncode;
                    n = 1;
                }
                else {
                    final String get_var;
                    if ((get_var = this.get_var()) != "") {
                        if (!this.check_var(get_var)) {
                            return "cannot use " + get_var + " in this context";
                        }
                        this.code[this.ncode] = new CellInstr("push_var", this.find_var(get_var));
                        ++this.ncode;
                        n = 1;
                    }
                    else {
                        final String get_func;
                        if ((get_func = this.get_func()) != "") {
                            if (get_func.equals("ran")) {
                                final int get_rng = this.get_rng();
                                if (get_rng == -1) {
                                    return "Error in ran() arguments: " + this.cur_line;
                                }
                                final String string = get_func + (char)(97 + get_rng);
                                this.code[this.ncode] = new CellInstr(string, this.find_var(string));
                                ++this.ncode;
                                n = 1;
                            }
                            else {
                                if (!get_func.equals("sel")) {
                                    return "syntax error 1: " + this.cur_line;
                                }
                                n2 = 0;
                                array[i] = get_func;
                                ++i;
                                n = 0;
                            }
                        }
                        else {
                            final String get_unop;
                            if ((get_unop = this.get_unop()) != "") {
                                array[i] = get_unop;
                                ++i;
                                n = 0;
                            }
                            else {
                                if (!this.get_left()) {
                                    return "syntax error 2: " + this.cur_line;
                                }
                                array[i] = "(";
                                ++i;
                                n = 0;
                            }
                        }
                    }
                }
            }
            else if (this.get_comma()) {
                if (n2 == -1) {
                    return "unexpected comma: " + this.cur_line;
                }
                ++n2;
                while (i > 0 && get_priority(",") <= get_priority(array[i - 1])) {
                    this.code[this.ncode] = new CellInstr(array[i - 1]);
                    ++this.ncode;
                    --i;
                }
                n = 0;
            }
            else if (this.get_right()) {
                while (i > 0 && get_priority(")") < get_priority(array[i - 1])) {
                    this.code[this.ncode] = new CellInstr(array[i - 1]);
                    ++this.ncode;
                    --i;
                }
                if (i == 0) {
                    return "parenthesis error: " + this.cur_line;
                }
                if (array[i - 1].equals("(")) {
                    --i;
                    n = 1;
                }
                else {
                    if (n2 == -1) {
                        return "syntax error 3: " + this.cur_line;
                    }
                    ++n2;
                    if (!array[i - 1].equals("sel")) {
                        return "parenthesis error: " + this.cur_line;
                    }
                    if (n2 <= 2) {
                        return "sel needs at least three arguments.";
                    }
                    this.code[this.ncode] = new CellInstr("sel", n2);
                    ++this.ncode;
                    --i;
                    n2 = -1;
                    n = 1;
                }
            }
            else {
                final String get_binop;
                if ((get_binop = this.get_binop()) == "") {
                    return "syntax error 3: " + this.cur_line;
                }
                while (i > 0 && get_priority(get_binop) <= get_priority(array[i - 1])) {
                    this.code[this.ncode] = new CellInstr(array[i - 1]);
                    ++this.ncode;
                    --i;
                }
                array[i] = get_binop;
                ++i;
                n = 0;
            }
        }
    }
    
    private boolean get_left() {
        if (this.cur_line.charAt(0) != '(') {
            return false;
        }
        this.cur_line = this.cur_line.substring(1);
        return true;
    }
    
    private boolean get_right() {
        if (this.cur_line.charAt(0) != ')') {
            return false;
        }
        this.cur_line = this.cur_line.substring(1);
        return true;
    }
    
    private boolean get_comma() {
        if (this.cur_line.charAt(0) != ',') {
            return false;
        }
        this.cur_line = this.cur_line.substring(1);
        return true;
    }
    
    private int get_num() {
        final char char1 = this.cur_line.charAt(0);
        if (char1 < '0' || char1 > '9') {
            return -1;
        }
        int n = 0;
        int i;
        for (i = 0; i < this.cur_line.length(); ++i) {
            final char char2 = this.cur_line.charAt(i);
            if (char2 < '0') {
                break;
            }
            if (char2 > '9') {
                break;
            }
            n = 10 * n + (char2 - '0');
        }
        this.cur_line = this.cur_line.substring(i);
        return n;
    }
    
    private String get_func() {
        if (this.cur_line.startsWith("ran(")) {
            this.cur_line = this.cur_line.substring(4);
            return "ran";
        }
        if (this.cur_line.startsWith("sel(")) {
            this.cur_line = this.cur_line.substring(4);
            return "sel";
        }
        return "";
    }
    
    private String get_var() {
        final String[] array = { "ROWP", "COLP", "ROW", "COL", "PHASE", "C4_0", "C4_1", "C4_2", "C4_3", "C4_4", "C4_5", "C4_6", "C4_7", "C5_0", "C5_1", "C5_2", "C5_3", "C5_4", "C5_5", "C5_6", "C5_7", "C6_0", "C6_1", "C6_2", "C6_3", "C6_4", "C6_5", "C6_6", "C6_7", "C7_0", "C7_1", "C7_2", "C7_3", "C7_4", "C7_5", "C7_6", "C7_7", "C8_0", "C8_1", "C8_2", "C8_3", "C8_4", "C8_5", "C8_6", "C8_7", "C9_0", "C9_1", "C9_2", "C9_3", "C9_4", "C9_5", "C9_6", "C9_7", "CW'", "CW", "CCW'", "CCW", "OPP'", "OPP", "NE'", "NE", "NW'", "NW", "SE'", "SE", "SW'", "SW", "NN'", "NN", "SS'", "SS", "EE'", "EE", "WW", "EE'", "N'", "N", "S'", "S", "E'", "E", "W'", "W", "C'", "C", "sum4", "sum5", "sum6", "sum7", "sum8", "sum9", "BN", "BS", "BE", "BW" };
        for (int i = 0; i < array.length; ++i) {
            if (this.cur_line.startsWith(array[i])) {
                final String s = array[i];
                this.cur_line = this.cur_line.substring(s.length());
                return s;
            }
        }
        return "";
    }
    
    private boolean check_var(final String s) {
        if (!this.init && (s.equals("ROW") || s.equals("COL"))) {
            return false;
        }
        if (this.nhbd == 'V') {
            if (s.equals("CW") || s.equals("CCW") || s.equals("OPP")) {
                return false;
            }
            if (s.equals("CW'") || s.equals("CCW'") || s.equals("OPP'")) {
                return false;
            }
            if (s.equals("sum6'") || s.equals("sum7'")) {
                return false;
            }
            if (s.startsWith("C6") || s.startsWith("C7")) {
                return false;
            }
        }
        if (this.nhbd == 'M') {
            if (s.equals("N") || s.equals("S") || s.equals("E") || s.equals("W")) {
                return false;
            }
            if (s.equals("N'") || s.equals("S'") || s.equals("E'") || s.equals("W'")) {
                return false;
            }
            if (s.equals("NE") || s.equals("NW") || s.equals("SE") || s.equals("SW")) {
                return false;
            }
            if (s.equals("NE'") || s.equals("NW'") || s.equals("SE'") || s.equals("SW'")) {
                return false;
            }
            if (s.equals("NN") || s.equals("SS") || s.equals("EE") || s.equals("WW")) {
                return false;
            }
            if (s.equals("NN'") || s.equals("SS'") || s.equals("EE'") || s.equals("WW'")) {
                return false;
            }
            if (s.startsWith("sum")) {
                return false;
            }
            if (s.startsWith("C4") || s.startsWith("C5")) {
                return false;
            }
            if (s.startsWith("C6") || s.startsWith("C7")) {
                return false;
            }
            if (s.startsWith("C8") || s.startsWith("C9")) {
                return false;
            }
        }
        if (this.nhbd == 'H') {
            if (s.equals("E") || s.equals("W")) {
                return false;
            }
            if (s.equals("E'") || s.equals("W'")) {
                return false;
            }
            if (s.equals("EE") || s.equals("WW")) {
                return false;
            }
            if (s.equals("EE'") || s.equals("WW'")) {
                return false;
            }
            if (s.equals("CW") || s.equals("CCW") || s.equals("OPP")) {
                return false;
            }
            if (s.equals("CW'") || s.equals("CCW'") || s.equals("OPP'")) {
                return false;
            }
            if (s.equals("sum4'") || s.equals("sum5'")) {
                return false;
            }
            if (s.equals("sum8'") || s.equals("sum9'")) {
                return false;
            }
            if (s.startsWith("C4") || s.startsWith("C5")) {
                return false;
            }
            if (s.startsWith("C8") || s.startsWith("C9")) {
                return false;
            }
        }
        return true;
    }
    
    private int find_var(final String s) {
        for (int i = 0; i < this.num_vars; ++i) {
            if (s.equals(this.vars[i])) {
                return i;
            }
        }
        this.vars[this.num_vars] = s;
        ++this.num_vars;
        return this.num_vars - 1;
    }
    
    private String get_unop() {
        if (!this.cur_line.startsWith("~")) {
            return "";
        }
        this.cur_line = this.cur_line.substring(1);
        return "~";
    }
    
    private String get_binop() {
        for (int i = 0; i < this.opnames.length; ++i) {
            if (this.cur_line.startsWith(this.opnames[i])) {
                final String s = this.opnames[i];
                this.cur_line = this.cur_line.substring(s.length());
                return s;
            }
        }
        return "";
    }
    
    private int get_rng() {
        final int[] array = new int[50];
        int i = 0;
        while (i < 50) {
            this.cur_line = this.cur_line.trim();
            final int get_num;
            if ((get_num = this.get_num()) == -1) {
                return -1;
            }
            array[i] = get_num;
            this.cur_line = this.cur_line.trim();
            if (this.get_right()) {
                if (i < 1) {
                    return -1;
                }
                this.rng[this.nrng] = new CellRandom(i + 1, array);
                ++this.nrng;
                this.vars[this.num_vars] = "ran" + (char)(97 + this.nrng - 1);
                this.values[this.num_vars] = 0;
                ++this.num_vars;
                return this.nrng - 1;
            }
            else {
                if (!this.get_comma()) {
                    return -1;
                }
                ++i;
            }
        }
        return -1;
    }
    
    public static int get_priority(final String s) {
        if (s.equals("start")) {
            return 1;
        }
        if (s.equals("end")) {
            return 1;
        }
        if (s.equals("(")) {
            return 2;
        }
        if (s.equals("sel")) {
            return 2;
        }
        if (s.equals(")")) {
            return 2;
        }
        if (s.equals(",")) {
            return 3;
        }
        if (s.equals("or")) {
            return 4;
        }
        if (s.equals("xor")) {
            return 4;
        }
        if (s.equals("and")) {
            return 5;
        }
        if (s.equals("==")) {
            return 6;
        }
        if (s.equals("!=")) {
            return 6;
        }
        if (s.equals("<")) {
            return 6;
        }
        if (s.equals("<=")) {
            return 6;
        }
        if (s.equals(">")) {
            return 6;
        }
        if (s.equals(">=")) {
            return 6;
        }
        if (s.equals("+")) {
            return 7;
        }
        if (s.equals("-")) {
            return 7;
        }
        if (s.equals("*")) {
            return 8;
        }
        if (s.equals("/")) {
            return 8;
        }
        if (s.equals("%")) {
            return 8;
        }
        if (s.equals("~")) {
            return 9;
        }
        return -1;
    }
    
    public String code_to_string() {
        String s = "";
        for (int i = 0; i < this.ncode; ++i) {
            if (this.code[i] == null) {
                s += "null ";
            }
            else {
                final String opcode = this.code[i].opcode();
                final int val = this.code[i].val();
                if (opcode.equals("push_var")) {
                    s = s + this.vars[val] + " ";
                }
                else if (opcode.equals("push_num")) {
                    s = s + "" + val + " ";
                }
                else if (opcode.startsWith("ran")) {
                    s = s + opcode + " ";
                }
                else if (opcode.equals("sel")) {
                    s = s + "sel" + val + " ";
                }
                else {
                    s = s + opcode + " ";
                }
            }
        }
        return s;
    }
    
    public char[] next_gen(final int n) {
        final int n2 = this.nrow * this.ncol;
        this.next_gen_error = "";
        if (this.nhbd == 'M') {
            return this.next_gen_m(n);
        }
        final char[] array = new char[n2];
        for (int i = 0; i < n2; ++i) {
            array[i] = '\0';
        }
        for (int j = 0; j < this.nrow; ++j) {
            for (int k = 0; k < this.ncol; ++k) {
                int n3 = 0;
                for (int l = 0; l < this.num_vars; ++l) {
                    final int n4 = n3 << this.num_bits[l];
                    final String s = this.vars[l];
                    if (s.startsWith("ran")) {
                        n3 = n4 + this.rng[s.charAt(3) - 'a'].next();
                    }
                    else {
                        final int get_value = this.get_value(s, j, k, n);
                        if (get_value == -1) {
                            this.next_gen_error = "next gen error at row = " + j + "   col = " + k;
                            return null;
                        }
                        n3 = n4 + get_value;
                    }
                }
                if (n3 < this.ntable) {
                    array[j * this.ncol + k] = this.table[n3];
                }
            }
        }
        return array;
    }
    
    private char[] next_gen_m(final int n) {
        final int n2 = this.nrow * this.ncol;
        this.next_gen_error = "";
        final char[] array = new char[n2];
        for (int i = 0; i < n2; ++i) {
            array[i] = '\0';
        }
        for (int j = n; j < this.nrow; j += 2) {
            for (int k = n; k < this.ncol; k += 2) {
                for (int l = 0; l < 2; ++l) {
                    for (int n3 = 0; n3 < 2; ++n3) {
                        int m = j + l;
                        if (m == this.nrow) {
                            m = 0;
                        }
                        int i2 = k + n3;
                        if (i2 == this.ncol) {
                            i2 = 0;
                        }
                        int n4 = 0;
                        for (int n5 = 0; n5 < this.num_vars; ++n5) {
                            final int n6 = n4 << this.num_bits[n5];
                            final String s = this.vars[n5];
                            if (s.startsWith("ran")) {
                                final int n7 = s.charAt(3) - 'a';
                                if (m == j && i2 == k) {
                                    n4 = n6 + this.rng[n7].next();
                                }
                                else {
                                    n4 = n6 + this.rng[n7].last();
                                }
                            }
                            else {
                                final int get_value = this.get_value(s, m, i2, n);
                                if (get_value == -1) {
                                    this.next_gen_error = "next gen error at row = " + m + "   col = " + i2;
                                    return null;
                                }
                                n4 = n6 + get_value;
                            }
                        }
                        if (n4 < this.ntable) {
                            array[m * this.ncol + i2] = this.table[n4];
                        }
                    }
                }
            }
        }
        return array;
    }
    
    public char[] next_gen_NT(final int n) {
        final int n2 = this.nrow * this.ncol;
        this.next_gen_error = "";
        final char[] array = new char[n2];
        for (int i = 0; i < n2; ++i) {
            array[i] = '\0';
        }
        for (int j = 0; j < this.nrow; ++j) {
            for (int k = 0; k < this.ncol; ++k) {
                final int eval = this.eval(false, j, k, n);
                if (eval == -1) {
                    this.next_gen_error = "next gen error at row = " + j + "   col = " + k;
                    return null;
                }
                array[j * this.ncol + k] = (char)eval;
            }
        }
        return array;
    }
}
