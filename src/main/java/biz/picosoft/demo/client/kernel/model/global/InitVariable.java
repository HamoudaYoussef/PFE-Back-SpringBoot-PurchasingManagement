package biz.picosoft.demo.client.kernel.model.global;

import biz.picosoft.demo.client.kernel.model.pm.Variable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class InitVariable implements Serializable {

    private List<Variable> variable = new ArrayList<>();
    private List<String> roleName = new ArrayList<>();
    private List<InitProfile> profiles;


    public List<Variable> getVariable() {
        return variable;
    }

    public void setVariable(List<Variable> variable) {
        this.variable = variable;
    }

    public List<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(List<String> roleName) {
        this.roleName = roleName;
    }

    public List<InitProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<InitProfile> profiles) {
        this.profiles = profiles;
    }

    public static class InitProfile {
        private String profileName;
        private String[] profileRole;

        public InitProfile() {
        }

        public InitProfile(String profileName, String[] profileRole) {
            this.profileName = profileName;
            this.profileRole = profileRole;
        }

        public String getProfileName() {
            return profileName;
        }

        public void setProfileName(String profileName) {
            this.profileName = profileName;
        }

        public String[] getProfileRole() {
            return profileRole;
        }

        public void setProfileRole(String[] profileRole) {
            this.profileRole = profileRole;
        }
    }
}
