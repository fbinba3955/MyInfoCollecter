package com.kiana.sjt.myinfocollecter.tts;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/8.
 */

public class ZenTaoLogin implements Serializable {
    /**
     * status : success
     * user : {"id":"31","account":"cuiliqing","email":"","realname":"崔丽青","gender":"f","dept":"15","role":"dev","company":"南京特捷交通技术系统有限公司"}
     */

    private String status;
    private UserBean user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean implements Serializable {
        /**
         * id : 31
         * account : cuiliqing
         * email :
         * realname : 崔丽青
         * gender : f
         * dept : 15
         * role : dev
         * company : 南京特捷交通技术系统有限公司
         */

        private String id;
        private String account;
        private String email;
        private String realname;
        private String gender;
        private String dept;
        private String role;
        private String company;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }
    }
}
