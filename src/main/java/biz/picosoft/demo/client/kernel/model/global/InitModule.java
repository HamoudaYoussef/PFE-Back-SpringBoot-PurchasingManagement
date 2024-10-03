package biz.picosoft.demo.client.kernel.model.global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class InitModule implements Serializable {

    private String packageName;
    private String version;
    private String defaultName;
    private String schema;
    private List<String> aclClass = new ArrayList<>();

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public List<String> getAclClass() {
        return aclClass;
    }

    public void setAclClass(List<String> aclClass) {
        this.aclClass = aclClass;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
