package uob.oop;

public class HtmlParser {
    /***
     * Extract the title of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the title if it's been found. Otherwise, return "Title not found!".
     */
    public static String getNewsTitle(String _htmlCode) {
        //TODO Task 1.1 - 5 marks
        int startTitleIndex = _htmlCode.indexOf("<title>");

        if (startTitleIndex != -1) {
            int endTitleIndex = _htmlCode.indexOf("</title>", startTitleIndex);

            if (endTitleIndex != -1) {
                String title_string = _htmlCode.substring(startTitleIndex + 7, endTitleIndex);
                String[] parts = title_string.split("\\|");
                return parts[0].trim();
            } else {
                return "Title not found!";
            }
        } else {
            return "Title not found!";
        }
    }
    /***
     * Extract the content of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the content if it's been found. Otherwise, return "Content not found!".
     */
    public static String getNewsContent(String _htmlCode) {
        //TODO Task 1.2 - 5 marks
        int startBodyIndex = _htmlCode.indexOf("\"articleBody\": \"");
        if (startBodyIndex != -1) {
            int endBodyIndex = _htmlCode.indexOf("\",\"mainEntityOfPage\":", startBodyIndex);
            if (endBodyIndex != -1) {
                String body = _htmlCode.substring(startBodyIndex + 16, endBodyIndex);
                return body.toLowerCase().trim();
            } else {
                return "Content not found!";
            }
        } else {
            return "Content not found!";
        }
    }
}
