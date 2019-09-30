import java.util.Random;

// 
// Decompiled by Procyon v0.5.36
// 

public class CellRandom
{
    private int[] prob;
    private int nstates;
    private int last;
    private int sum;
    private Random R;
    
    public CellRandom(final int nstates, final int[] array) {
        this.nstates = nstates;
        this.prob = new int[this.nstates];
        this.sum = 0;
        this.last = 0;
        for (int i = 0; i < this.nstates; ++i) {
            this.prob[i] = array[i];
            this.sum += this.prob[i];
        }
        this.R = new Random();
    }
    
    public int nstates() {
        return this.nstates;
    }
    
    public int last() {
        return this.last;
    }
    
    public int next() {
        int nextInt;
        int last;
        for (nextInt = this.R.nextInt(this.sum), last = 0; last < this.nstates && nextInt >= this.prob[last]; nextInt -= this.prob[last], ++last) {}
        return this.last = last;
    }
}
