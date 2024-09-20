public class HillCipher {

    public static String encrypt(String text, String key) {       
        int n = 3;
        int[][] keyMatrix = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                keyMatrix[i][j] = key.charAt(i * n + j) - 'A';
            }
        }
        
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        while (text.length() % n != 0) {
            text += "X";
        }

        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i += n) {
            int[] block = new int[n];
            for (int j = 0; j < n; j++) {
                block[j] = text.charAt(i + j) - 'A';
            }

            int[] result = multiplyByKeyMatrix(block, keyMatrix);
            for (int value : result) {
                encryptedText.append((char) ((value + 26) % 26 + 'A'));
            }
        }

        return encryptedText.toString();
    }

    public static String decrypt(String text, String key) {        
        int n = 3;
        int[][] keyMatrix = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                keyMatrix[i][j] = key.charAt(i * n + j) - 'A';
            }
        }
        
        int[][] inverseKeyMatrix = invertMatrix(keyMatrix);

        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i += n) {
            int[] block = new int[n];
            for (int j = 0; j < n; j++) {
                block[j] = text.charAt(i + j) - 'A';
            }

            int[] result = multiplyByKeyMatrix(block, inverseKeyMatrix);
            for (int value : result) {
                decryptedText.append((char) ((value + 26) % 26 + 'A'));
            }
        }

        return decryptedText.toString();
    }

    private static int[] multiplyByKeyMatrix(int[] block, int[][] matrix) {
        int[] result = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i] += block[j] * matrix[i][j];
            }
            result[i] %= 26; 
        }
        return result;
    }

    private static int[][] invertMatrix(int[][] matrix) {
        int det = (matrix[0][0] * matrix[1][1] * matrix[2][2] + 
                    matrix[0][1] * matrix[1][2] * matrix[2][0] + 
                    matrix[0][2] * matrix[1][0] * matrix[2][1] -
                    matrix[0][2] * matrix[1][1] * matrix[2][0] -
                    matrix[0][1] * matrix[1][0] * matrix[2][2] -
                    matrix[0][0] * matrix[1][2] * matrix[2][1]) % 26;

        det = (det + 26) % 26;

        int invDet = modInverse(det, 26);
        if (invDet == -1) {
            throw new IllegalArgumentException("Kunci tidak dapat dibalik; pilih kunci yang berbeda.");
        }

        int[][] invMatrix = new int[3][3];
        invMatrix[0][0] = (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) * invDet % 26;
        invMatrix[0][1] = (matrix[0][2] * matrix[2][1] - matrix[0][1] * matrix[2][2]) * invDet % 26;
        invMatrix[0][2] = (matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1]) * invDet % 26;
        invMatrix[1][0] = (matrix[1][2] * matrix[2][0] - matrix[1][0] * matrix[2][2]) * invDet % 26;
        invMatrix[1][1] = (matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0]) * invDet % 26;
        invMatrix[1][2] = (matrix[0][2] * matrix[1][0] - matrix[0][0] * matrix[1][2]) * invDet % 26;
        invMatrix[2][0] = (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]) * invDet % 26;
        invMatrix[2][1] = (matrix[0][1] * matrix[2][0] - matrix[0][0] * matrix[2][1]) * invDet % 26;
        invMatrix[2][2] = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) * invDet % 26;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                invMatrix[i][j] = (invMatrix[i][j] + 26) % 26;
            }
        }

        return invMatrix;
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) return x;
        }
        return -1;
    }
}
