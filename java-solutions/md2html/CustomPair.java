package md2html;

public class CustomPair {
    private final String tag;
    private final int pos;
    CustomPair (String str, int num) {
        tag = str;
        pos = num;
    }
    public String getTag() {
        return tag;
    }
    public int getPos() {
        return pos;
    }
}
