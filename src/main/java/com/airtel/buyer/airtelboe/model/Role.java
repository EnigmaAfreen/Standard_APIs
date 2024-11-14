package com.airtel.buyer.airtelboe.model;

public enum Role {
    ROLE_MASTER("MASTER"),
    ROLE_BUYER("BUYER"),
    ROLE_PHD_SUPER_ADMIN("PHD SUPER ADMIN"),
    ROLE_PHD_MAKER("PHD MAKER"),
    ROLE_PHD_CHECKER("PHD CHECKER"),
    ROLE_WAREHOUSE("WAREHOUSE"),
    ROLE_AP_INQUIRY("AP INQUIRY"),
    ROLE_INVOICE_REGISTER("INVOICE REGISTER"),
    ROLE_FDOA_APPROVER("FDOA_APPROVER"),
    ROLE_DOA("DOA"),
    ROLE_SCM_ADMIN("SCM_ADMIN"),
    ROLE_IP("IP"),
    ROLE_SCM_LOB("SCM_LOB"),
    ROLE_SCM_GVIEW("SCM_GVIEW"),
    ROLE_SRN_SCHEDULER_CONFIGURATOR("SRN SCHEDULER CONFIGURATOR"),
    ROLE_CCO("CCO"),
    ROLE_SUPERVISOR("SUPERVISOR"),
    ROLE_COMPLIANCE("COMPLIANCE"),
    ROLE_CA("CA"),
    ROLE_SLT("SLT"),
    ROLE_CPO("CPO") ;

    private final String roleValue;

    Role(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getRoleValue() {
        return roleValue;
    }
}
