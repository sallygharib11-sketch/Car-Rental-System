/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author salley
 */
public class Employe {
  private int Employeeid ;
 private String username;
 private String password;
  private String role;
 private String firstName;
    private String lastName;
    private String phone;
     private Date lastlogin;  
     
      public Employe(String username, String password, String role,
                    String firstName, String lastName,
                    String phone, Date lastLogin) {

        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.lastlogin = lastLogin;
    }

    public Employe(int employeeId, String username, String password,
                    String role, String firstName, String lastName,
                    String phone, Date lastLogin) {

        Employeeid = employeeId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
       this.lastlogin = lastLogin;
    }
       public int getEmployeeid() {
            return Employeeid;       }
        public String getusername() {
            return username;    } 
        public String getpassword() {
            return password;    } 
        public String getrole() {
            return role;     }
        public String getFirstName() {
            return firstName;   }
        public String getLastName() {
            return lastName;    }
        public String getPhone() {
            return phone;       }
        public Date getlastlogin() {
            return lastlogin;     }
      public void setEmployeeid(int Employeeid) {
        this.Employeeid =Employeeid;
    }
      public void  setusername(String username){
     this.username = username;}
      public void setpassword(String password) {
        this.password = password;}
      public void setrole(String role) {
        this.role = role;}
       public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
       public void setLastName(String lastName) {
        this.lastName = lastName;
    }
      public void setPhone(String phone) {
        this.phone = phone;
    }
      public void setlastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;}
      
}
