package markup;

public class UnorderedList extends List{
    public UnorderedList(java.util.List<ListItem> list) {
        super(list);
        listOpeningTag = "[list]";
        listClosingTag = "[/list]";
    }
}
