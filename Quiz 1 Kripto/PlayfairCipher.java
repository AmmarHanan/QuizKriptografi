public class PlayfairCipher {
    private static char[][] keyMatrix;

    public static String encrypt(String text, String key) {
        keyMatrix = generateKeyMatrix(key);
        text = prepareText(text);
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            encryptedText.append(encryptPair(a, b));
        }

        return encryptedText.toString();
    }

    public static String decrypt(String text, String key) {
        keyMatrix = generateKeyMatrix(key);
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            decryptedText.append(decryptPair(a, b));
        }

        return decryptedText.toString();
    }

    private static char[][] generateKeyMatrix(String key) {
        StringBuilder uniqueKey = new StringBuilder();
        boolean[] usedLetters = new boolean[26];

        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace('J', 'I');
        for (char c : key.toCharArray()) {
            if (!usedLetters[c - 'A']) {
                uniqueKey.append(c);
                usedLetters[c - 'A'] = true;
            }
        }
        
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !usedLetters[c - 'A']) {
                uniqueKey.append(c);
                usedLetters[c - 'A'] = true;
            }
        }

        char[][] matrix = new char[5][5];
        for (int i = 0, k = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = uniqueKey.charAt(k++);
            }
        }

        return matrix;
    }

    private static String prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace('J', 'I');
        StringBuilder preparedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char a = text.charAt(i);
            if (i + 1 < text.length() && text.charAt(i + 1) == a) {
                preparedText.append(a).append('X');
            } else {
                preparedText.append(a);
            }
        }

        if (preparedText.length() % 2 != 0) {
            preparedText.append('X');
        }

        return preparedText.toString();
    }

    private static String encryptPair(char a, char b) {
        int[] posA = findPosition(a);
        int[] posB = findPosition(b);

        if (posA[0] == posB[0]) {
            return "" + keyMatrix[posA[0]][(posA[1] + 1) % 5] + keyMatrix[posB[0]][(posB[1] + 1) % 5];
        } else if (posA[1] == posB[1]) {
            return "" + keyMatrix[(posA[0] + 1) % 5][posA[1]] + keyMatrix[(posB[0] + 1) % 5][posB[1]];
        } else {
            return "" + keyMatrix[posA[0]][posB[1]] + keyMatrix[posB[0]][posA[1]];
        }
    }

    private static String decryptPair(char a, char b) {
        int[] posA = findPosition(a);
        int[] posB = findPosition(b);

        if (posA[0] == posB[0]) {
            return "" + keyMatrix[posA[0]][(posA[1] + 4) % 5] + keyMatrix[posB[0]][(posB[1] + 4) % 5];
        } else if (posA[1] == posB[1]) {
            return "" + keyMatrix[(posA[0] + 4) % 5][posA[1]] + keyMatrix[(posB[0] + 4) % 5][posB[1]];
        } else {
            return "" + keyMatrix[posA[0]][posB[1]] + keyMatrix[posB[0]][posA[1]];
        }
    }

    private static int[] findPosition(char c) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyMatrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
