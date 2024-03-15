package uob.oop;

import java.text.DecimalFormat;

public class NewsClassifier {
    public String[] myHTMLs;
    public String[] myStopWords = new String[127];
    public String[] newsTitles;
    public String[] newsContents;
    public String[] newsCleanedContent;
    public double[][] newsTFIDF;

    private final String TITLE_GROUP1 = "Osiris-Rex's sample from asteroid Bennu will reveal secrets of our solar system";
    private final String TITLE_GROUP2 = "Bitcoin slides to five-month low amid wider sell-off";
    
    public Toolkit myTK;

    public NewsClassifier() {
        myTK = new Toolkit();
        myHTMLs = myTK.loadHTML();
        myStopWords = myTK.loadStopWords();

        loadData();
    }

    public static void main(String[] args) {
        NewsClassifier myNewsClassifier = new NewsClassifier();

        myNewsClassifier.newsCleanedContent = myNewsClassifier.preProcessing();

        myNewsClassifier.newsTFIDF = myNewsClassifier.calculateTFIDF(myNewsClassifier.newsCleanedContent);

        //Change the _index value to calculate similar based on a different news article.
        double[][] doubSimilarity = myNewsClassifier.newsSimilarity(0);

        System.out.println(myNewsClassifier.resultString(doubSimilarity, 10));

        String strGroupingResults = myNewsClassifier.groupingResults(myNewsClassifier.TITLE_GROUP1, myNewsClassifier.TITLE_GROUP2);
        System.out.println(strGroupingResults);
    }

    public void loadData() {
        newsTitles = new String[myHTMLs.length];
        newsContents = new String[myHTMLs.length];
        for (int i = 0; i < myHTMLs.length; i++) {
            newsTitles[i] = HtmlParser.getNewsTitle(myHTMLs[i]);
            newsContents[i] = HtmlParser.getNewsContent(myHTMLs[i]);
        }
        //TODO 4.1 - 2 marks
    }


    public String[] preProcessing() {
        String[] myCleanedContent = new String[newsContents.length];
        for (int i = 0; i < myCleanedContent.length; i++) {
            myCleanedContent[i] = newsContents[i];
            myCleanedContent[i] = NLP.textCleaning(myCleanedContent[i]);
            myCleanedContent[i] = NLP.textLemmatization(myCleanedContent[i]);
            myCleanedContent[i] = NLP.removeStopWords(myCleanedContent[i], myStopWords);
        }
        //TODO 4.2 - 5 marks

        return myCleanedContent;
    }

    public double[][] calculateTFIDF(String[] _cleanedContents) {
        String[] vocabulary_list = buildVocabulary(_cleanedContents);
        int num = _cleanedContents.length;
        int num2 = vocabulary_list.length;
        double[][] myTFIDF = new double[num][num2];

        // IDF Calculation
        double[] IDF = new double[num2];
        for (int j = 0; j < num2; j++) {
            int article_frequency = 0;

            for (String cleanedContent : _cleanedContents) {
                String[] words = cleanedContent.trim().split(" ");
                int term_frequency = 0;

                for (String word : words) {
                    if (word.equals(vocabulary_list[j])) {
                        term_frequency++;
                    }
                }

                if (term_frequency > 0) {
                    article_frequency++;
                }

                IDF[j] = Math.log((double) num / (article_frequency)) + 1;
            }
        }

        // Loading each document
        for (int i = 0; i < num; i++) {
            String[] words = _cleanedContents[i].trim().split(" ");
            double total_words = words.length;  // Total words for each document

            for (int j = 0; j < num2; j++) {
                int term_frequency = 0;

                for (String word : words) {
                    if (word.equals(vocabulary_list[j])) {
                        term_frequency++;
                    }
                }

                myTFIDF[i][j] = ((double) term_frequency / total_words) * IDF[j];
            }
        }
        return myTFIDF;
    }

    public String[] buildVocabulary(String[] _cleanedContents) {
        String[] words = String.join(" ", _cleanedContents).split(" ");
        int size = words.length;
        boolean[] Unique = new boolean[size];

        int unique_words = 0;

        // Mark the first occurrence of each word as unique
        for (int i = 0; i < size; i++) {
            if (!Unique[i]) {
                unique_words++;
                for (int j = i + 1; j < size; j++) {
                    if (words[i].equals(words[j])) {
                        Unique[j] = true;
                    }
                }
            }
        }

        String[] vocabulary = new String[unique_words];
        int index = 0;

        // Copy unique words into the vocabulary array
        for (int i = 0; i < size; i++) {
            if (!Unique[i]) {
                vocabulary[index] = words[i];
                index++;
            }
        }

        return vocabulary;
    }

