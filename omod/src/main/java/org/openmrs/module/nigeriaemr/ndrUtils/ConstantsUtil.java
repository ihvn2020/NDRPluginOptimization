/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrUtils;

import java.util.HashMap;
import java.util.Map;
import static org.openmrs.module.nigeriaemr.ndrfactory.LabDictionary.CD4_Count_Concept_Id;

/**
 * @author MORRISON.I
 */
public class ConstantsUtil {
	
    
    public static final int PEPFAR_IDENTIFIER_INDEX = 4;

    public static final int HOSPITAL_IDENTIFIER_INDEX = 5;

    public static final int OTHER_IDENTIFIER_INDEX = 3;

    public static final int HTS_IDENTIFIER_INDEX = 8;

    public static final int PMTCT_IDENTIFIER_INDEX = 6;

    public static final int EXPOSE_INFANT_IDENTIFIER_INDEX = 7;

    public static final int PEP_ED_IDENTIFIER_INDEX = 9;

    public static final String COMMUNITY_TESTER_TABLE = "community_testers";

    public static final String PATIENT_CONTACT_TABLE = "patient_contacts";

    public static final String FACILITY_LOCATION_TABLE = "facility_has_location";

    public static final int ADMISSION_ENCOUNTER_TYPE = 2;

    //Endpoints
    //public static final String BASE_URL = "https://hts.shieldnigeriaproject.com/api";

    public static final String BASE_URL = "https://hts.shieldnigeriaproject.com/api/nmrs";

    public static final String GET_COMMUNITY_TESTER = "/GetTester";

    public static final String GET_ClIENT = "/GetClientReferal";
    private static int Previously_Tested_Hiv_Negative_Concept_Id = 165799;
    private static int Client_Pregnant_Concept_Id = 1434;
    private static int Client_Informed_About_Transsmission_Concept_Id = 165801;
    private static int Client_Informed_About_Risk_Factors_Concept_Id = 165802;
    private static int Client_Informed_On_Prevention_Concept_Id = 165804;
    private static int Client_Informed_About_Possible_Concept_Id = 165884;
    private static int Informed_Consent_For_Hiv_Testing_Concept_Id = 1710;
    //Risk assessment
    private static int Ever_Had_Sexual_Intercourse_Concept_Id = 165800;
    private static int Blood_Transfussion_In_Last_3_Months = 1063;
    private static int Unprotected_Sex_With_Casual_Partner_In_Last_3_Months = 159218;
    private static int Unprotected_Sex_With_Regular_Partner_In_Last_3_Months = 165803;
    private static int Sti_In_Last_3_Months = 164809;
    private static int More_Than_1_Sex_Partner = 165806;

    //Clinical tb screening
    private static int Current_Cough = 143264;
    private static int Weight_loss = 832;
    private static int Fever = 140238;
    private static int Night_sweats = 133027;
    //Syndronmic Sti
    private static int Complaints_of_vaginal_discharge_or_burning_when_urinating = 165809;
    private static int Complaints_of_lower_abdominal_pains_with_or_without_vaginal_discharge = 165810;
    private static int Complaints_of_urethral_discharge_or_burning_when_urinating = 165811;
    private static int Complaints_of_scrotal_swelling_and_pain = 165812;
    private static int Complaints_of_genital_sore = 165813;
    //Post test councelling
    private static int Have_you_been_tested_for_HIV_before_within_this_year = 165881;
    private static int HIV_Request_and_Result_form_signed_by_tester = 165818;
    private static int HIV_Request_and_Result_form_filled_with_CT_Intake_Form = 165819;
    private static int Client_received_HIV_test_result = 164848;
    private static int Post_test_counseling_done = 159382;
    private static int Risk_reduction_plan_developed = 165820;
    private static int Post_test_disclosure_plan_developed = 165821;
    private static int Will_bring_partner_for_HIV_testing = 165822;
    private static int Will_bring_own_children_Less_5_years_for_HIV_testing = 165823;
    private static int Provided_with_information_on_FP_and_dual_contraception = 1382;
    private static int Client_Partner_use_FP_methods = 165883;
    private static int Client_Partner_use_condoms_as_FP_method = 5571;
    private static int Correct_condom_use_demonstrated = 165824;
    private static int Condoms_provided_to_client = 159777;
    private static int Client_referred_to_other_services = 1648;

    //Client intake form has no concept ids for partner section
    //Partner details
    private static int Partner_Age = 164955;
    private static int Partner_preTest_counselled_ = 164956;
    private static int Partner_accepts_HIV_test = 164957;
    private static int Partner_HIV_test_result = 164958;
    private static int Partner_postTest_counseled = 165571;
    private static int Partner_HBV_status = 165561;
    private static int Partner_HCV_status = 165562;

    private static int Partner_referred_to = 164960;
    //Health facility Visit
    private static int Visit_Date = 1769;
    private static int Visit_Status = 166061;
    private static int Weight = 5089;
    private static int Breast_Feeding = 165876;

