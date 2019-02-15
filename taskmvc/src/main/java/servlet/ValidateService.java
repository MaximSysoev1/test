package servlet;

import java.util.List;

public class ValidateService {
    private static ValidateService instance = null;
    private final Store store = DbStore.getInstance();

    private ValidateService() {}

    public static synchronized ValidateService getInstance() {
        if (instance == null) {
            instance = new ValidateService();
        }
        return instance;
    }

    public void close() {
        store.close();
    }

    public boolean containItem(Item item) {
        return store.containItem(item);
    }

    public boolean containCategory(Category category) {
        return store.containCategory(category);
    }

    public void addItem(Item item) {
        store.addItem(item);
    }

    public void addCategory(Category category) {
        store.addCategory(category);
    }

    public List<Item> findAllItems() {
        return  store.findAllItems();
    }

    public List<Item> findAllItemsById(String id) { return  store.findAllItemsById(id); }

    public List<Category> findAllCategoryes() {
        return  store.findAllCategoryes();
    }

    public Item findItemById(int key) {return store.findItemById(key);}

    public Category findCatById(int key) {return store.findCatById(key);}

    public void updateItem(int id, Item item) { store.updateItem(id, item); }

    public void updateCat(int id, Category category) { store.updateCat(id, category);}

    public int findByLogin(String login) {
        return store.findByLogin(login);
    }

    public int listItemsSize() {
        return store.listItemsSize();
    }

    public int listCategoruesSize() {
        return store.listCategoruesSize();
    }
}
