public class VigenereCipher {

    public static String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        text = text.toUpperCase().replaceAll("[^A-Z]", "");

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                char k = key.charAt(j % key.length());
                result.append((char) ((c + k - 2 * 'A') % 26 + 'A'));
                j++;
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        text = text.toUpperCase().replaceAll("[^A-Z]", "");

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                char k = key.charAt(j % key.length());
                result.append((char) ((c - k + 26) % 26 + 'A'));
                j++;
            }
        }
        return result.toString();
    }
}