    //client intake fields
    public static int HTS_Client_Intake_SETTING_ConceptID = 165839;
    public static int HTS_CLient_Intake_FIRST_TIME_ConceptID = 165790;
    public static int HTS_Client_Intake_SESSION_TYPE_ConceptID = 165793;
    public static int HTS_Client_Intake_REFFERRED_FROM = 165480;
    public static int HTS_Client_Intake_MARITAL_STATUS_ConceptID = 1054;
    public static int HTS_Client_Intake_LESS_THAN_FIVE_CHILDREN = 160312;
    public static int HTS_Client_Intake_NO_OF_COWIFES_ConceptID = 5557;
    public static int HTS_Client_Intake_Client_IDENTIFIED_INDEX = 165794;
    public static int HTS_Client_Intake_INDEX_TYPE = 165798;
    public static int HTS_Client_Intake_INDEX_CLIENT_ID = 165859;
    public static int HTS_Client_Intake_CLIENT_RETESTING = 165976;
    public static int HTS_CLIENT_INTAKE_SETTINGS_OTHERS = 5622;
    public static int HTS_CLIENT_INTAKE_SETTINGS_OTHERS_VALUE = 165966;

    //HIV Testing Result
    public static int SCREENING_TEST_RESULT = 165840;
    public static int CONFIRMATORY_TEST_RESULT = 165841;
    public static int TIE_BREAKER_RESULT = 165842;
    public static int SCREENING_TEST_RESULT_DATE = 165844;
    public static int CONFIRMATORY_TEST_RESULT_date = 165845;
    public static int TIE_BREAKER_RESULT_DATE = 165846;
    public static int FINAL_RESULT = 165843;

    //Index Notification Services
    public static int PARTNER_ELICITATION_GROUPING_CONCEPT = 165858;
    public static int PARTNER_NAME_CONCEPT = 161135;
    public static int PARTNER_GENDER_CONCEPT = 165857;
    public static int PARTNER_INDEX_TYPE_CONCEPT = 165798;
    public static int PARTNER_INDEX_DESCRIPTIVE_ADDRESS_CONCEPT = 166021;
    public static int PARTNER_INDEX_RELATION_PHONE_CONCEPT = 166022;
    
    //HIV Recency Testing
    public static int HIV_RECENCY_TEST_NAME_CONCEPT = 166216;//formerly 165849
    public static int HIV_RECENCY_TEST_DATE_CONCEPT = 165850;
    public static int HIV_RECENCY_ASSAY_CONCEPT = 165853;
    public static int SAMPLE_TEST_DATE_CONCEPT = 165854;
    public static int SAMPLE_TEST_RESULT_CONCEPT = 856;
    public static int FINAL_HIV_RECENCY_INFECTION_TESTING_ALGORITHM_RESULT_CONCEPT = 165856;
    public static int OPT_OUT_OF_RTRI = 165805;
    public static int RECENCY_NUMBER = 166209;
    public static int CONTROL_LINE = 166212;
    public static int VERIFICATION_LINE = 165976;
    public static int LONG_TERM_LINE = 166211;
    public static int RECENCY_INTERPRETATION = 166213;
    public static int VIRALLOAD_REQUEST_MADE = 143264;
    
    
    

    //syphillis and hepatitis
    private static int SYPHILLIS_STATUS_RESULT = 299;
    private static int HEPATITIS_B_RESULT = 159430;
    private static int HEPATITIS_C_RESULT = 161471;
    public final static int CD4_Count_Concept_Id = 5497;
    public final static int SERUM_CREATININE_CONCEPT_ID = 164364;
    public final static int WEIGHT_CONCEPT_ID = 5089;
    public final static int WHO_STAGING_CONCEPT_ID = 5356;
    public final static int PCV_HCT_CONCEPT_ID = 1015;
    public final static int ALT_SGPT_CONCEPT_ID = 654;
    public final static int Viral_Load_CONCEPT_ID = 856;

    final static int Ordered_By_Concept_Id = 164987;

    final static int Ordered_Date_Concept_id = 164989;

    final static int Checked_By_Concept_Id = 164983;
    final static int Checked_By_Date_Concept_Id = 164984;

    final static int Reported_By_Date_Concept_Id = 1644984;
    final static int REPORTED_BY_CONCEPT_ID = 164982;
    final static int Numeric_DataType_Concept_Id = 1;
    final static int Coded_DataType_ConceptId = 2;
    final static int Text_DataType_ConceptId = 3;
    final static int Visit_Type_Concept_Id = 164181;

    // final static int Laboratory_Identifier_Concept_Id = 164409;
    final static int Laboratory_Identifier_Concept_Id = 165715;
    final static int SAMPLE_COLLECTION_DATE = 159951;
    
