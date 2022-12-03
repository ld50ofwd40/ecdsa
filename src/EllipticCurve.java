import java.math.BigInteger;

public class EllipticCurve {

    private BigInteger P, n, a, b, Gx, Gy;

    public EllipticCurve(BigInteger P, BigInteger n, BigInteger a, BigInteger b,
                         BigInteger Gx, BigInteger Gy) {
        this.P = P;
        this.n = n;
        this.a = a;
        this.b = b;
        this.Gx = Gx;
        this.Gy = Gy;
    }

    public EllipticCurve(String s) {
        switch (s) {
            case "test":
                P = new BigInteger("23");
                n = new BigInteger("7");
                a = new BigInteger("1");
                b = new BigInteger("1");
                Gx = new BigInteger("5");
                Gy = new BigInteger("4");
                break;
            case "P-192":
                P = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
                n = new BigInteger("6277101735386680763835789423176059013767194773182842284081");
                a = new BigInteger("-3");
                b = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16);
                Gx = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16);
                Gy = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811", 16);
                break;
            default:
                System.out.println("Curve " + s + " is not supported.");
        }
    }

    public BigInteger getP() {
        return P;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
    }

    public BigInteger getGx() {
        return Gx;
    }

    public BigInteger getGy() {
        return Gy;
    }
}
