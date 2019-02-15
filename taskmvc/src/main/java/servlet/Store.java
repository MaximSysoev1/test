package servlet;

import java.util.List;

public interface Store {
    void close();
    void addItem(Item item);
    void addCategory(Category category);
    boolean containItem(Item item);
    boolean containCategory(Category category);
    List<Item> findAllItems();
    List<Item> findAllItemsById(String id);
    List<Category> findAllCategoryes();
    Item findItemById(int key);
    Category findCatById(int key);
    void updateItem(int id, Item item);
    void updateCat(int id, Category category);
    int findByLogin(String login);
    int listItemsSize();
    int listCategoruesSize();
}
