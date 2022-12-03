import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

public class ECSignature {

    private KeyPair keyPair;
    private EllipticCurve ec;
    private ECPoint G;
    private MessageDigest md;

    public ECSignature(EllipticCurve ec) {
        this.ec = ec;
        keyPair = new KeyPair();
    }

    public void keyGen() {
        G = new ECPoint(ec.getGx(), ec.getGy(), ec.getP(), ec.getA());
        BigInteger d = randomBigInt(BigInteger.TWO, ec.getN().subtract(BigInteger.TWO));
        ECPoint Q = G.multiply(d);
        keyPair.setQ(Q);
        keyPair.setD(d);
        System.out.println("public key: Q(" + keyPair.getQ().getX() + ", " + keyPair.getQ().getY() + ")");
        System.out.println("private key: " + keyPair.getD());
    }

    public SignaturePair sign(byte[] m) {
        SignaturePair s = new SignaturePair();
        BigInteger k = randomBigInt(BigInteger.TWO, ec.getN().subtract(BigInteger.TWO));
        do {
            s.setR(G.multiply(k).getX().mod(ec.getN()));
        } while (s.getR().equals(BigInteger.ZERO));
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(m);
            BigInteger hm = new BigInteger(digest);
            s.setS(k.modInverse(ec.getN()).multiply(hm.add(keyPair.getD().multiply(s.getR()))).mod(ec.getN()));
        } catch (Exception e) {
            System.out.println("sign: " + e);
        }
        if (s.getS().equals(BigInteger.ZERO)) {
            return sign(m);
        }
        System.out.println("signature: r = " + s.getR() + ", s = " + s.getS());
        return s;
    }

    public boolean verify(BigInteger r, BigInteger s, byte[] m) {
        if (r.compareTo(BigInteger.ZERO) <= 0 || r.compareTo(ec.getN()) >= 0) {
            System.out.println("r is not in range [1, n-1]");
            return false;
        }
        if (s.compareTo(BigInteger.ZERO) <= 0 || s.compareTo(ec.getN()) >= 0) {
            System.out.println("s is not in range [1, n-1]");
            return false;
        }
        BigInteger w = s.modInverse(ec.getN());
        BigInteger u1, u2;
        u1 = u2 = BigInteger.valueOf(0);
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(m);
            BigInteger hm = new BigInteger(digest);
            u1 = hm.multiply(w).mod(ec.getN());
            u2 = r.multiply(w).mod(ec.getN());
        } catch (Exception e) {
            System.out.println("verify: " + e);
        }
        BigInteger v = G.multiply(u1).add(keyPair.getQ().multiply(u2)).getX().mod(ec.getN());
        return v.equals(r);
    }

    private BigInteger randomBigInt(BigInteger min, BigInteger max) {
        BigInteger i;
        Random rnd = new Random();
        do {
            i = new BigInteger(max.bitLength(), rnd);
        } while (i.compareTo(max) > 0 || i.compareTo(min) < 0);
        return i;
    }

}
