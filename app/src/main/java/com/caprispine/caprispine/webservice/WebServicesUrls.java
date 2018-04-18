package com.caprispine.caprispine.webservice;

/**
 * Created by sunil on 20-01-2017.
 */

public class WebServicesUrls {

        public static final String BASE_URL = "http://10.0.2.2/caprispine/";
//    public static final String BASE_URL = "http://www.caprispine.in/api/caprispine/";

    public static final String ASSESSMENT_IMAGE_BASE_URL = BASE_URL + "upload/patientassessment/";
    public static final String PROFILE_PIC_BASE_URL = BASE_URL + "usermanagement/";
    public static final String BODY_CHART_BASE_URL = BASE_URL + "patientassessment/bodychart/upload/";
    public static final String PHOTO_VIDEO_BASE_URL = BASE_URL + "patientassessment/functionalassessment/functionalproblem/";
    public static final String INVESTIGATION_BASE_URL = BASE_URL + "patientassessment/investigation/";
    public static final String TREATMENT_GIVEN_URL = BASE_URL + "patientassessment/treatmentgiven/";
    public static final String CHAT_MEDIA_URL = BASE_URL + "chat/";

    public static final String CASE_REPORT_URL = BASE_URL + "report/pdf/tcpdf/caseprint.php";
    public static final String REPORT_BASE_URL = BASE_URL + "report/";

    public static final String USER_CRUD = BASE_URL + "usermanagement/usercrud.php";
    public static final String BRANCH_CRUD = BASE_URL + "branch/branchcrud.php";
    public static final String TREATMENT_CRUD = BASE_URL + "treatment/treatmentcrud.php";
    public static final String PATIENT_ALLOCATION_CRUD = BASE_URL + "patientallocation/patientallocationcrud.php";
    public static final String TREATMENT_GIVEN_CRUD = BASE_URL + "patientassessment/treatmentgiven/treatmentgivencrud.php";
    public static final String REPORT_CRUD = BASE_URL + "report/api/reportcrud.php";

    public static final String CHIEF_COMPLAINT_CRUD = BASE_URL + "patientassessment/cheifcomplaints/chiefcomplaintcrud.php";
    public static final String HISTORY_CRUD = BASE_URL + "patientassessment/history/historycrud.php";
    public static final String PAIN_CRUD = BASE_URL + "patientassessment/pain/paincrud.php";
    public static final String PHYSICAL_CRUD = BASE_URL + "patientassessment/physicalExamination/PhysicalCrud.php";
    public static final String TREATMENT_PLAN_CRUD = BASE_URL + "patientassessment/treatmentplan/treatmentplancrud.php";
    public static final String CASE_NOTE_CRUD = BASE_URL + "patientassessment/case_note/case_notecrud.php";
    public static final String PROGRESS_NOTE_CRUD = BASE_URL + "patientassessment/progress_note/progress_notecrud.php";
    public static final String REMARK_NOTE_CRUD = BASE_URL + "patientassessment/remark_note/remark_notecrud.php";


    public static final String HIP_CRUD = BASE_URL + "patientassessment/motor/hip/hipcrud.php";
    public static final String KNEE_CRUD = BASE_URL + "patientassessment/motor/knee/kneecrud.php";
    public static final String ANKLE_CRUD = BASE_URL + "patientassessment/motor/ankle/anklecrud.php";
    public static final String TOES_CRUD = BASE_URL + "patientassessment/motor/toes/toescrud.php";
    public static final String SHOULDER_CRUD = BASE_URL + "patientassessment/motor/shoulder/shouldercrud.php";
    public static final String ELBOW_CRUD = BASE_URL + "patientassessment/motor/elbow/elbowcrud.php";
    public static final String FOREARM_CRUD = BASE_URL + "patientassessment/motor/forearm/forearmcrud.php";
    public static final String WRIST_CRUD = BASE_URL + "patientassessment/motor/wrist/wristcrud.php";
    public static final String FINGER_CRUD = BASE_URL + "patientassessment/motor/fingers/fingercrud.php";
    public static final String SACROILIC_CRUD = BASE_URL + "patientassessment/motor/sacroilic/sacroiliccrud.php";
    public static final String COMBINED_CRUD = BASE_URL + "patientassessment/motor/combined/combinedcrud.php";
    public static final String CERVICAL_CRUD = BASE_URL + "patientassessment/motor/cervical/cervicalcrud.php";
    public static final String THORACIC_CRUD = BASE_URL + "patientassessment/motor/thoracic/thoraciccrud.php";
    public static final String LUMBAR_CRUD = BASE_URL + "patientassessment/motor/lumbar/lumbarcrud.php";

