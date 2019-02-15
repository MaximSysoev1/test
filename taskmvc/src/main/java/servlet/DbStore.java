package servlet;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DbStore implements Store, AutoCloseable {

    private static DbStore instance = new DbStore();
    public static DbStore getInstance() {
        return instance;
    }
    private BasicDataSource source;

    public DbStore() {
        this.source = new BasicDataSource();
        source.setDriverClassName("org.postgresql.Driver");
        source.setUrl("jdbc:postgresql://localhost:5432/warehouse");
        source.setUsername("postgres");
        source.setPassword("password");
        source.setMinIdle(5);
        source.setMaxIdle(10);
        source.setMaxOpenPreparedStatements(100);
        createTables();
        if (isEmpty()) {
            addRootUser();
        }
    }

    public void createTables() {
        try (Connection connection = source.getConnection()) {
            PreparedStatement st1 = connection.prepareStatement("create table if not exists public.roles(id serial primary key, name varchar(100))");
            PreparedStatement st2 = connection.prepareStatement("create table if not exists public.users(" +
                      "id serial primary key,  login varchar(100)," +
                      "password varchar(100)," +
                       "roles_id int references roles(id))");
            PreparedStatement st3 = connection.prepareStatement("create table if not exists public.category("+
                    "id serial primary key," +
                    "cat_name varchar(100)," +
                    "description varchar(300))");
            PreparedStatement st4 = connection.prepareStatement("create table if not exists public.items(" +
                    "id serial primary key," +
                    "image varchar(100),"+
                    "item_name varchar(100),"+
                    "description varchar (300)," +
                    "price int," +
                    "volume int," +
                    "category_id int references category(id))");
            st1.execute();
            st2.execute();
            st3.execute();
            st4.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isEmpty() {
        int count = 0;
        boolean result = false;
        try (Connection connection = source.getConnection();
             PreparedStatement ps = connection.prepareStatement("Select count(*) from users")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
                if (count == 0) {
                    result = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void addRootUser() {
        try (Connection connection = source.getConnection()) {
            final PreparedStatement ps = connection.prepareStatement("INSERT INTO roles(name) values ('admin')");
            PreparedStatement st2 = connection.prepareStatement("INSERT INTO roles(name) values ('user')");
            PreparedStatement st3 = connection.prepareStatement("INSERT INTO users(login, password, roles_id) VALUES ('admin', 'admin', 1)");
            PreparedStatement st4 = connection.prepareStatement("INSERT INTO users(login, password, roles_id) VALUES ('user', 'user', 2)");
            ps.execute();
            st2.execute();
            st3.execute();
            st4.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isCredentional(String login, String password) {
        boolean exists = false;
        try(Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("select * from users where login = ? and password = ?");
            st.setString(1, login);
            st.setString(2, password);
            if (st.executeQuery().next()) {
                exists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    public boolean checkInputName(String name) {
        boolean exists = false;
        if (name.equals("") || name.equals(null) || name == "") {
            exists = true;
        }
        return exists;
    }

    @Override
    public void close() {
        try {
            source.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean containItem(Item item) {
        try  (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("select item_name from items where item_name = ?");
            st.setString(1, item.getItemName());
            ResultSet rs = st.executeQuery();

            Item iDb = null;
            if (rs.next()) {
                final String itemName = rs.getString("item_name");
                iDb = new Item(0, "", itemName, "desc", 0, 0, 1);
            }

            if (iDb!=null) {
                if (item.getItemName().equals(iDb.getItemName())) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean containCategory(Category category) {
        try  (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("select cat_name from category where cat_name = ?");
            st.setString(1, category.getCatName());
            ResultSet rs = st.executeQuery();

            Category catDb = null;
            if (rs.next()) {
                final String catName = rs.getString("cat_name");
                catDb = new Category(0, catName, "");
            }

            if (catDb!=null) {
                if (category.getCatName().equals(catDb.getCatName())) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addItem(Item item) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("INSERT INTO items(image, item_name, description, price, volume, category_id) values(?,?,?,?,?,?)");
            st.setString(1, item.getImage());
            st.setString(2, item.getItemName());
            st.setString(3, item.getDescription());
            st.setInt(4, item.getPrice());
            st.setInt(5, item.getVolume());
            st.setInt(6, item.getCategoryId());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCategory(Category category) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("INSERT INTO category(cat_name, description) values(?,?)");
            st.setString(1, category.getCatName());
            st.setString(2, category.getDescription());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> findAllItems() {
        List<Item> itemsStore = new CopyOnWriteArrayList<Item>();
        try (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("select * from items");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getInt("id"), rs.getString("image"), rs.getString("item_name"), rs.getString("description"), rs.getInt("price"), rs.getInt("volume"), rs.getInt("category_id"));
                itemsStore.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemsStore;
    }

    @Override
    public List<Item> findAllItemsById(String id) {
        List<Item> itemsStore = new CopyOnWriteArrayList<Item>();
        try (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("select * from items where category_id = ?");
            st.setInt(1, Integer.parseInt(id));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getInt("id"), rs.getString("image"), rs.getString("item_name"), rs.getString("description"), rs.getInt("price"), rs.getInt("volume"), rs.getInt("category_id"));
                itemsStore.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemsStore;
    }

    @Override
    public List<Category> findAllCategoryes() {
        List<Category> categoryesStore = new CopyOnWriteArrayList<>();
        try (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("select * from category");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("cat_name"), rs.getString("description"));
                categoryesStore.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryesStore;
    }

    @Override
    public Item findItemById(int key) {
        Item item = new Item();
        try (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("select * from items where id = ?");
            st.setInt(1, key);
            ResultSet rs = st.executeQuery();
            rs.next();
            item.setId(rs.getInt("id"));
            item.setImage(rs.getString("image"));
            item.setItemName(rs.getString("item_name"));
            item.setDescription(rs.getString("description"));
            item.setPrice(rs.getInt("price"));
            item.setVolume(rs.getInt("volume"));
            item.setCategoryId(rs.getInt("category_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public Category findCatById(int key) {
        Category cat = new Category();
        try (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("select * from category where id = ?");
            st.setInt(1, key);
            ResultSet rs = st.executeQuery();
            rs.next();
            cat.setId(rs.getInt("id"));
            cat.setCatName(rs.getString("cat_name"));
            cat.setDescription(rs.getString("description"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cat;
    }

    @Override
    public void updateItem(int id, Item item) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("UPDATE items SET item_name=? WHERE id=?");
            st.setString(1, item.getItemName());
           /* st.setString(2, item.getDescription());
            st.setInt(3, item.getPrice());
            st.setInt(4, item.getVolume());
            st.setInt(5, item.getCategoryId());*/
            st.setInt(2, id);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateCat(int id, Category category) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("UPDATE category SET cat_name=?, description=? WHERE id=?");
            st.setString(1, category.getCatName());
            st.setString(2, category.getDescription());
            st.setInt(3, id);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int findByLogin(String login) {
        int id = 0;
        try (Connection connection = source.getConnection()) {
            PreparedStatement st = connection.prepareStatement("select * from users where login = ?");
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            rs.next();
            id = rs.getInt("roles_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int listItemsSize() {
        return this.findAllItems().size();
    }

    @Override
    public int listCategoruesSize() {
        return this.findAllCategoryes().size();
    }
}
