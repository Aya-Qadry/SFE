package dut.stage.sfe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Component
@Table(name = "user")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || user_id == 0)
            return null;
        return "/users-photos/" + user_id + "/" + photos;
    }

    @Column(nullable = true, length = 128)
    private String photos;

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @NotEmpty(message = "Fill the first name field")
    private String firstname;

    @NotEmpty(message = "Fill the last name field")
    private String lastname;

    @Email
    @NotEmpty(message = "Fill the email address field")
    private String emailaddress;

    @NotEmpty(message = "Fill the phone number field")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Pattern(regexp = "[0-9]+", message = "Phone number must contain only digits")
    private String phonenumber;

    // @NotEmpty(message = "Fill the password field")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "Fill the CIN field")
    @Size(min = 4, max = 12, message = "CIN must be between 4 and 12 characters")
    @Pattern(regexp = "[A-Za-z]+\\d*", message = "CIN must contain at least one character")
    private String cin;

    @NotEmpty(message = "Select the correspondant gender")
    private String gender;

    @CreationTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotNull(message = "Choose a correspondant role")
    private Set<Role> roles = new HashSet<>();

    public boolean hasAuthority(String roleName) {
        Iterator<Role> it = this.roles.iterator();
        while (it.hasNext()) {
            Role role = it.next();
            System.out.println(role.getName());
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public User() {
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public User(Request request) {
        this.firstname = request.getFirstname();
        this.lastname = request.getLastname();
        this.password = request.getPassword();
        this.emailaddress = request.getEmailaddress();
        this.phonenumber = request.getPhonenumber();
        this.cin = request.getCin();
        this.gender = request.getGender();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
