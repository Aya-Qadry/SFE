// package dut.stage.sfe.model;


// import jakarta.persistence.*;
// import java.util.*; 

// @Entity
// @Table(name="user")
// public class User copy{

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name="user_id")
//     private int id;

//     private String firstname ; 
//     private String lastname ; 
//     private String emailaddress ;
//     private String phonenumber; 
//     private String password ;

//     @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//     @JoinTable(
//             name = "user_roles",
//             joinColumns = @JoinColumn(name = "user_id"),
//             inverseJoinColumns = @JoinColumn(name = "role_id")
//             )
//     private Set<Roles> roles = new HashSet<>();

//     public boolean hasAuthority(String roleName){
//         Iterator<Roles> it = this.roles.iterator() ; 
//         while(it.hasNext()){
//             Roles role = it.next();
//             System.out.println(role.getName());
//                 if(role.getName().equals(roleName)){
//                         return true ; 
//                 }
//         }
//         return false ; 
//     }
//     public void setId(int id) {
//         this.id = id;
//     }
//     public Set<Roles> getRoles() {
//         return roles;
//     }
//     public void setRoles(Set<Roles> roles) {
//         this.roles = roles;
//     }
//     public String getFirstname() {
//         return firstname;
//     }
//     public int getId() {
//         return id;
//     }
//     public void setFirstname(String firstnname) {
//         this.firstname = firstnname;
//     }
//     public String getLastname() {
//         return lastname;
//     }
//     public void setLastname(String lastname) {
//         this.lastname = lastname;
//     }
//     public String getEmailaddress() {
//         return emailaddress;
//     }
//     public void setEmailaddress(String emailaddress) {
//         this.emailaddress = emailaddress;
//     }
//     public String getPhonenumber() {
//         return phonenumber;
//     }
//     public void setPhonenumber(String phonenumber) {
//         this.phonenumber = phonenumber;
//     }
//     public String getPassword() {
//         return password;
//     }
//     public void setPassword(String password) {
//         this.password = password;
//     } 
// }
