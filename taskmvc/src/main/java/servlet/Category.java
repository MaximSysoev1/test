package servlet;

public class Category {
    private int id;
    private String catName;
    private String description;

    public Category() {}

    public Category(int id, String catName, String description) {
        this.id = id;
        this.catName = catName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
