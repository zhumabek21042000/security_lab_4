public class PublicKeyEncryption {

    final static int p = 1019;
    final static int g = 2;
    
    private static int modularExponentiation(int g, int x, int p) {
        if (x == 0) {
            return 1;
        } else if (x == 1) {
            return g % p;
        } else if (x % 2 == 1) {
            int h = modularExponentiation(g, x - 1, p);
            return (h * g) % p;
        } else {
            int h = modularExponentiation(g, x / 2, p);
            return (h * h) % p;
        }
    }
    
    public static int[] generateKeys() {
        int privateKey = (int) (Math.random() * 1000);
        int publicKey = modularExponentiation(g, privateKey, p);
        return new int[] {privateKey, publicKey};
    }

    public static String encryptMessage(int myPrivateKey, int recipientPublicKey, String message) {
        int sharedKey = modularExponentiation(recipientPublicKey, myPrivateKey, p);
        return AES.encrypt(message, sharedKey);
    }

    public static String decryptMessage(int myPrivateKey, int senderPublicKey, String message) {
        int sharedKey = modularExponentiation(senderPublicKey, myPrivateKey, p);
        return AES.decrypt(message, sharedKey);
    }
    
}
