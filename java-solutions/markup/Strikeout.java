package markup;

import java.util.List;
public class Strikeout extends Markdown {
    public Strikeout (List<Element> list) {
        super(list);
        markdownTag = "~";
        BBCodeOpeningTag = "[s]";
        BBCodeClosingTag = "[/s]";
    }
}