    public double[][] newsSimilarity(int _newsIndex) {
        int size_of_array = newsTFIDF.length;
        int size_of_array2 = newsTitles.length;
        Vector[] vectors = new Vector[size_of_array];
        // One row for order, another row for Cosine Similarity
        double[][] mySimilarity = new double [size_of_array2][2];

        // Create Vector objects for all news article
        for (int i = 0; i < size_of_array; i++) {
            vectors[i] = new Vector(newsTFIDF[i]);
        }

        // Calculate cosineSimilarity
        for (int i = 0; i < size_of_array; i++) {
            mySimilarity[i][0] = i;
            mySimilarity[i][1] = vectors[_newsIndex].cosineSimilarity(vectors[i]);
        }

        // Sort matrix in descending order
        int rows = mySimilarity.length;
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < rows - i - 1; j++) {
                if (mySimilarity[j][1] < mySimilarity[j + 1][1]) {
                    double[] temp = mySimilarity[j];
                    mySimilarity[j] = mySimilarity[j + 1];
                    mySimilarity[j + 1] = temp;
                }
            }
        }
        return mySimilarity;
        //TODO 4.5 - 15 marks
    }

    public boolean notContains(String[] _arrayTarget, String _searchValue) {
        for (String element : _arrayTarget) {
            if (_searchValue.equals(element)) {
                return false;
            }
        }
        return true;
    }

    public String[] trimArray(String[] _arrayTarget, int _newSize) {
        String[] trimmedArray = new String[_newSize];
        System.arraycopy(_arrayTarget, 0, trimmedArray, 0, _newSize);
        return trimmedArray;
    }

    public int[] trimArray(int[] _arrayTarget, int _newSize) {
        int[] trimmedArray = new int[_newSize];
        System.arraycopy(_arrayTarget, 0, trimmedArray, 0, _newSize);
        return trimmedArray;
    }

    public String groupingResults(String _firstTitle, String _secondTitle) {
        int[] arrayGroup1, arrayGroup2;

        //TODO 4.6 - 15 marks
        int[] arrayFirstGroupIndex = new int[myHTMLs.length];
        int[] arraySecondGroupIndex = new int[myHTMLs.length];

        int indexGroup1 = 0, indexGroup2 = 0;
        int firstIndex = 0, secondIndex = 0;

        for (int i = 0; i < newsTitles.length; i++) {
            String newsTitle = newsTitles[i];
            if (newsTitle.equals(_firstTitle)) {
                firstIndex = i;
            } else if (newsTitle.equals(_secondTitle)) {
                secondIndex = i;
            }
        }

        Vector firstVector = new Vector(newsTFIDF[firstIndex]);
        Vector secondVector = new Vector(newsTFIDF[secondIndex]);

        for (int i = 0; i < myHTMLs.length; i++) {
            Vector myVector = new Vector(newsTFIDF[i]);
            if (firstVector.cosineSimilarity(myVector) >= secondVector.cosineSimilarity(myVector)) {
                arrayFirstGroupIndex[indexGroup1] = i;
                indexGroup1++;
            } else {
                arraySecondGroupIndex[indexGroup2] = i;
                indexGroup2++;
            }
        }

        arrayGroup1 = trimArray(arrayFirstGroupIndex, indexGroup1);
        arrayGroup2 = trimArray(arraySecondGroupIndex, indexGroup2);

        return resultString(arrayGroup1, arrayGroup2);
    }

    public String resultString(double[][] _similarityArray, int _groupNumber) {
        StringBuilder mySB = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        for (int j = 0; j < _groupNumber; j++) {
            for (int k = 0; k < _similarityArray[j].length; k++) {
                if (k == 0) {
                    mySB.append((int) _similarityArray[j][k]).append(" ");
                } else {
                    String formattedCS = decimalFormat.format(_similarityArray[j][k]);
                    mySB.append(formattedCS).append(" ");
                }
            }
            mySB.append(newsTitles[(int) _similarityArray[j][0]]).append("\r\n");
        }
        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

    public String resultString(int[] _firstGroup, int[] _secondGroup) {
        StringBuilder mySB = new StringBuilder();
        mySB.append("There are ").append(_firstGroup.length).append(" news in Group 1, and ").append(_secondGroup.length).append(" in Group 2.\r\n").append("=====Group 1=====\r\n");

        for (int i : _firstGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }
        mySB.append("=====Group 2=====\r\n");
        for (int i : _secondGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }

        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

}
