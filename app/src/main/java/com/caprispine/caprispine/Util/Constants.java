package com.caprispine.caprispine.Util;

import com.caprispine.caprispine.pojo.user.AdminUserPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;

/**
 * Created by sunil on 16-11-2017.
 */

public class Constants {

    public static final int GENDER_DEFAULT=0;
    public static final int GENDER_MALE=1;
    public static final int GENDER_FEMALE=2;
    public static final int GENDER_OTHER=3;

    public static final int USER_TYPE_CITIZEN=1;
    public static final int USER_TYPE_LEADER=2;
    public static final int USER_TYPE_SUB_LEADER=3;
    public static final int USER_TYPE_NONE=0;

    public static final int EXAM_START_ACTIVITY_INTENT=12345;

    public static PatientPOJO patientPOJO=null;
    public static TherapistPOJO therapistPOJO=null;
    public static StaffPOJO staffPOJO=null;
    public static AdminUserPOJO adminUserPOJO=null;

}
