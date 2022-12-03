import java.math.BigInteger;

public class KeyPair {

    private ECPoint Q;
    private BigInteger d;

    public ECPoint getQ() {
        return Q;
    }

    public BigInteger getD() {
        return d;
    }

    public void setQ(ECPoint Q) {
        this.Q = Q;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

}
