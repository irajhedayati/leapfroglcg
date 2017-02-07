package ir.iraj.rng;

/**
 * LCG random number generator using Leapfrog method
 * @author iraj
 */
public class LcgLeapfrog {

    private int previous;
    private int m;
    private int a;
    private int c;
    private int n;
    private boolean first = true;

    /**
     * LCG random number generator using Leapfrog method <br>
     * <code>x_n = a*x_{n-1}+c (mod m)</code>
     * @param x0 Initial value (seed)
     * @param m  Modulus number
     * @param a  Multiplier
     * @param c  Increment
     * @param n  number of generators
     */
    public LcgLeapfrog(int x0, int m, int a, int c, int n) {
        previous = x0;
        this.m = m;
        this.a = a;
        this.c = c;
        this.n = n;
    }

    public int next(){
        if(first){
            first = false;
            return previous;
        }
        int next = (int)(Math.pow(a,n) * previous + ((Math.pow(a,n) - 1)/(a - 1)) * c) % m;
        previous = next;
        return next;
    }
}