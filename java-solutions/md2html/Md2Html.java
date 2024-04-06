package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class Md2Html {

    private static final Map<String, String> mdToHtml= Map.of("*", "em", "_", "em",
            "**", "strong", "__", "strong", "--", "s", "`", "code",
            "```", "pre");
    private static int mdTag(String str, int idx) {
        if (idx < str.length() - 2 && mdToHtml.containsKey(str.substring(idx, idx + 3))) return 3;
        if (idx < str.length() - 1 && mdToHtml.containsKey(str.substring(idx, idx + 2))) return 2;
        if (mdToHtml.containsKey(str.substring(idx, idx + 1))) {
            if (idx > 0) {
                char prevChar = str.charAt(idx - 1);
                if (prevChar == '\\') {
                    return 0;
                } else if (!Character.isWhitespace(prevChar)) {
                    return 1;
                }
            }
            if (idx < str.length() - 1) {
                char nextChar = str.charAt(idx + 1);
                if (!Character.isWhitespace(nextChar)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private static void parseLine(String str, ArrayDeque<CustomPair> mdTags) {
        int idx = 0;
        while (idx < str.length()) {
            int shift = mdTag(str, idx);
            idx += shift;
            if (shift > 0) {
                String tag = str.substring(idx - shift, idx);
                CustomPair customPair = new CustomPair(tag, idx - shift);
                mdTags.offerLast(customPair);
            } else {
                idx++;
            }
        }
    }

    private static void appendMark(StringBuilder markedText, String tag, int closing) {
        markedText.append("<");
        if (closing == 1) {
            markedText.append("/");
        }
        markedText.append(mdToHtml.get(tag));
        markedText.append(">");
    }

    private static StringBuilder lineToHtml(String str, ArrayDeque<CustomPair> mdTags,
                                            Map<String, Integer> count) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (!mdTags.isEmpty() && i == mdTags.peekFirst().getPos()) {
                String tag = mdTags.peekFirst().getTag();
                if (!tag.equals("```") && count.get("```") == 1) {
                    line.append(tag);
                    mdTags.pollFirst();
                    i += tag.length() - 1;
                    continue;
                }
                mdTags.pollFirst();
                appendMark(line, tag, count.get(tag));
                count.put(tag, 1 - count.get(tag));
                i += tag.length() - 1;
            } else {
                switch (str.charAt(i)) {
                    case '\\':
                        if (i == str.length() - 1 || mdTag(str, i + 1) == 1) {
                            line.append(str.charAt(i));
                        }
                        break;
                    case '<':
                        line.append("&lt;");
                        break;
                    case '>':
                        line.append("&gt;");
                        break;
                    case '&':
                        line.append("&amp;");
                        break;
                    default:
                        line.append(str.charAt(i));
                }
            }
        }
        return line;
    }

    private static int paragraphToHtml(List<String> text, int firstLine, StringBuilder markedText) {
        ArrayDeque<CustomPair> mdTags = new ArrayDeque<>();
        Map<String, Integer> count = new HashMap<>();
        for (String tag : mdToHtml.keySet()) {
            count.put(tag, 0);
        }
        int idx = firstLine;
        int level = beginParagraph(text.get(firstLine), markedText, mdTags, count);
        if (level > 0) {
            idx++;
        }
        boolean nonEmptyString = level > 0;
        while (idx < text.size() && !text.get(idx).isEmpty()) {
            parseLine(text.get(idx), mdTags);
            nonEmptyString = true;
            markedText.append(lineToHtml(text.get(idx), mdTags, count));
            markedText.append(System.lineSeparator());
            idx++;
        }
        endParagraph(level, nonEmptyString, markedText);
        return idx;
    }

    private static int beginParagraph(String firstLine, StringBuilder markedText,
                                      ArrayDeque<CustomPair> mdTags,
                                      Map<String, Integer> count) {
        int level = headerLevel(firstLine);
        if (level > 0) {
            parseLine(firstLine.substring(level + 1), mdTags);
            markedText.append("<h");
            markedText.append(level);
            markedText.append(">");
            markedText.append(lineToHtml(firstLine.substring(level + 1), mdTags, count));
            markedText.append(System.lineSeparator());
        } else {
            markedText.append("<p>");
        }
        return level;
    }

    private static void endParagraph(int level, boolean nonEmptyString, StringBuilder markedText) {
        if (level > 0) {
            if (nonEmptyString) {
                markedText.setLength(markedText.length() -
                        System.lineSeparator().length());
                markedText.append("</h");
                markedText.append(level);
                markedText.append(">");
                markedText.append(System.lineSeparator());
            } else {
                markedText.setLength(markedText.length() - 4);
            }
        } else {
            if (nonEmptyString) {
                markedText.setLength(markedText.length() -
                        System.lineSeparator().length());
                markedText.append("</p>");
                markedText.append(System.lineSeparator());
            } else {
                markedText.setLength(markedText.length() - 3);
            }
        }
    }

    private static int headerLevel(String str) {
        int idx = 0;
        while (idx < str.length() && str.charAt(idx) == '#') {
            idx++;
        }
        if (idx > 0 && Character.isWhitespace(str.charAt(idx))) {
            return idx;
        } else {
            return 0;
        }
    }

    private static int skipEmptyLines(List <String> text, int firstLine) {
        int idx = firstLine;
        while (idx < text.size() && text.get(idx).isEmpty()) {
            idx++;
        }
        return idx;
    }

    public static StringBuilder textToHtml(List <String> text) {
        int idx = 0;
        StringBuilder markedText = new StringBuilder();
        while (idx < text.size()) {
            idx = skipEmptyLines(text, idx);
            idx = paragraphToHtml(text, idx, markedText);
        }
        return markedText;
    }
    public static void main(String[] args) {
        StringBuilder markedText = new StringBuilder();
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(args[0]), StandardCharsets.UTF_8))) {
                String currLine = reader.readLine();
                List<String> text = new ArrayList<>();
                while (currLine != null) {
                    text.add(currLine);
                    currLine = reader.readLine();
                }
                markedText = Md2Html.textToHtml(text);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found");
        } catch (IOException e) {
            System.err.println("Input error: " + e.getMessage());
        }
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            out.write(markedText.toString());
        } catch (IOException e) {
            System.out.println("Error while writing to a file" + e.getMessage());
        }
    }
}