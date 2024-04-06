package markup;

public class OrderedList extends List{
    public OrderedList(java.util.List<ListItem> list) {
        super(list);
        listOpeningTag = "[list=1]";
        listClosingTag = "[/list]";
    }
}
