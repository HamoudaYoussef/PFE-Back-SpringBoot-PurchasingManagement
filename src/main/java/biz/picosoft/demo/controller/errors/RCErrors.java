package biz.picosoft.demo.controller.errors;

public class RCErrors {
    public static final String Entity_Doc_param = "DocParam";
    public static final String ERR_Key_doc_param_null = "doc_param.null";
    public static final String ERR_Msg_doc_param_null = "doc param not found";

//    public static final String Entity_requestCase = "MmOutbound";
//    public static final String ERR_Msg_requestCase_null = "MmOutbound not found";
//    public static final String ERR_Key_requestCase_null = "MmOutbound.null";

    public static final String ERR_Msg_Company_null = "Company not found";
    public static final String ERR_Key_Company_null = "Company.null";


    public static final String Entity_Outbound = "MmInbound";
    public static final String ERR_Msg_Outbound_null = "MmInbound not found";
    public static final String ERR_Key_Outbound_null = "MmInbound.null";

    public static final String Entity_MmTask = "MmTask";
    public static final String ERR_Msg_Task_null = "task not found";
    public static final String ERR_Key_Task_null = "task.null";

    public static final String ERR_Msg_Task_initialized = "Unable to delete an initialized task";
    public static final String ERR_Key_Task_initialized = "task.initialized";

    public static final String ERR_Msg_ActorSid_null = "actor sid not found";
    public static final String ERR_Key_ActorSid_null = "actorsid.null";

    public static final String ERR_Msg_dueDate_null = "dueDate not found";
    public static final String ERR_Key_dueDate_null = "dueDate.null";

    public static final String ERR_Key_not_authorized = "not_authorized";
    public static final String ERR_Msg_not_authorized = "not authorized";

    public static final String ERR_Key_not_authorized_not_belongs_company = "The user does not belong to any company.";
    public static final String ERR_Msg_not_authorized_not_belongs_company  = "The user does not belong to any company.";

    public static final String ERR_Key_profile_not_found = "profile_not_found";
    public static final String ERR_Msg_profile_not_found = "profile not found";


    public static final String ERR_Msg_Task_type_null = "task type not found";
    public static final String ERR_Key_Task_type_null = "task.type.null";




    public static final String ERR_Msg_Document_null = "document not found";
    public static final String ERR_Key_Document_null = "document.null";

    public static final String ERR_Msg_Transporter_null = "transporter not found";
    public static final String ERR_Key_Transporter_null = "transporter.not.found";

    public static final String ERR_Msg_RefTransporter_null = "ref transporter is obligatory ";
    public static final String ERR_Key_RefTransporter_null = "refTransporter.is.obligatory";



    public static final String Entity_Internal = "Internal";
    public static final String ERR_Msg_Internal_null = "Internal not found";
    public static final String ERR_Key_Internal_null = "Internal.null";

    public static final String ERR_Key_not_authorized_to_create_task = "not_authorized_to create task";
    public static final String ERR_Msg_not_authorized_to_create_task = "not authorized to create task";

    public static final String ERR_Key_not_authorized_to_update_task = "not_authorized_to_update_task";
    public static final String ERR_Msg_not_authorized_to_update_task = "not authorized to update task";

    public static final String Entity_requestCase = "requestCase";
    public static final String ERR_Msg_requestCase_null = "requestCase not found";
    public static final String ERR_Key_requestCase_null = "requestCase.null";
    //An error occurred while executing the rule:

    public static final String ERR_Msg_drools = "An error occurred while executing the rule";
    public static final String ERR_Key_drools = "error.executing.drools";


    public static final String ERR_Msg_Node_name_notValid = "Node name not valid";
    public static final String ERR_Key_Node_name_notValid = "NodeName.notValid";

    public static final String ERR_Msg_not_autorized_create_node = "not autorized to create new Node";
    public static final String ERR_Key_not_autorized_create_Node = "not.autorized.create.Node";


    public static final String ERR_Msg_wf_null = "requestCase is not initialized in workflow";
    public static final String ERR_Key_wf_null = "process_id.null";


    public static final String ERR_Msg_requestCase_subject_null = "the subject must exist to make a copy";
    public static final String ERR_Key_requestCase_subject_null = "subject.null";

    public static final String ERR_Msg_AclClass_null = "AclClass not found";
    public static final String ERR_Key_AclClass_null = "AclClass.null";


    public static final String Entity_Tag = "Tag";
    public static final String ERR_Msg_delete_tag = "could not to delete tag";
    public static final String ERR_Key_delete_tag = "tag already in use";

    public static final String ERR_Msg_default_state_null = "Class without default state";
    public static final String ERR_Key_default_state_null = "defaultState.null";

    public static final String ERR_Msg_delete_requestCase = "cannot delete a document is not draft";
    public static final String ERR_Key_delete_requestCase = "document.isNot.draft";

    public static final String ERR_Msg_delete_requestCase_ref = "Unable to delete a document with reference";
    public static final String ERR_Key_delete_requestCase_ref = "document.with.reference";

    public static final String ERR_Msg_requestCase_ref = "requestCase with reference";
    public static final String ERR_Key_requestCase_ref = "document.with.reference";

}
