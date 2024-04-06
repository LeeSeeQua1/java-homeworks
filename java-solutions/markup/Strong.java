package markup;

import java.util.List;
public class Strong extends Markdown {
    public Strong (List<Element> list) {
        super(list);
        markdownTag = "__";
        BBCodeOpeningTag = "[b]";
        BBCodeClosingTag = "[/b]";
    }
}
