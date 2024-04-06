package markup;

public abstract class List implements ListItemInterface {
    private final java.util.List<ListItem> elementList;
    protected String listOpeningTag;
    protected String listClosingTag;

    public List(java.util.List<ListItem> list) {
        elementList = list;
    }

    public void toBBCode(StringBuilder text) {
        text.append(listOpeningTag);
        for (ListItem el : elementList) {
            el.toBBCode(text);
        }
        text.append(listClosingTag);
    }
}
