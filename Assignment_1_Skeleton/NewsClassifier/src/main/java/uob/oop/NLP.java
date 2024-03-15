package uob.oop;

public class NLP {
    /***
     * Clean the given (_content) text by removing all the characters that are not 'a'-'z', '0'-'9' and white space.
     * @param _content Text that need to be cleaned.
     * @return The cleaned text.
     */
    public static String textCleaning(String _content) {
        StringBuilder sbContent = new StringBuilder();
        //TODO Task 2.1 - 3 marks
        for (char c : _content.toLowerCase().toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || Character.isWhitespace(c)) {
                sbContent.append(c);
            }
        }

        return sbContent.toString().trim();
    }

    /***
     * Text lemmatization. Delete 'ing', 'ed', 'es' and 's' from the end of the word.
     * @param _content Text that need to be lemmatized.
     * @return Lemmatized text.
     */
    public static String textLemmatization(String _content) {
        String[] words = _content.split(" ");
        String[] lemmatize_words = new String[words.length];
        String[] suffixes = {"ing", "ed", "es", "s"};
        for (int i = 0; i < words.length; i++) {
            lemmatize_words[i] = words[i];
            for (String suffix : suffixes) {
                if (words[i].endsWith(suffix)) {
                    lemmatize_words[i] = words[i].substring(0, words[i].length() - suffix.length());
                    break;
                }
            }
        }
            return String.join(" ", lemmatize_words);
    }

    //TODO Task 2.2 - 3 marks

    /***
     * Remove stop-words from the text.
     * @param _content The original text.
     * @param _stopWords An array that contains stop-words.
     * @return Modified text.
     */
    public static String removeStopWords(String _content, String[] _stopWords) {
        String[] words = _content.split(" ");
        StringBuilder result = new StringBuilder();
        boolean[] isStopWord = new boolean[words.length];
        for (int i = 0; i < words.length; i++) {
            for (String stop_word : _stopWords) {
                if (words[i].equals(stop_word)) {
                    isStopWord[i] = true;
                    break;
                }
            }
        }
        for (int i = 0; i < words.length; i++) {
            if (!isStopWord[i]) {
                result.append(words[i]).append(" ");
            }
        }
        return result.toString().trim();
    }
}

