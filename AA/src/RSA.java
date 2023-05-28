import java.math.BigInteger;
import java.util.Random;
import java.io.*;
import java.util.HashMap;

public class RSA {

    /**
     * Bit length of each prime number.
     */
    int primeSize;
    /**
     * Two distinct large prime numbers p and q.
     */
    BigInteger p, q;
    /**
     * Modulus N.
     */
    BigInteger N;
    /**
     * r = ( p - 1 ) * ( q - 1 )
     */
    BigInteger r;
    /**
     * Public exponent E and Private exponent D
     */
    BigInteger E, D;

    public RSA() {

        this.primeSize = 8;
    }

    /**
     * Constructor.
     *
     * @param	primeSize	Bit length of each prime number.
     */
    public RSA(int primeSize) {
        this.primeSize = primeSize;

        // Generate two distinct large prime numbers p and q.
        generatePrimeNumbers();

        // Generate Public and Private Keys.
        generatePublicPrivateKeys();
    }

    /**
     * Generate two distinct large prime numbers p and q.
     */
    public void generatePrimeNumbers() {
        p = new BigInteger(primeSize, 10, new Random());


        do {
            q = new BigInteger(primeSize, 10, new Random());
        } while (q.compareTo(p) == 0);
    }

    /**
     * Generate Public and Private Keys.
     */
    public void generatePublicPrivateKeys() {
        // N = p * q
        N = p.multiply(q);


        // r = ( p - 1 ) * ( q - 1 )
        r = p.subtract(BigInteger.valueOf(1));
        r = r.multiply(q.subtract(BigInteger.valueOf(1)));


        // Choose E, coprime to and less than r
        do {
            E = new BigInteger(2 * primeSize, new Random());
        } while ((E.compareTo(r) != -1) || (E.gcd(r).compareTo(BigInteger.valueOf(1)) != 0));


        // Compute D, the inverse of E mod r
        D = E.modInverse(r);
    }

    /**
     * Encrypts the plaintext (Using Public Key).
     *
     * @param	message	String containing the plaintext message to be encrypted.
     * @return	The ciphertext as a BigInteger array.
     */
    public BigInteger[] encrypt(String message) {
        int i;
        byte[] temp = new byte[1];


        byte[] digits = message.getBytes();

        BigInteger[] bigdigits = new BigInteger[digits.length];

        for (i = 0; i < bigdigits.length; i++) {
            temp[0] = digits[i];
            bigdigits[i] = new BigInteger(temp);
        }

        BigInteger[] encrypted = new BigInteger[bigdigits.length];

        for (i = 0; i < bigdigits.length; i++) {
            encrypted[i] = bigdigits[i].modPow(E, N);
        }


        return (encrypted);
    }

    public String encrypt(String message, BigInteger userE, BigInteger userN) {
        int i;
        byte[] temp = new byte[1];


        byte[] digits = message.getBytes();

        BigInteger[] bigdigits = new BigInteger[digits.length];

        for (i = 0; i < bigdigits.length; i++) {
            temp[0] = digits[i];
            bigdigits[i] = new BigInteger(temp);
        }

        BigInteger[] encrypted = new BigInteger[bigdigits.length];

        for (i = 0; i < bigdigits.length; i++) {
            encrypted[i] = bigdigits[i].modPow(userE, userN);
        }
        boolean tempd = true;

        String encryptedstring = null;

        for (int j = 0; j < encrypted.length; j++) {
            System.out.print(encrypted[j].toString(16).toUpperCase());
            if (tempd) {
                encryptedstring = encrypted[j].toString(16).toUpperCase();
                tempd = false;
            } else {
                encryptedstring += "#" + encrypted[j].toString(16).toUpperCase();
            }


        }
        System.out.println("Encry:"+encryptedstring);
        return (encryptedstring);
    }

    /**
     * Decrypts the ciphertext (Using Private Key).
     *
     * @param	encrypted	BigInteger array containing the ciphertext to be
     * decrypted.
     * @return	The decrypted plaintext.
     */
    public String decrypt(BigInteger[] encrypted, BigInteger D, BigInteger N) {
        int i;


        BigInteger[] decrypted = new BigInteger[encrypted.length];

        for (i = 0; i < decrypted.length; i++) {
            decrypted[i] = encrypted[i].modPow(D, N);
        }

        char[] charArray = new char[decrypted.length];

        for (i = 0; i < charArray.length; i++) {
            charArray[i] = (char) (decrypted[i].intValue());
        }
        

        return (new String(charArray));
    }

