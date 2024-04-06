package markup;

import java.util.List;

public abstract class Markdown implements Element {
    private final List<Element> markdownElements;
    protected String markdownTag;
    protected String BBCodeOpeningTag;
    protected String BBCodeClosingTag;

    protected Markdown(List<Element> list) {
        markdownElements = list;
    }

    public void toMarkdown(StringBuilder text) {
        text.append(markdownTag);
        for (Element el : markdownElements) {
            el.toMarkdown(text);
        }
        text.append(markdownTag);
    }

    public void toBBCode(StringBuilder text) {
        text.append(BBCodeOpeningTag);
        for (Element el : markdownElements) {
            el.toBBCode(text);
        }
        text.append(BBCodeClosingTag);
    }
}
