package hu.szocialis_etkeztetes.szocialis_etkeztetes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String displayName;
    private String password;
    private String roleName;
    private boolean adminRoleType = false;
    private boolean chefRoleType = false;
    private boolean warehouseRoleType = false;
    public boolean active = false;

    public User() {
    }

    public User(String userName, String displayName, String password, String roleName, boolean adminRoleType, boolean chefRoleType, boolean warehouseRoleType, boolean active) {
        this.userName = userName;
        this.displayName = displayName;
        this.password = password;
        this.roleName = roleName;
        this.adminRoleType = adminRoleType;
        this.chefRoleType = chefRoleType;
        this.warehouseRoleType = warehouseRoleType;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isAdminRoleType() {
        return adminRoleType;
    }

    public void setAdminRoleType(boolean adminRoleType) {
        this.adminRoleType = adminRoleType;
    }

    public boolean isChefRoleType() {
        return chefRoleType;
    }

    public void setChefRoleType(boolean chefRoleType) {
        this.chefRoleType = chefRoleType;
    }

    public boolean isWarehouseRoleType() {
        return warehouseRoleType;
    }

    public void setWarehouseRoleType(boolean warehouseRoleType) {
        this.warehouseRoleType = warehouseRoleType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
