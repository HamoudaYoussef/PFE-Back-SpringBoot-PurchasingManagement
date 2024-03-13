package biz.picosoft.demo.client.kernel.model.global;


import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import biz.picosoft.demo.config.BeanUtil;
import biz.picosoft.demo.config.audit.Auditable;
import biz.picosoft.demo.config.slf4j.Slf4jMDCFilterConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@RequestScope
public class CurrentUser {

    private String module;
    private String samaccountname;
    private Long userIdDb;
    private String displayName;
    private String token;
    private String mail;
    private String uuid;
    private String adrIp;
    private String localAddr;
    private String remoteAddr;
    private List<String> authority;
    private List<String> sid;
    private String employeId;
    private Long employeIdDb;
    private String employeSid;
    private String employeDisplayName;

    private String employeType;
    private String profileName;
    private String tenantId;
    private String securityKey;
    private Integer profileSecuriteLevel;
    private CurrentUser effectiveUser;
    private String stringResTrad;


    private Long agencyId;
    private String agencyName;
    private String agencyCode;

    private String regionName;
    private Long regionId;
    private String regionCode;

    private Long customerId;
    private String customerName;
    private String customerCode;

    private Long customerSiteId;
    private String customerSiteName;
    private String customerSiteCode;


    private String originePaysCode;
    private String originePaysName;
    private String organizationName;
    private String defaultLanguage;


    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getEmployeType() {
        return employeType;
    }

    public void setEmployeType(String employeType) {
        this.employeType = employeType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Long getCustomerSiteId() {
        return customerSiteId;
    }

    public void setCustomerSiteId(Long customerSiteId) {
        this.customerSiteId = customerSiteId;
    }

    public String getCustomerSiteName() {
        return customerSiteName;
    }

    public void setCustomerSiteName(String customerSiteName) {
        this.customerSiteName = customerSiteName;
    }

    public String getCustomerSiteCode() {
        return customerSiteCode;
    }

    public void setCustomerSiteCode(String customerSiteCode) {
        this.customerSiteCode = customerSiteCode;
    }

    public String getOriginePaysCode() {
        return originePaysCode;
    }

    public void setOriginePaysCode(String originePaysCode) {
        this.originePaysCode = originePaysCode;
    }

    public String getOriginePaysName() {
        return originePaysName;
    }

    public void setOriginePaysName(String originePaysName) {
        this.originePaysName = originePaysName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public CurrentUser getEffectiveUser() {
        return effectiveUser;
    }

    public void setEffectiveUser(CurrentUser effectiveUser) {
        this.effectiveUser = effectiveUser;
    }

    public Long getEmployeIdDb() {
        return employeIdDb;
    }

    public void setEmployeIdDb(Long employeIdDb) {
        this.employeIdDb = employeIdDb;
    }

    public String getEmployeDisplayName() {
        return employeDisplayName;
    }

    public void setEmployeDisplayName(String employeDisplayName) {
        this.employeDisplayName = employeDisplayName;
    }

    public String getSamaccountname() {
        return samaccountname;
    }

    public void setSamaccountname(String samaccountname) {
        this.samaccountname = samaccountname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLocalAddr() {
        return localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    public Integer getProfileSecuriteLevel() {
        return profileSecuriteLevel;
    }

    public void setProfileSecuriteLevel(Integer profileSecuriteLevel) {
        this.profileSecuriteLevel = profileSecuriteLevel;
    }

    public String getEmployeSid() {
        return employeSid;
    }

    public void setEmployeSid(String employeSid) {
        this.employeSid = employeSid;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAdrIp() {
        return adrIp;
    }

    public void setAdrIp(String adrIp) {
        this.adrIp = adrIp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getAuthority() {
        return authority;
    }

    public void setAuthority(List<String> authority) {
        this.authority = authority;
    }

    public List<String> getSid() {
        return sid;
    }

    public void setSid(List<String> sid) {
        this.sid = sid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmployeId() {
        return employeId;
    }

    public void setEmployeId(String employeId) {
        this.employeId = employeId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getStringResTrad() {
        return stringResTrad;
    }

    public void setStringResTrad(String stringResTrad) {
        this.stringResTrad = stringResTrad;
    }

    public Long getUserIdDb() {
        return userIdDb;
    }

    public void setUserIdDb(Long userIdDb) {
        this.userIdDb = userIdDb;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    @PostConstruct
    public void init() {
        KernelInterface currentuserInterface = BeanUtil.getBean(KernelInterface.class);
        RequestAttributes req = RequestContextHolder.getRequestAttributes();
        CurrentUser currentUser = currentuserInterface.getCurrentUser();
        String uuid = null;
        if (req != null) {
            HttpServletResponse response = ((ServletRequestAttributes) req).getResponse();


            if (currentUser.getUuid() == null) {
                uuid = response.getHeaders(Slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER).toString();
                uuid = uuid.replace("[", "");
                uuid = uuid.replace("]", "");
            } else uuid = currentUser.getUuid();

        }

        this.userIdDb = currentUser.getUserIdDb();
        this.tenantId = currentUser.getTenantId();
        this.securityKey = currentUser.getSecurityKey();
        this.stringResTrad = currentUser.getStringResTrad();
        this.profileName = currentUser.getProfileName();
        this.profileSecuriteLevel = currentUser.getProfileSecuriteLevel();
        this.samaccountname = currentUser.getSamaccountname();
        this.displayName = currentUser.getDisplayName();
        this.mail = currentUser.getMail();
        this.token = currentUser.getToken();
        this.uuid = uuid;
        this.module = currentUser.getModule();
        this.adrIp = currentUser.getAdrIp();
        this.localAddr = currentUser.getLocalAddr();
        this.remoteAddr = currentUser.getRemoteAddr();
        this.authority = currentUser.getAuthority();
        this.sid = currentUser.getSid();
        this.employeId = currentUser.getEmployeId();
        this.employeSid = currentUser.getEmployeSid();
        this.employeDisplayName = currentUser.getEmployeDisplayName();
        this.employeIdDb = currentUser.getEmployeIdDb();
        this.employeType = currentUser.getEmployeType();
        this.effectiveUser = currentUser.getEffectiveUser();

        this.agencyCode = currentUser.getAgencyCode();
        this.agencyId = currentUser.getAgencyId();
        this.agencyName = currentUser.getAgencyName();

        this.regionId = currentUser.getRegionId();
        this.regionCode = currentUser.getRegionCode();
        this.regionName = currentUser.getRegionName();

        this.customerSiteId = currentUser.getCustomerSiteId();
        this.customerSiteName = currentUser.getCustomerSiteName();
        this.customerSiteCode = currentUser.getCustomerSiteCode();

        this.customerId = currentUser.getCustomerId();
        this.customerCode = currentUser.getCustomerCode();
        this.customerName = currentUser.getCustomerName();
    }

    @PrePersist
    public void setLastCreate(Auditable o) {
        this.init();
        if (getSamaccountname() != null) {
            o.setSyscreatedBy(getSamaccountname());
            o.setSysupdatedBy(getSamaccountname());
        }
    }

    @PreUpdate
    public void setLastUpdate(Auditable o) {
        if (getNameCurrentUser() != null) {
            o.setSysupdatedBy(getNameCurrentUser());
        }
    }

    public String getNameCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            authentication.getName();
        }
        return null;
    }

}