    final static int Date_Of_Visit_Concept_Id = 159590;
    final static int Last_Menstural_Period_Concept_Id = 1427;
    final static int Gestational_Age_At_ANC_Registration_Concept_Id = 1438;
    final static int Gravida_Concept_Id = 5624;
    final static int Parity_Concept_Id = 1053;
    final static int Source_Of_Referal_Concept_Id = 165847;
    final static int EDD_Concept_Id = 5596;
    final static int Test_For_Syphilis_Concept_Id = 165280;
    final static int Syphilis_Test_Result_Concept_Id = 299;
    final static int Treated_For_Syphilis_Concept_Id = 160733;
    final static int Reffered_Syphilis_Positive_Client_Concept_Id = 165517;
    //Delivery encounter concepts
    final static int Time_Of_Hiv_Diagnosis_Concept_Id = 164851;
    final static int Gestation_Age_At_Delivery_Concept_Id = 1409;
    final static int Hbv_Status_Concept_Id = 159430;
    final static int Hcv_Status_Concept_Id = 161471;
    final static int Woman_On_Art_Concept_Id = 165563;
    final static int Art_Started_In_Ld_Ward_Concept_Id = 165563;
    final static int Rom_Delivery_Internal_Concept_Id = 165479;
    final static int Mode_Of_Delivery_Concept_Id = 5630;
    final static int Episiotomy_Concept_Id = 5577;
    final static int Vaginal_Tear_Concept_Id = 165929;
    final static int Feeding_Decision_Concept_Id = 985;
    final static int Maternal_Outcome_Concept_Id = 160085;
    //child birth details concepts
    final static int Hiv_Exposed_Infant_Number_Concept_Id = 165668;
    final static int Date_of_Birth_Concept_Id = 164802;
    final static int Child_Sex_Concept_Id = 1587;
    final static int Hbv_Exposed_Infant_Given_Blg_Within_24_Hours_Concept_Id = 165667;
    final static int Non_Hbv_Exposed_Infant_Given_Blg_Within_24_Hours_Concept_Id = 165930;
    final static int Apgar_Score_Concept_Id = 1504;
    final static int Mean_Upper_Arm_Circumference_Concept_Id = 1343;
    final static int Birth_Length_Concept_Id = 1503;
    final static int Birth_Weight_concept_Id = 5916;
    final static int Head_Circumference_Concept_Id = 5314;
    final static int Immunization_Received_At_Birth_Concept_Id = 5314;
    final static int Infant_Arv_Type_Concept_Id = 164971;
    final static int Timimg_Of_Arv_Prophylaxis_Concept_Id = 165864;
    final static int Age_At_Ctx_Initiation_Concept_Id = 164979;
    final static int Infant_Outcome_at_18_Months_Concept_Id = 165035;
    final static int Date_Linked_to_Art_Clinic_Concept_Id = 166062;
    final static int Art_Enrollment_No_Concept_Id = 165560;
    //Immunization
    final static int Immunization_Date = 1410;
    final static int Lot_Number = 1420;

