import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        EllipticCurve ec = new EllipticCurve("P-192");
        ECSignature ecSignature = new ECSignature(ec);

        System.out.println("===== Key generation ============================");
        ecSignature.keyGen();


        System.out.println("\n===== Signing message ===========================");
        byte[] message = "Agent Cooper loves coffee.".getBytes();
        SignaturePair s = ecSignature.sign(message);


        System.out.println("\n===== Verification ==============================");
        System.out.println("Verified: " + ecSignature.verify(s.getR(), s.getS(), message));


        System.out.println("\n===== Failed verification =======================");
        byte[] message2 = "The owls are not what they seem.".getBytes();
        System.out.println("Verified: " + ecSignature.verify(s.getR(), s.getS(), message2));
        ECSignature ecSignature2 = new ECSignature(ec);
        ecSignature2.keyGen();
        System.out.println("Verified: " + ecSignature2.verify(s.getR(), s.getS(), message));


        System.out.println("\n===== EC points =================================");
        ECPoint P = new ECPoint(5,4,23,1);
        ECPoint Q = new ECPoint(0, 0, 23, 1);
        for (int i = 0; i <= 7; i++){
            Q = Q.add(P);
            System.out.println("(" + Q.getX() + ", " + Q.getY() + ")");
        }
        System.out.println();
        for (int i = 1; i<= 8; i++) {
            Q = P.multiply(BigInteger.valueOf(i));
            System.out.println("(" + Q.getX() + ", " + Q.getY() + ")");
        }

    }
}