package biz.picosoft.demo.errors;


public class DemandeErrors {

    public static final String ERR_Key_not_authorized = "not_authorized";
    public static final String ERR_Msg_not_authorized = "not authorized";

    public static final String ERR_Key_profile_not_found = "profile_not_found";
    public static final String ERR_Msg_profile_not_found = "profile not found";

    public static final String Entity_Invoice = "Invoice";
    public static final String ERR_Msg_Invoice_null = "Demande not found";
    public static final String ERR_Key_Invoice_null = "Demande.null";


    public static final String ERR_Msg_wf_null = "Demande is not initialized in workflow";
    public static final String ERR_Key_wf_null = "process_id.null";

    public static final String ERR_Msg_default_state_null = "Class without default state";
    public static final String ERR_Key_default_state_null = "defaultState.null";


    //throw new BadRequestAlertException("Class without default state ", ENTITY_NAME, "Class without default state");


}
