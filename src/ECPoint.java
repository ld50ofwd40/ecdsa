import java.math.BigInteger;

public class ECPoint {

    private BigInteger x, y;
    private BigInteger p, a;

    public ECPoint(BigInteger p, BigInteger a) {
        x = BigInteger.valueOf(0);
        y = BigInteger.valueOf(0);
        this.p = p;
        this.a = a;
    }

    public ECPoint(BigInteger x, BigInteger y, BigInteger p, BigInteger a) {
        this.x = x;
        this.y = y;
        this.p = p;
        this.a = a;
    }

    public ECPoint(int x, int y, int p, int a) {
        this.x = BigInteger.valueOf(x);
        this.y = BigInteger.valueOf(y);
        this.p = BigInteger.valueOf(p);
        this.a = BigInteger.valueOf(a);
    }

    public ECPoint add(ECPoint Q) {
        BigInteger xp = this.x;
        BigInteger yp = this.y;
        BigInteger xq = Q.getX();
        BigInteger yq = Q.getY();

        if (xp.equals(BigInteger.ZERO) && yp.equals(BigInteger.ZERO))
            return Q;
        if (xq.equals(BigInteger.ZERO) && yq.equals(BigInteger.ZERO))
            return this;
        if (xp.equals(xq)) {
            if (yp.add(yq).mod(p).equals(BigInteger.ZERO)) {
                return new ECPoint(BigInteger.ZERO, BigInteger.ZERO, p, a);
            }
            if (yp.mod(p).equals(yq.mod(p))) {
                return this.doubling();
            }
        }

        BigInteger s = (yq.subtract(yp)).multiply((xq.subtract(xp)).modInverse(p));
        BigInteger xr, yr;
        xr = s.modPow(BigInteger.TWO, p).subtract(xp).subtract(xq).mod(p);
        yr = s.multiply(xp.subtract(xr)).subtract(yp).mod(p);

        return new ECPoint(xr, yr, p, a);
    }

    public ECPoint doubling() {
        if (y.add(y).mod(p).equals(BigInteger.ZERO))
            return new ECPoint(BigInteger.ZERO, BigInteger.ZERO, p, a);
        BigInteger s = BigInteger.valueOf(3).multiply(x.pow(2)).add(a)
                .multiply((BigInteger.TWO.multiply(y)).modInverse(p)).mod(p);
        BigInteger xr, yr;
        xr = s.modPow(BigInteger.TWO, p).subtract(BigInteger.TWO.multiply(x)).mod(p);
        yr = s.multiply(x.subtract(xr)).subtract(y).mod(p);

        return new ECPoint(xr, yr, p, a);
    }

    public ECPoint multiply(BigInteger d) {
        if (d.equals(BigInteger.ZERO))
            return new ECPoint(p, a);
        if (d.equals(BigInteger.ONE)) {
            return this;}
        else {
            if (d.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
                ECPoint S = multiply(d.subtract(BigInteger.ONE));
                return this.add(S);
            } else {
                ECPoint S = new ECPoint(this.getX(), this.getY(), p, a);
                return S.multiply(d.divide(BigInteger.TWO)).doubling();
            }
        }
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }
}
