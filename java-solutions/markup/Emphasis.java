package markup;

import java.util.List;
public class Emphasis extends Markdown {
    public Emphasis (List<Element> list) {
        super(list);
        markdownTag = "*";
        BBCodeOpeningTag = "[i]";
        BBCodeClosingTag = "[/i]";
    }
}
