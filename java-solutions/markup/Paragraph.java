package markup;

import java.util.List;

public class Paragraph implements ListItemInterface {
    private final List<Element> paragraphElements;

    public Paragraph(List<Element> list) {
        paragraphElements = list;
    }

    public void toMarkdown(StringBuilder text) {
        for (Element el : paragraphElements) {
            el.toMarkdown(text);
        }
    }

    public void toBBCode(StringBuilder text) {
        for (Element el : paragraphElements) {
            el.toBBCode(text);
        }
    }
}
