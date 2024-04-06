package markup;

public interface Element {
    void toMarkdown(StringBuilder text);
    void toBBCode(StringBuilder text);
}