    //Infant PCR
    static final int AgeAtTest = 0;//the concept for this was not found at this time
    static final int Date_Sample_Collected = 165869;
    static final int Date_Sample_Sent = 165870;
    static final int Date_Result_Received = 165874;
    static final int Date_Caregiver_Given_Result = 165873;
    static final int Test_Result = 165872;
    static final int Date_of_Test = 165025;
    
    
 
    
    public static Map<Integer, String> map = new HashMap<>();

    

    
     public static void loadDictionary() {
      
        map.put(654, "2");//SERUM GLUTAMIC-PYRUVIC TRANSAMINASE
        map.put(653, "4");//SERUM GLUTAMIC-OXALOACETIC TRANSAMINASE
        map.put(655, "7");//TOTAL BILIRUBIN
        map.put(CD4_Count_Concept_Id, "11");//CD4 COUNT
        map.put(730, "11");//CD4%
        map.put(1319, "12");//LYMPHOCYTE COUNT
        map.put(1338, "12");//LYMPHOCYTES (%) - MICROSCOPIC EXAM
        map.put(1007, "18");//HIGH-DENSITY LIPOPROTEIN CHOLESTEROL
        map.put(1008, "19");//LOW-DENSITY LIPOPROTEIN CHOLESTEROL
        map.put(164364, "21");//Serum creatinine (mg/dL)
        map.put(160053, "31");//Glucose measurement, serum, fasting (mmol/dL)
        map.put(1015, "34");//HEMATOCRIT
        map.put(159430, "42");//Hepatitis B Surface Antigen Test
        map.put(1325, "43");//HEPATITIS C TEST - QUALITATIVE
        map.put(32, "50");//MALARIAL SMEAR "
        map.put(885, "52");//PAPANICOLAOU SMEAR "
        map.put(785, "54");//ALKALINE PHOSPHATASE
        map.put(729, "56");//Platelets
        map.put(1133, "57");//SERUM POTASSIUM
        map.put(717, "59");//TOTAL PROTEIN "
        map.put(1132, "64");//SERUM SODIUM
        map.put(299, "70");//VDRL "
        map.put(1006, "72");//TOTAL CHOLESTEROL
        map.put(1009, "74");//TRIGLYCERIDES
        map.put(856, "80");//HIV VIRAL LOAD
        map.put(678, "82");//WHITE BLOOD CELLS
        map.put(165398, "0");//Additional Lab Tests
        map.put(1025, "0");//BASOPHILS
        map.put(1341, "0");//BASOPHILS (%) - MICROSCOPIC EXAM
        map.put(1024, "0");//EOSINOPHILS
        map.put(1023, "0");//MONOCYTES
        map.put(1339, "0");//MONOCYTES (%) - MICROSCOPIC EXAM
        map.put(1022, "13");//NEUTROPHILS
        map.put(45, "58");//URINE PREGNANCY TEST
        //Map OpenMRS concepts to corresponding NDR values
         //Map OpenMRS concepts to corresponding NDR values
       
        //visit type
        map.put(160530, "R"); //repeat
        map.put(164180, "B"); //baseline
     
        map.put(48, "CellsPerMicroLiter,cell/ul");
        map.put(60, "CopiesPerMilliLiter,copies/ml");
        map.put(398, "percent,%");
        map.put(311, "MilliMolesPerLiter,mmol/L");
        map.put(25, "BillionPerLiter,10*9/L");
        map.put(257, "MicroMole,umol");
        map.put(136, "GramsPerDeciLiter,g/dL");
        map.put(93, "GramsPerDeciLiter,U/L");
        map.put(88, "48");
        map.put(315, "60");
        map.put(1153, "398");
        map.put(365, "311");
        map.put(331, "25");
        map.put(1717, "48");
        map.put(1168, "398");
        map.put(366, "311");
        map.put(1169, "48");
        map.put(1718, "398");
        map.put(367, "311");
        map.put(1716, "48");
        map.put(1170, "398");
        map.put(1531, "311");
        map.put(1719, "48");
        map.put(1156, "398");
        map.put(451, "257");
        map.put(1150, "48");
        map.put(7777906, "398");
        map.put(1528, "257");
        map.put(7777907, "398");
        map.put(228, "136");
        map.put(1529, "93");
        map.put(375, "25");
        map.put(1175, "311");
        map.put(1159, "311");
        map.put(313, "257");
        map.put(308, "93");
        map.put(309, "93");
        map.put(1176, "311");
        map.put(1530, "93");
        map.put(329, "398");
        map.put(332, "25");
        map.put(1065, "Y");
        map.put(1066, "N");
        map.put(1067, "U");
        map.put(703, "Pos");
        map.put(664, "Neg");
        map.put(1228, "R");
        map.put(1229, "NR");
        map.put(1382, "FP");
        map.put(1610, "ART");
        map.put(165815, "1");
        map.put(165816, "2");
        map.put(165817, "3");
        map.put(165882, "4");

        //setting
        map.put(160539, "1");
        map.put(160529, "2");
        map.put(160548, "3");
        map.put(5271, "4");
        map.put(160542, "5");
        map.put(161629, "6");
        map.put(165788, "7");
        map.put(165838, "9");
        map.put(5622, "8");

        //session
        map.put(165792, "1");
        map.put(165789, "2");
        map.put(165885, "3");

        //refferred from
        map.put(5622, "8");

        //marital status
        map.put(1057, "S");
        map.put(5555, "M");
        map.put(1058, "D");
        map.put(1056, "A");
        map.put(1060, "G");
        map.put(1059, "W");

        //index type
        map.put(165796, "1");
        map.put(165797, "2");
        map.put(165795, "3");

        //recency
        map.put(165852, "R");
        map.put(165851, "L");
        map.put(166215, "AS");
        map.put(5622, "OTH");
        map.put(165853, "L");
        map.put(165855, "Neg");
        map.put(165854, "Inv");
        
        
        //gender
        map.put(165184, "M");
        map.put(165185, "F");
        map.put(123, "12");
        //adherence
        map.put(165289, "P");
        map.put(165287, "G");
        map.put(165288, "F");
        
        
        //encounter type WHO clinical stage concept

        map.put(1204, "1");
        map.put(1205, "2");
        map.put(1206, "3");
        map.put(1207, "4");

        //pediatric
        map.put(1220, "I");
        map.put(1221, "II");
        map.put(1222, "III");
        map.put(1223, "IV");

        //Family planning
        map.put(190, "FP1");
        map.put(780, "FP2");
        map.put(5279, "FP3");
        map.put(5278, "FP4");
        map.put(5275, "FP5");
        map.put(1489, "FP6");

        //TB Status
        map.put(1660, "1");
        map.put(142177, "2");
        map.put(1662, "3");
        map.put(1661, "5");
        map.put(1663, "4");

        //OTHER OI
        map.put(117543, "1");
        map.put(114100, "2");
        map.put(119566, "3");
        map.put(5334, "4");
        map.put(140238, "5");
        map.put(143264, "6");

        //Noted_Side_Effects_Concept_Id
        map.put(133473, "1");
        map.put(139084, "2");
        map.put(119566, "3");
        map.put(116743,"3");
        map.put(5226, "4");
        map.put(165767,"5");
        map.put(512, "6");
        map.put(165052, "7");
        map.put(121629, "8");
        map.put(165053, "9");
        map.put(125886, "10");
        map.put(138291, "11");

        //functional status
        map.put(159468, "W");
        map.put(160026, "A");
        map.put(162752, "B");

        //ARV Drug Adherence
        map.put(165287, "G");
        map.put(165288, "P");
        map.put(165289, "F");
        map.put(160124, "1a");//"AZT-3TC-EFV"
        map.put(1652, "1b");//"AZT-3TC-NVP"
        map.put(104565, "1c");//"TDF-FTC-EFV"
        map.put(164854, "1d");//"TDF-FTC-NVP"
        map.put(164505, "1e"); //"TDF-3TC-EFV"
        map.put(162565, "1f");//"TDF-3TC-NVP"
        map.put(817, "1g"); //"AZT-3TC-ABC" same as ABC/3TC/AZT
        map.put(165522, "1h"); //"AZT-3TC-TDF” same as TDF-3TC-AZT
        map.put(162563, "1l"); //"ABC-3TC-EFV"
        map.put(165681, "1m"); //"TDF-3TC-DTG"
        map.put(165686, "1n"); //"TDF-3TC-EFV400"
        map.put(165682, "1o"); //"TDF-FTC-DTG"
        map.put(165687, "1p"); //"TDF-FTC-EFV400"
        map.put(165523, "2a"); //"TDF-FTC-LPV/r"
        map.put(162201, "2b");//"TDF-3TC-LPV/r"
        map.put(165524, "2c"); //"TDF-FTC-ATV/r"
        map.put(164512, "2d");//"TDF-3TC-ATV/r"
        map.put(162561, "2e");//"AZT-3TC-LPV/r"
        map.put(164511, "2f");//"AZT-3TC-ATV/r"
        map.put(165530, "2h");//"AZT-TDF-3TC-LPV/r"
        map.put(165537, "2i");//"TDF-AZT-3TC-ATV/r"
        map.put(165688, "3a ");//"DRV/r-DTG + 1-2 NRTIs"
        map.put(160124, "4a");//"AZT-3TC-EFV"
        map.put(1652, "4b");//"AZT-3TC-NVP"
        map.put(162563, "4c");//"ABC-3TC-EFV"
        map.put(162199, "4d");//"ABC-3TC-NVP"
        map.put(817, "4e");//"AZT-3TC-ABC" Same as ABC-3TC-AZT
        map.put(792, "4f");//"d4T-3TC-NVP"
        map.put(166074, "4g"); // Nelson Added Concept in NigeriaMRS and mapped it here as code already exist on NDR.
        map.put(165691, "4h"); //ABC-3TC-DTG
        map.put(165693, "4i"); //ABC-3TC-EFV400
        map.put(162200, "4j"); //ABC-3TC-LPV/r
        map.put(165692, "4k"); //ABC-FTC-DTG
        map.put(165694, "4l"); //ABC-FTC-EFV400
        map.put(165690, "4m"); //ABC-FTC-NVP
        map.put(162561, "4n"); //AZT-3TC-LPV/r
        map.put(165695, "4o");//AZT-3TC-RAL
        map.put(165681, "4p"); //TDF-3TC-DTG
        map.put(164505, "4q"); //TDF-3TC-EFV// Add PrescribedRegimenCode and Code Description in NDR
        map.put(165686, "4r"); //TDF-3TC-EFV400
        map.put(162565, "4s"); // TDF-3TC-NVP
        map.put(165682, "4t"); // TDF-FTC-DTG
        map.put(104565, "4u"); //TDF-FTC-EFV
        map.put(165687, "4v"); // TDF-FTC-EFV400
        map.put(164854, "4w");// TDF-FTC-NVP
        map.put(162200, "5a");;//"ABC-3TC-LPV/r"
        map.put(162561, "5b");;//"AZT-3TC-LPV/r"
        map.put(162560, "5c");;//"d4T-3TC-LPV/r"
        map.put(165526, "5e");;//"ABC-3TC-ddi"
        map.put(165696, "5g");//ABC-3TC-RAL
        map.put(164511, "5h"); // AZT-3TC-ATV/r
        map.put(165695, "5i");  //AZT-3TC-RAL
        map.put(164512, "5j"); //TDF-3TC-ATV/r
        map.put(162201, "5k");//TDF-3TC-LPV/r
        map.put(165698, "6a"); //DRV/r + 2 NRTIs + 2 NNRTI
        map.put(165700, "6b"); //DRV/r +2NRTIs
        map.put(165688, "6c"); //DRV/r-DTG + 1-2 NRTIs
        map.put(165701, "6d"); //DRV/r-RAL + 1-2NRTIs
        map.put(165697, "6e"); //DTG+2 NRTIs
        map.put(165699, "6f"); //RAL + 2 NRTIs
        map.put(86663, "9a");//"AZT" Concept ID didnt match. So, Changed concept id from 26 to 86663 as defined In NMRS
        map.put(78643, "9b");//3TC Concept ID didnt match. So, changed ID from 27 to 78643 as defined In NMRS
        map.put(80586, "9c");//"NVP" Concept ID didnt match. So, Changed concept id from 28 to 80586 as defined in NMRS
        map.put(630, "9d");//"AZT-3TC" Concept ID didnt match. So, Changed concept id from 29 to 630 as defined on NMRS
        map.put(165544, "9e");//"AZT-NVP" Concept ID didnt match. So, Changed concept id from 30 to 165544 as defined in NMRS
        map.put(104567, "9f");//"FTC-TDF" Concept ID didnt match. So, Changed concept id from 31 to 104567 as defined in NMRS
        map.put(161363, "9g");//"3TC-d4T"  Concept ID didnt match. So, Changed concept id from 32 to 104567 as defined in NMRS
        map.put(166075, "9h"); //"3TC-d4T" Changed the code desc from 3TC-4DT to 3TC-NVP and Created new concept for it on NMRS and replaced the initial Concpet Id of 33 to 166075
        map.put(161364, "Unknown NDR Code APINSs Instance");//TDF/3TC Missing Drug Combination without NDR Code
        map.put(165631, "Missing NDR Code from IHVN Instance"); //Dolutegravir
        map.put(1674, "Missing NDR Code frm IHVN Instance");//RIFAMPICIN/ISONIAZID/PYRAZINAMIDE/ETHAMBUTOL PROPHYLAXIS
        map.put(165048, "P"); //Pregnant
        map.put(165047, "NP");
        //map.put(128, "NK");
        map.put(165049, "PMTCT");

        //EDUCATIONAL_LEVEL MAPPING
        map.put(1107, "1");
        map.put(1713, "2");
        map.put(1714, "3");
        map.put(160292, "6");

        /* OCCUPATIONAL CODE */
        map.put(123801, "UNE");
        map.put(1540, "EMP");
        map.put(159465, "STU");
        map.put(159461, "RET");
        map.put(1175, "NA");
        map.put(1067, "UNK");

        //MARITAL STATUS CODE
        map.put(1057, "S");
        map.put(5555, "M");
        map.put(1058, "D");
        map.put(1056, "A");
        map.put(1059, "W");

        //FUNCTIONAL STATUS
        map.put(159468, "W");
        map.put(162752, "B");
        map.put(160026, "A");

        //WHO STAGING
        map.put(1204, "1");
        map.put(1205, "2");
        map.put(1206, "3");
        map.put(1207, "4");

        // TB Status
        map.put(1660, "1");
        map.put(142177, "2");
        map.put(166042, "3");
        map.put(1661, "5");
        map.put(1662, "4");

        //care entry point
        map.put(160539, "3"); //HTS
        map.put(160538, "9"); //ANC/PMTCT
        map.put(160536, "12"); //In Patient
        map.put(160537, "12");//Current Clinic Patient
        map.put(160542, "2");//OPD
        map.put(160541, "6");//TB DOTS
        map.put(160543, "4");//CBO
        map.put(160545, "4");//Outreaches
        map.put(160546, "1"); //STI
        //  map.put(5622, ""); //Other

        //Mode of HIV Test
        map.put(164949, "HIVAb");
        map.put(164948, "HIVPCR");

        //Prior ART Exposure
        map.put(1107, "N");
        map.put(165241, "E");
        map.put(165240, "P");
        map.put(165238, "T");
        map.put(165239, "T");
        //Reason Medically Eligible
        map.put(164426, "1");
        map.put(5497, "2");
        map.put(730, "3");
        map.put(164427, "4");
        map.put(703, "Pos");
        map.put(664, "Neg");
        map.put(1065, "Y");
        map.put(1066, "N");
        map.put(165478, "1");
        map.put(165477, "2");
        map.put(5630, "Unbooked");
        map.put(5526, "EBF");
        map.put(164857, "ERF");
        map.put(160429, "Alive");
        map.put(164970, "1");
        map.put(165860, "Within 72 hrs of facility delivery");
        map.put(1228, "Pos");
        map.put(1229, "Neg");
        map.put(134612, "Dead");
        map.put(1170, "1");
        map.put(1171, "2");
        map.put(159739, "3");
        map.put(165544, "2");
        map.put(1107, "3");
        map.put(165863, "1");
        map.put(165862, "2");
        map.put(165861, "3");
         map.put(160124, "1a");//"AZT-3TC-EFV"
        map.put(1652, "1b");//"AZT-3TC-NVP"
        map.put(104565, "1c");//"TDF-FTC-EFV"
        map.put(164854, "1d");//"TDF-FTC-NVP"
        map.put(164505, "1e"); //"TDF-3TC-EFV"
        map.put(162565, "1f");//"TDF-3TC-NVP"
        map.put(817, "1g"); //"AZT-3TC-ABC" same as ABC/3TC/AZT
        map.put(165522, "1h"); //"AZT-3TC-TDF” same as TDF-3TC-AZT
        map.put(162563, "1l"); //"ABC-3TC-EFV"
        map.put(165681, "1m"); //"TDF-3TC-DTG"
        map.put(165686, "1n"); //"TDF-3TC-EFV400"
        map.put(165682, "1o"); //"TDF-FTC-DTG"
        map.put(165687, "1p"); //"TDF-FTC-EFV400"
        map.put(165523, "2a"); //"TDF-FTC-LPV/r"
        map.put(162201, "2b");//"TDF-3TC-LPV/r"
        map.put(165524, "2c"); //"TDF-FTC-ATV/r"
        map.put(164512, "2d");//"TDF-3TC-ATV/r"
        map.put(162561, "2e");//"AZT-3TC-LPV/r"
        map.put(164511, "2f");//"AZT-3TC-ATV/r"
        map.put(165530, "2h");//"AZT-TDF-3TC-LPV/r"
        map.put(165537, "2i");//"TDF-AZT-3TC-ATV/r"
        map.put(165688, "3a ");//"DRV/r-DTG + 1-2 NRTIs"
        map.put(160124, "4a");//"AZT-3TC-EFV"
        map.put(1652, "4b");//"AZT-3TC-NVP"
        map.put(162563, "4c");//"ABC-3TC-EFV"
        map.put(162199, "4d");//"ABC-3TC-NVP"
        map.put(817, "4e");//"AZT-3TC-ABC" Same as ABC-3TC-AZT
        map.put(792, "4f");//"d4T-3TC-NVP"
        map.put(166074, "4g"); // Nelson Added Concept in NigeriaMRS and mapped it here as code already exist on NDR.
        map.put(165691, "4h"); //ABC-3TC-DTG
        map.put(165693, "4i"); //ABC-3TC-EFV400
        map.put(162200, "4j"); //ABC-3TC-LPV/r
        map.put(165692, "4k"); //ABC-FTC-DTG
        map.put(165694, "4l"); //ABC-FTC-EFV400
        map.put(165690, "4m"); //ABC-FTC-NVP
        map.put(162561, "4n"); //AZT-3TC-LPV/r
        map.put(165695, "4o");//AZT-3TC-RAL
        map.put(165681, "4p"); //TDF-3TC-DTG
        map.put(164505, "4q"); //TDF-3TC-EFV// Add PrescribedRegimenCode and Code Description in NDR
        map.put(165686, "4r"); //TDF-3TC-EFV400
        map.put(162565, "4s"); // TDF-3TC-NVP
        map.put(165682, "4t"); // TDF-FTC-DTG
        map.put(104565, "4u"); //TDF-FTC-EFV
        map.put(165687, "4v"); // TDF-FTC-EFV400
        map.put(164854, "4w");// TDF-FTC-NVP
        map.put(162200, "5a");;//"ABC-3TC-LPV/r"
        map.put(162561, "5b");;//"AZT-3TC-LPV/r"
        map.put(162560, "5c");;//"d4T-3TC-LPV/r"
        map.put(165526, "5e");;//"ABC-3TC-ddi"
        map.put(165696, "5g");//ABC-3TC-RAL
        map.put(164511, "5h"); // AZT-3TC-ATV/r
        map.put(165695, "5i");  //AZT-3TC-RAL
        map.put(164512, "5j"); //TDF-3TC-ATV/r
        map.put(162201, "5k");//TDF-3TC-LPV/r
        map.put(165698, "6a"); //DRV/r + 2 NRTIs + 2 NNRTI
        map.put(165700, "6b"); //DRV/r +2NRTIs
        map.put(165688, "6c"); //DRV/r-DTG + 1-2 NRTIs
        map.put(165701, "6d"); //DRV/r-RAL + 1-2NRTIs
        map.put(165697, "6e"); //DTG+2 NRTIs
        map.put(165699, "6f"); //RAL + 2 NRTIs
        map.put(86663, "9a");//"AZT" Concept ID didnt match. So, Changed concept id from 26 to 86663 as defined In NMRS
        map.put(78643, "9b");//3TC Concept ID didnt match. So, changed ID from 27 to 78643 as defined In NMRS
        map.put(80586, "9c");//"NVP" Concept ID didnt match. So, Changed concept id from 28 to 80586 as defined in NMRS
        map.put(630, "9d");//"AZT-3TC" Concept ID didnt match. So, Changed concept id from 29 to 630 as defined on NMRS
        map.put(165544, "9e");//"AZT-NVP" Concept ID didnt match. So, Changed concept id from 30 to 165544 as defined in NMRS
        map.put(104567, "9f");//"FTC-TDF" Concept ID didnt match. So, Changed concept id from 31 to 104567 as defined in NMRS
        map.put(161363, "9g");//"3TC-d4T"  Concept ID didnt match. So, Changed concept id from 32 to 104567 as defined in NMRS
        map.put(166075, "9h"); //"3TC-d4T" Changed the code desc from 3TC-4DT to 3TC-NVP and Created new concept for it on NMRS and replaced the initial Concpet Id of 33 to 166075
        map.put(161364, "Unknown NDR Code APINSs Instance");//TDF/3TC Missing Drug Combination without NDR Code
        map.put(165631, "Missing NDR Code from IHVN Instance"); //Dolutegravir
        map.put(1674, "Missing NDR Code frm IHVN Instance");//RIFAMPICIN/ISONIAZID/PYRAZINAMIDE/ETHAMBUTOL PROPHYLAXIS


        //Added By Nelson
        map.put(165257, "CTX480");//
        map.put(76488, "FLUC");//Added By Nelson
        map.put(1679, "H");
        map.put(80945, "CTX960");

        /* Added by Bright Ibezim */
        map.put(164506, "10");
        map.put(164513, "20");
        map.put(165702, "30");
        map.put(164507, "10");
        map.put(164514, "20");
        map.put(164703, "30");
        /* Added by Bright Ibezim Reason for substitusion and switch */
        map.put(102, "1");
        map.put(165048, "2");
        map.put(160559, "3");
        map.put(160567, "4");
        map.put(160561, "5");
        map.put(159834, "6");
        map.put(163523, "7");
        map.put(160566, "8");
        map.put(160569, "9");

        //key is concept id, value is NDR code text
        map.put(160124, "AZT-3TC-EFV");
        map.put(1652, "AZT-3TC-NVP");
        map.put(104565, "DF-FTC-EFV");
        map.put(164854, "TDF-FTC-NVP");
        map.put(164505, "TDF-3TC-EFV");
        map.put(162565, "TDF-3TC-NVP");
        map.put(817, "AZT-3TC-ABC");
        map.put(165522, "AZT-3TC-TDF");
        map.put(162563, "ABC-3TC-EFV");
        map.put(165681, "TDF-3TC-DTG");
        map.put(165686, "TDF-3TC-EFV400");
        map.put(165682, "TDF-FTC-DTG");
        map.put(165687, "TDF-FTC-EFV400");
        map.put(165523, "TDF-FTC-LPV/r");
        map.put(162201, "TDF-3TC-LPV/r");
        map.put(165524, "TDF-FTC-ATV/r");
        map.put(164512, "TDF-3TC-ATV/r");
        map.put(162561, "AZT-3TC-LPV/r");
        map.put(164511, "AZT-3TC-ATV/r");
        map.put(165530, "AZT-TDF-3TC-LPV/r");
        map.put(165537, "TDF-AZT-3TC-ATV/r");
        map.put(165688, "DRV/r-DTG + 1-2 NRTIs");
        map.put(160124, "AZT-3TC-EFV");
        map.put(1652, "AZT-3TC-NVP");
        map.put(162563, "ABC-3TC-EFV");
        map.put(162199, "ABC-3TC-NVP");
        map.put(817, "AZT-3TC-ABC");
        map.put(792, "d4T-3TC-NVP");
        map.put(165691, "ABC-3TC-DTG");
        map.put(165693, "ABC-3TC-EFV400");
        map.put(162200, "ABC-3TC-LPV/r");
        map.put(165692, "ABC-FTC-DTG");
        map.put(165694, "ABC-FTC-EFV400");
        map.put(165690, "ABC-FTC-NVP");
        map.put(162561, "AZT-3TC-LPV/r");
        map.put(165695, "AZT-3TC-RAL");
        map.put(165681, "TDF-3TC-DTG");
        map.put(164505, "TDF-3TC-EFV");
        map.put(165686, "TDF-3TC-EFV400");
        map.put(162565, "TDF-3TC-NVP");
        map.put(165682, "TDF-FTC-DTG");
        map.put(104565, "TDF-FTC-EFV");
        map.put(165687, "TDF-FTC-EFV400");
        map.put(164854, "TDF-FTC-NVP");
        map.put(162200, "ABC-3TC-LPV/r");
        map.put(162561, "AZT-3TC-LPV/r");
        map.put(162560, "d4T-3TC-LPV/r");
        map.put(165526, "ABC-3TC-ddi");
        map.put(165696, "ABC-3TC-RAL");
        map.put(164511, "AZT-3TC-ATV/r");
        map.put(165695, "AZT-3TC-RAL");
        map.put(164512, "TDF-3TC-ATV/r");
        map.put(162201, "TDF-3TC-LPV/r");
        map.put(165698, "DRV/r + 2 NRTIs + 2 NNRTI");
        map.put(165700, "DRV/r +2NRTIs");
        map.put(165688, "DRV/r-DTG + 1-2 NRTIs");
        map.put(165701, "DRV/r-RAL + 1-2 NRTIs");
        map.put(165697, "DTG+2 NRTIs");
        map.put(165699, "RAL + 2 NRTIs");
        map.put(86663, "AZT");//"AZT" Concept ID didnt match. So, Changed concept id from 26 to 86663 as defined in NMRS
        map.put(78643, "3TC");// 3TC Concept ID didnt match . So , Changed concept Id from 27 to 78643 as defined in NMRS
        map.put(80586, "NVP");//"NVP" Concept ID didnt match. So, Changed concept id from 28 to 80586 as defined in NMRS
        map.put(630, "AZT-3TC");//"AZT-3TC" Concept ID didnt match. So, Changed concept id from 29 to 630 as defined in NMRS
        map.put(165544, "AZT-NVP");//"AZT-NVP" Concept ID didnt match. So, Changed concept id from 30 to 165544 as defined in NMRS
        map.put(104567, "FTC-TDF");//"FTC-TDF" Concept ID didnt match. So, Changed concept id from 31 to 104567 as defined in NMRS
        map.put(161363, "3TC-D4T");//"3TC-d4T"  Concept ID didnt match. So, Changed concept id from 32 to 104567 as defined n NMRS
        map.put(166075, "3TC-NVP"); //"3TC-d4T" Changed the code desc from 3TC-4DT to 3TC-NVP and Created new concept for it on NMRS and replaced the initial Concpet Id of 33 to 166075
        map.put(165257, "Cotrimoxazole 480mg"); //Defined Concept name is CTX prophylaxis. Check with Dr. Sunday for clearification
        map.put(76488, "FLUCONAZOLE");//Added By Nelson
        map.put(1679, "Isoniazid");//Added By Nelson
        map.put(80945, "Nystatin");//Added By Nelson
        map.put(161364, "TDF/3TC"); //Missing NDR Code lamivudine/fenofovir from APINS Instance
        map.put(165631, "Dolutegravir");// Missing NDR Code from IHVN Instance
        map.put(1674, "RIFAMPICIN/ISONIAZID/PYRAZINAMIDE/ETHAMBUTOL PROPHYLAXIS");// Missing NDR Code from IHVN Instance

        //Added By Nelson Attah
        //OI drug
        map.put(71160, "CTX960");//71160 "C00"); //Cotrimoxazole 800mg 105281 No NDR Code
        /*map.put("Cotrimoxotrimoxazole 960mg"
        map.put("Cotrimoxazole 800mg", "CTX8azole 480mg", "CTX480");
        map.put("Cotrimoxazole 400mg", "CTX400");
        map.put("Cotrimoxazole 240mg/5ml", "CTX240");
        map.put("Fluconazole", "FLUC");
        map.put("Dapsone", "DDS");
        map.put("Isoniazid-Pyridoxine", "INHB6");*/


    }
	
}
