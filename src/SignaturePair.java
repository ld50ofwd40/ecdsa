import java.math.BigInteger;

public class SignaturePair {

    private BigInteger r;
    private BigInteger s;

    public void setR(BigInteger r) {
        this.r = r;
    }

    public void setS(BigInteger s) {
        this.s = s;
    }

    public BigInteger getR() {
        return r;
    }

    public BigInteger getS() {
        return s;
    }
}
