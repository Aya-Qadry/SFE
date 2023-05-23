package dut.stage.sfe.model;

import java.lang.Integer;
import java.util.Set;

import jakarta.persistence.*;
@Entity
@Table(name = "roles")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;
     
    @Column(nullable = false, length = 45)
    private String name;
 
   

    public Role(int id) {
        this.id = id;
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role() { }
      
    public Role(String name) {
        this.name = name;
    }
     
    
 
    @Override
    public String toString() {
        return this.name;
    }

  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   
 
}