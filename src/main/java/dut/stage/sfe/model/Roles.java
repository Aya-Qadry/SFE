package dut.stage.sfe.model;

import java.lang.Integer;
import java.util.Set;

import jakarta.persistence.*;
 
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // @OneToMany(mappedBy = "role")
    // private Set<User> users;
    
    // public Set<User> getUsers() {
    //     return users;
    // }
    // public void setUsers(Set<User> users) {
    //     this.users = users;
    // }

    @Column(name = "name")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Roles() {
    }
    public Roles(int id, String name) {
        this.id = id;
        this.name = name;
    }
}