    public static final String SENSORY_CRUD = BASE_URL + "patientassessment/sensory/sensorycrud.php";

    public static final String NEURO_CRUD = BASE_URL + "patientassessment/neuroexam/neurocrud.php";
    public static final String NTP_CRUD = BASE_URL + "patientassessment/ntpexam/ntpcrud.php";
    public static final String SPECIAL_CRUD = BASE_URL + "patientassessment/specialtest/specialcrud.php";

    public static final String INVESTIGATION_CRUD = BASE_URL + "patientassessment/investigation/investigationcrud.php";
    public static final String PHYSIOTHERAPUTIC_CRUD = BASE_URL + "patientassessment/physiotherapeutic_diagnosis/physiotherapeutic_diagnosiscrud.php";
    public static final String MEDICAL_CRUD = BASE_URL + "patientassessment/medical_diagnosis/medical_diagnosiscrud.php";

    public static final String PATIENT_WALLET = BASE_URL + "patientwallet/patientwalletcrud.php";
    public static final String EXPENSE_CRUD = BASE_URL + "expense/expensecrud.php";
    public static final String PROBLEM_CRUD = BASE_URL + "patientassessment/functionalassessment/functionalproblem/problemcrud.php";
    public static final String BODY_CHART_CRUD = BASE_URL + "patientassessment/bodychart/bodychartcrud.php";
    public static final String APPOINTMENT_CRUD = BASE_URL + "appointment/appointmentcrud.php";
    public static final String CHAT_CRUD = BASE_URL + "chat/chatcrud.php";


    public static String getCaseUrl(String patient_id, String branch_id) {
        return CASE_REPORT_URL + "?patient_id=" + patient_id + "&branch_id=" + branch_id;
    }

    public static String getIncomeStatementReport(String branch_id, String start_date, String end_date) {
        return REPORT_BASE_URL + "/incomestatement.php?branch_id=" + branch_id + "&start_date=" + start_date + "&end_date=" + end_date;
    }

    public static String getIncomeTreatmentWiseReportUrl(String branch_id, String treatment_id) {
        return REPORT_BASE_URL + "incometreatmentwise.php?branch_id=" + branch_id + "&treatment_id=" + treatment_id;
    }

    public static String getIncomeStatementTherapistWise(String branch_id, String therapist_id, String start_date, String end_date) {
        return REPORT_BASE_URL + "incomestatementtherapistwise.php?branch_id=" + branch_id + "&start_date=" + start_date + "&end_date=" + end_date + "&therapist_id=" + therapist_id;
    }

    public static String getPatientWalletIncome(String branch_id, String start_date, String end_date, String payment_mode) {
        return REPORT_BASE_URL + "incomefrompatient.php?branch_id=" + branch_id + "&start_date=" + start_date + "&end_date=" + end_date + "&payment_mode=" + payment_mode;
    }

    public static String getIncomePatientWise(String branch_id, String patient_id, String start_date, String end_date) {
        return REPORT_BASE_URL + "incomepatientwise.php?branch_id=" + branch_id + "&start_date=" + start_date + "&end_date=" + end_date + "&patient_id=" + patient_id;
    }

    public static String getCaseReport(String branch_id, String patient_id, String start_date,String end_date) {
        return REPORT_BASE_URL + "addcasereport.php?branch_id=" + branch_id + "&start_date=" + start_date + "&end_date=" + end_date+"&patient_id=" + patient_id;
    }

    public static String getProgressReport(String branch_id, String patient_id,String start_date,String end_date){
        return REPORT_BASE_URL + "addprogressreport.php?branch_id=" + branch_id + "&start_date=" + start_date + "&end_date=" + end_date+"&patient_id=" + patient_id;
    }

    public static String getRemarkReport(String branch_id, String patient_id, String start_date,String end_date){
        return REPORT_BASE_URL + "addremarkreport.php?branch_id=" + branch_id + "&start_date=" + start_date + "&end_date=" + end_date+"&patient_id=" + patient_id;
    }

    public static String getExpenseurl(String branch_id, String start_date, String end_date) {
        return REPORT_BASE_URL + "expensereport.php?branch_id=" + branch_id + "&start_date=" + start_date + "&end_date=" + end_date;
    }

}
