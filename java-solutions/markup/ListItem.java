package markup;
import java.util.List;

public class ListItem {
    List<ListItemInterface> listItem;
    public ListItem(List<ListItemInterface> item) {
        listItem = item;
    }
    void toBBCode(StringBuilder text) {
        text.append("[*]");
        for (ListItemInterface item : listItem) {
            item.toBBCode(text);
        }
    }
}