    /**
     * Get prime number p.
     *
     * @return	Prime number p.
     */
    public BigInteger getp() {
        return (p);
    }

    /**
     * Get prime number q.
     *
     * @return	Prime number q.
     */
    public BigInteger getq() {
        return (q);
    }

    /**
     * Get r.
     *
     * @return	r.
     */
    public BigInteger getr() {
        return (r);
    }

    /**
     * Get modulus N.
     *
     * @return	Modulus N.
     */
    public BigInteger getN() {
        return (N);
    }

    /**
     * Get Public exponent E.
     *
     * @return	Public exponent E.
     */
    public BigInteger getE() {
        return (E);
    }

    /**
     * Get Private exponent D.
     *
     * @return	Private exponent D.
     */
    public BigInteger getD() {
        return (D);
    }

    public BigInteger[] getdecr(String fn) {
        String filecontent = "";
        try {
            File fl = new File("d:\\decrypt\\" + fn);

            FileInputStream fin = new FileInputStream(fl);
            byte data[] = new byte[(int) fl.length()];
            fin.read(data);
            fin.close();
            filecontent = new String(data);
        } catch (Exception e) {
        }


        System.out.println("file content: d:\\decrypt\\" + fn + "=" + filecontent);

        String cipher[] = filecontent.split("#");

        BigInteger ciphertext[] = new BigInteger[cipher.length - 1];

        for (int i = 0; i < cipher.length - 1; i++) {
            ciphertext[i] = new BigInteger(cipher[i]);
            System.out.println(cipher[i]);


        }



        return (ciphertext);

    }

    public HashMap<String, String> generatepoly(int nn) {


        int m = (nn * 60) / 100;
        int primeSize = 8;
        HashMap<String, String> temp = new HashMap<String, String>();
        while (m > 0) {
            RSA rsa2 = new RSA(primeSize);

            String N = rsa2.getN().toString();
            String E = rsa2.getE().toString();
            String D = rsa2.getD().toString();
            System.out.println("N=" + N + " E=" + E + " D=" + D);
            temp.put(N, E + "," + D);

            m--;
        }
        return temp;
    }

    /**
     * RSA Main program for Unit Testing.
     */
    public static void main(String[] args) throws IOException {

        int primeSize = 8;

        // Generate Public and Private Keys

        RSA rsa = new RSA(primeSize);

        System.out.println("Key Size: [" + primeSize + "]");
        System.out.println("");

        System.out.println("Generated prime numbers p and q");

        System.out.println("p: [" + rsa.getp().toString() + "]");
        System.out.println("q: [" + rsa.getq().toString() + "]");
        System.out.println("");


        System.out.println("The public key is the pair (N, E) which will be published.");
        System.out.println("N: [" + rsa.getN().toString() + "]");
        System.out.println("E: [" + rsa.getE().toString() + "]");
        System.out.println("");

        System.out.println("The private key is the pair (N, D) which will be kept private.");
        System.out.println("N: [" + rsa.getN().toString() + "]");
        System.out.println("D: [" + rsa.getD().toString() + "]");
        System.out.println("");


        // Get message (plaintext) from user
        System.out.println("Please enter message (plaintext):");
        String plaintext = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        System.out.println("");

        // Encrypt Message
        BigInteger[] ciphertext = rsa.encrypt(plaintext);

        System.out.print("Ciphertext: [");
        for (int i = 0; i < ciphertext.length; i++) {
            System.out.print(ciphertext[i].toString(16).toUpperCase());

            if (i != ciphertext.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println("]");
        System.out.println("Decrypt:"+rsa.decrypt(ciphertext, rsa.getD(), rsa.getN()));

       RSA rsa1 = new RSA(8);
        
        String encd = rsa1.encrypt("hello",rsa1.getE(),rsa1.getN());
        
        String dat[] =encd.split("#");
        
        BigInteger ciphert[] = new BigInteger[dat.length];
        
        for(int i =0 ;i<ciphert.length;i++){
            ciphert[i]= new BigInteger(dat[i].trim(),16);
        }

        String recoveredPlaintext = rsa1.decrypt(ciphert, rsa1.getD(), rsa1.getN());

        System.out.println("Recovered plaintext: [" + recoveredPlaintext + "]");
    }
}
