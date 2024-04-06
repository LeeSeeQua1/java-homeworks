package markup;

public class Text implements Element {
    String textStr;

    public Text(String text) {
        textStr = text;
    }

    @Override
    public void toMarkdown(StringBuilder markedText) {
        markedText.append(textStr);
    }

    public void toBBCode(StringBuilder markedText) {
        markedText.append(textStr);
    }
}
