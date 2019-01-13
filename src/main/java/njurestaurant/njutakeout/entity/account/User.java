package njurestaurant.njutakeout.entity.account;

import njurestaurant.njutakeout.entity.food.Food;
import njurestaurant.njutakeout.entity.order.Order;
import njurestaurant.njutakeout.publicdatas.account.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "avatarUrl")
    private String avatarUrl;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private int role;
    @Column(name = "tableId")
    private int tableId;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonalCard> cards;
//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    private List<Order> orders;
//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    private List<Food> foods;

    public User() {
    }

    public User(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, int role, int tableId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.tableId = tableId;
    }

    public User(String username, String password, int role, List<PersonalCard> cards) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.tableId = tableId;
        this.cards = cards;
    }

    public List<PersonalCard> getCards() {
        return cards;
    }

    public void setCards(List<PersonalCard> cards) {
        this.cards = cards;
    }

    //    public User(String avatarUrl, String username, String password, Role role, List<Order> orders, List<Food> foods) {
//        this.avatarUrl = avatarUrl;
//        this.username = username;
//        this.password = password;
//        this.role = role;
//        this.orders = orders;
//        this.foods = foods;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }
//
//    public List<Food> getFoods() {
//        return foods;
//    }
//
//    public void setFoods(List<Food> foods) {
//        this.foods = foods;
//    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
