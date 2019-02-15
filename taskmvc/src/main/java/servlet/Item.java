package servlet;

public class Item {
    private int id;
    private String image;
    private String itemName;
    private String description;
    private int price;
    private int volume;
    private int categoryId;

    public Item() {}

    public Item(int id, String image, String itemName, String description, int price, int volume, int categoryId) {
        this.id = id;
        this.image = image;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.volume = volume;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
