package com.fincare.mel_los.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import com.fincare.mel_los.data.Branch;
import com.fincare.mel_los.data.BussinessType;
import com.fincare.mel_los.data.KYCType;
import com.fincare.mel_los.data.LeadGeneration;
import com.fincare.mel_los.data.LeadType;
import com.fincare.mel_los.data.LoanPurpose;
import com.fincare.mel_los.data.LoginData;
import com.fincare.mel_los.data.Member;
import com.fincare.mel_los.data.PropertyType;
import com.fincare.mel_los.data.SourceLead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Phaneendra on 02-Aug-16.
 */
@SuppressLint("DefaultLocale")
public class DbHelper_Old extends SQLiteOpenHelper {

    public DbHelper_Old(Context context){
        super(context,"Mellos.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists User_Info (mfi_emp_name text,mfi_emp_user_name text,mfi_emp_user_pass text,mfi_emp_active text,route_access text,main_screen_access text,sub_l1_screen_access text)");
        db.execSQL("create table if not exists branches (id int,branch_name text,division_name text,zone_name text)");
        db.execSQL("create table if not exists id_type (id int,id_name text)");
        db.execSQL("create table if not exists property (id int,name text)");
        db.execSQL("create table if not exists bussiness_type (id int,name text)");
        db.execSQL("create table if not exists loan_purpose (id int,name text)");
        db.execSQL("create table if not exists source_leads (id int,name text)");
        db.execSQL("create table if not exists lead_type (id int,name text)");


        db.execSQL("create table if not exists LeadGen (id INTEGER PRIMARY KEY NOT NULL,borrower_name text, mobile text, rec_address text, prop_address text, kyc_type text, kyc_id text, property_type text, property_localtion text, business_type text, business_discription text, business_address text, loan_purpose text, source_of_lead text, lead_type text, edd text, next_followup text, loan_amt text,uploaded text,inserted_at text,uniqno text)");
        db.execSQL("create table if not exists MemberInfo (zone_name text, division_name text, branch_name text, Unique_Lead_No text, c_first_name text, c_dob text, c_contact_mobile text, c_pancard text, c_voter_id text, c_aadhar_card text, g_first_name text, g_dob text, g_contact_mobile text, g_pancard text, g_voter_id text, g_aadhar_card text, tenure text, roi text, applied_amt text, rec_amt text, cibil_score text, score text, ltv text, foir text, pdcam_remarks text, dcm_remarks text, zcm_remarks text, ncm_remarks text, coo_remarks text, added_at text, product text, loan_purpose text, cm_amt text, dcm_amt text, zcm_amt text, ncm_amt text, cro_amt text, dcm_done_at text, dcm_done_by text, dcm_qry text, dcm_res_msg text, dcm_status text, zcm_done_at text, zcm_done_by text, zcm_qry text, zcm_res_msg text, zcm_sts text, ncm_done_at text, ncm_done_by text, ncm_qry text, ncm_res_msg text, ncm_msg text, coo_done_at text, coo_done_by text, coo_qry text, coo_res_msg text, coo_sts text, main_sts text, property_photo_file_name text, residency_photo_file_name text, collateral_photo_file_name text,uploaded text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS User_Info");
        db.execSQL("DROP TABLE IF EXISTS branches");
        db.execSQL("DROP TABLE IF EXISTS id_type");

        db.execSQL("DROP TABLE IF EXISTS property");
        db.execSQL("DROP TABLE IF EXISTS bussiness_type");
        db.execSQL("DROP TABLE IF EXISTS loan_purpose");
        db.execSQL("DROP TABLE IF EXISTS source_leads");
        db.execSQL("DROP TABLE IF EXISTS lead_type");
        db.execSQL("DROP TABLE IF EXISTS LeadGen");
        db.execSQL("DROP TABLE IF EXISTS MemberInfo");

        db.execSQL("create table if not exists User_Info (mfi_emp_name text,mfi_emp_user_name text,mfi_emp_user_pass text,mfi_emp_active text,route_access text,main_screen_access text,sub_l1_screen_access text)");
        db.execSQL("create table if not exists branches (id int,branch_name text,division_name text,zone_name text)");
        db.execSQL("create table if not exists id_type (id int,id_name text)");

        db.execSQL("create table if not exists property (id int,name text)");
        db.execSQL("create table if not exists bussiness_type (id int,name text)");
        db.execSQL("create table if not exists loan_purpose (id int,name text)");
        db.execSQL("create table if not exists source_leads (id int,name text)");
        db.execSQL("create table if not exists lead_type (id int,name text)");

        db.execSQL("create table if not exists LeadGen (id INTEGER PRIMARY KEY NOT NULL,borrower_name text, mobile text, rec_address text, prop_address text, kyc_type text, kyc_id text, property_type text, property_localtion text, business_type text, business_discription text, business_address text, loan_purpose text, source_of_lead text, lead_type text, edd text, next_followup text, loan_amt text,uploaded text,inserted_at text,uniqno text)");
        db.execSQL("create table if not exists MemberInfo (zone_name text, division_name text, branch_name text, Unique_Lead_No text, c_first_name text, c_dob text, c_contact_mobile text, c_pancard text, c_voter_id text, c_aadhar_card text, g_first_name text, g_dob text, g_contact_mobile text, g_pancard text, g_voter_id text, g_aadhar_card text, tenure text, roi text, applied_amt text, rec_amt text, cibil_score text, score text, ltv text, foir text, pdcam_remarks text, dcm_remarks text, zcm_remarks text, ncm_remarks text, coo_remarks text, added_at text, product text, loan_purpose text, cm_amt text, dcm_amt text, zcm_amt text, ncm_amt text, cro_amt text, dcm_done_at text, dcm_done_by text, dcm_qry text, dcm_res_msg text, dcm_status text, zcm_done_at text, zcm_done_by text, zcm_qry text, zcm_res_msg text, zcm_sts text, ncm_done_at text, ncm_done_by text, ncm_qry text, ncm_res_msg text, ncm_msg text, coo_done_at text, coo_done_by text, coo_qry text, coo_res_msg text, coo_sts text, main_sts text, property_photo_file_name text, residency_photo_file_name text, collateral_photo_file_name text)");
    }

    //----------------- login ------------------------
    public boolean truncatelogin()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from User_Info ");
        return true;
    }
    public String getuser()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT mfi_emp_user_name FROM User_Info";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    user=cursor.getString(0).toString().trim();
                }while (cursor.moveToNext());
            }
        }
        return user;
    }
    public boolean insertlogin(LoginData l)
    {
        truncatelogin();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("mfi_emp_name", l.getMfi_emp_name());
        contentValues.put("mfi_emp_user_name", l.getMfi_emp_user_name());
        contentValues.put("mfi_emp_user_pass", l.getMfi_emp_user_pass());
        contentValues.put("mfi_emp_active", l.getMfi_emp_active());
        contentValues.put("route_access", l.getRoute_access());
        contentValues.put("main_screen_access", l.getMain_screen_access());
        contentValues.put("sub_l1_screen_access", l.getSub_l1_screen_access());

        long rows = db.insert("User_Info", null, contentValues);

        System.out.println("inserted properly :"+rows);
        return true;
    }
    public boolean checklogin(String uname,String Pass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT mfi_emp_user_pass,mfi_emp_user_name FROM User_Info where mfi_emp_user_name='"+uname+"'";

        boolean c=true;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor!=null)
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst())
            {
                do
                {
                    if(Pass.toString().trim().equals(cursor.getString(0).toString()) && uname.toString().equals(cursor.getString(1).toString()))
                    {
                        c=true;

                    }
                    else
                    {
                        c=false;
                    }

                } while (cursor.moveToNext());
            }
            else
            {
                c=false;
            }
        }
        else {
            c = false;
        }
        return c;
    }

    //------------- branch --------------------
    public boolean truncatebranch()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from branches");
        return true;
    }
    public boolean insertbranch(Branch b)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", b.getId());
        contentValues.put("branch_name", b.getBnam());
        contentValues.put("division_name", b.getDnam());
        contentValues.put("zone_name", b.getZnam());

        long rows = db.insert("branches", null, contentValues);

        System.out.println("inserted properly :"+rows);
        return true;
    }

    public List<String> getBranchList()
    {
        List<String> list=new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT branch_name FROM branches";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(0).toString().trim());
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    public String getBranchId(String name)
    {
        String list="";
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT id FROM branches where branch_name= '"+name+"' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list=String.valueOf(cursor.getInt(0));
                }while (cursor.moveToNext());
            }
        }
        return list;
    }

    //------------- KYC Type --------------------
    public boolean truncatekyctype()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from id_type");
        return true;
    }
    public boolean insertkyc(KYCType k)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", k.getId());
        contentValues.put("id_name", k.getId_name());

        long rows = db.insert("id_type", null, contentValues);

        System.out.println("inserted properly :"+rows);
        return true;
    }
    public List<String> getKycList()
    {
        List<String> list=new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT id_name FROM id_type";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(0).toString().trim());
                }while (cursor.moveToNext());
            }
        }
        return list;
    }

    public String getkyctypeId(String name)
    {
        String list="";
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT id FROM id_type where id_name= '"+name+"' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list=String.valueOf(cursor.getInt(0));
                }while (cursor.moveToNext());
            }
        }
        return list;
    }

    //------------- property  Type --------------------
    public boolean truncatepropertytype()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from property");
        return true;
    }
    public boolean insertproperty(PropertyType k)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", k.getId());
        contentValues.put("name", k.getName());

        long rows = db.insert("property", null, contentValues);

        System.out.println("inserted properly :"+rows);
        return true;
    }
    public List<String> getPropertyList()
    {
        List<String> list=new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT name FROM property";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(0).toString().trim());
                }while (cursor.moveToNext());
            }
        }
        return list;
    }

    public String getpropoertyTypeId(String name)
    {
        String list="";
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT id FROM property where name= '"+name+"' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list=String.valueOf(cursor.getInt(0));
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    //------------- Business  Type --------------------
    public boolean truncatebusinesstype()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from bussiness_type");
        return true;
    }
    public boolean insertbusinessType(BussinessType k)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", k.getId());
        contentValues.put("name", k.getName());

        long rows = db.insert("bussiness_type", null, contentValues);

        System.out.println("inserted properly :"+rows);
        return true;
    }
    public List<String> getBusinessTypeList()
    {
        List<String> list=new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT name FROM bussiness_type";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(0).toString().trim());
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    public String getBusinessTypeId(String name)
    {
        String list="";
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT id FROM bussiness_type where name= '"+name+"' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list=String.valueOf(cursor.getInt(0));
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    //------------- Loan Purpose --------------------
    public boolean truncateloan_purpose()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from loan_purpose");
        return true;
    }
    public boolean insertloan_purpose(LoanPurpose k)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", k.getId());
        contentValues.put("name", k.getName());

        long rows = db.insert("loan_purpose", null, contentValues);

        System.out.println("inserted properly :"+rows);
        return true;
    }
    public List<String> getLoanPurposeList()
    {
        List<String> list=new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT name FROM loan_purpose";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(0).toString().trim());
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    public String getloan_purposeId(String name)
    {
        String list="";
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT id FROM loan_purpose where name= '"+name+"' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list=String.valueOf(cursor.getInt(0));
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    //------------- source_leads --------------------
    public boolean truncatesource_leads()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from source_leads");
        return true;
    }
    public boolean insertsource_leads(SourceLead k)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", k.getId());
        contentValues.put("name", k.getName());

        long rows = db.insert("source_leads", null, contentValues);

        System.out.println("inserted properly :"+rows);
        return true;
    }
    public List<String> getsource_leadsList()
    {
        List<String> list=new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT name FROM source_leads";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(0).toString().trim());
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    public String getsource_leadsId(String name)
    {
        String list="";
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT id FROM source_leads where name= '"+name+"' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list=String.valueOf(cursor.getInt(0));
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    //------------- lead_type --------------------
    public boolean truncatelead_type()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from lead_type");
        return true;
    }
    public boolean insertlead_type(LeadType k)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", k.getId());
        contentValues.put("name", k.getName());

        long rows = db.insert("lead_type", null, contentValues);

        System.out.println("inserted properly :"+rows);
        return true;
    }
    public List<String> getlead_typeList()
    {
        List<String> list=new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT name FROM lead_type";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list.add(cursor.getString(0).toString().trim());
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    public String getlead_typeId(String name)
    {
        String list="";
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT id FROM lead_type where name= '"+name+"' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String user="";
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    list=String.valueOf(cursor.getInt(0));
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
    //-------------lead Generation-------------------------

    public boolean ins_lead_gen(LeadGeneration l)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("borrower_name", l.getBorrower_name());
        contentValues.put("mobile", l.getMobile());
        contentValues.put("rec_address", l.getRec_address());
        contentValues.put("prop_address", l.getProp_address());
        contentValues.put("kyc_type", l.getKyc_type());
        contentValues.put("kyc_id", l.getKyc_id());
        contentValues.put("property_type", l.getProperty_type());
        contentValues.put("property_localtion", l.getProperty_localtion());
        contentValues.put("business_type", l.getBusiness_type());
        contentValues.put("business_discription", l.getBusiness_discription());
        contentValues.put("business_address", l.getBusiness_address());
        contentValues.put("loan_purpose", l.getLoan_purpose());
        contentValues.put("source_of_lead", l.getSource_of_lead());
        contentValues.put("lead_type", l.getLead_type());
        contentValues.put("edd", l.getEdd());
        contentValues.put("next_followup", l.getNext_followup());
        contentValues.put("loan_amt", l.getLoan_amt());
        contentValues.put("uploaded","Pending");
        Date d=new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        contentValues.put("inserted_at",format.format(d).toString());
        String pattern1 = "yyyyMMddHHmmss";
        SimpleDateFormat format1 = new SimpleDateFormat(pattern1);
        contentValues.put("uniqno",format1.format(d).toString());

        long rows = db.insert("LeadGen", null, contentValues);

        System.out.println("inserted properly :"+rows);
        return true;
    }

    //--------------------memer --------------------------------------


    public boolean ins_member(Member m,String sts) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uploaded",sts);
        contentValues.put("zone_name",m.getZone_name());
        contentValues.put("division_name",m.getDivision_name());
        contentValues.put("branch_name",m.getBranch_name());
        contentValues.put("Unique_Lead_No",m.getUnique_Lead_No());
        contentValues.put("c_first_name",m.getC_first_name());
        contentValues.put("c_dob",m.getC_dob());
        contentValues.put("c_contact_mobile",m.getC_contact_mobile());
        contentValues.put("c_pancard",m.getC_pancard());
        contentValues.put("c_voter_id",m.getC_voter_id());
        contentValues.put("c_aadhar_card",m.getC_aadhar_card());
        contentValues.put("g_first_name",m.getG_first_name());
        contentValues.put("g_dob",m.getG_dob());
        contentValues.put("g_contact_mobile",m.getG_contact_mobile());
        contentValues.put("g_pancard",m.getG_pancard());
        contentValues.put("g_voter_id",m.getG_voter_id());
        contentValues.put("g_aadhar_card",m.getG_aadhar_card());
        contentValues.put("tenure",m.getTenure());
        contentValues.put("roi",m.getRoi());
        contentValues.put("applied_amt",m.getApplied_amt());
        contentValues.put("rec_amt",m.getRec_amt());
        contentValues.put("cibil_score",m.getCibil_score());
        contentValues.put("score",m.getScore());
        contentValues.put("ltv",m.getLtv());
        contentValues.put("foir",m.getFoir());
        contentValues.put("pdcam_remarks",m.getPdcam_remarks());
        contentValues.put("dcm_remarks",m.getDcm_remarks());
        contentValues.put("zcm_remarks",m.getZcm_remarks());
        contentValues.put("ncm_remarks",m.getNcm_remarks());
        contentValues.put("coo_remarks",m.getCoo_remarks());
        contentValues.put("added_at",m.getAdded_at());
        contentValues.put("product",m.getProduct());
        contentValues.put("loan_purpose",m.getLoan_purpose());
        contentValues.put("cm_amt",m.getCm_amt());
        contentValues.put("dcm_amt",m.getDcm_amt());
        contentValues.put("zcm_amt",m.getZcm_amt());
        contentValues.put("ncm_amt",m.getNcm_amt());
        contentValues.put("cro_amt",m.getCro_amt());
        contentValues.put("dcm_done_at",m.getDcm_done_at());
        contentValues.put("dcm_done_by",m.getDcm_done_by());
        contentValues.put("dcm_qry",m.getDcm_qry());
        contentValues.put("dcm_res_msg",m.getDcm_res_msg());
        contentValues.put("dcm_status",m.getDcm_status());
        contentValues.put("zcm_done_at",m.getZcm_done_at());
        contentValues.put("zcm_done_by",m.getZcm_done_by());
        contentValues.put("zcm_qry",m.getZcm_qry());
        contentValues.put("zcm_res_msg",m.getZcm_res_msg());
        contentValues.put("zcm_sts",m.getZcm_sts());
        contentValues.put("ncm_done_at",m.getNcm_done_at());
        contentValues.put("ncm_done_by",m.getNcm_done_by());
        contentValues.put("ncm_qry",m.getNcm_qry());
        contentValues.put("ncm_res_msg",m.getNcm_res_msg());
        contentValues.put("ncm_msg",m.getNcm_msg());
        contentValues.put("coo_done_at",m.getCoo_done_at());
        contentValues.put("coo_done_by",m.getCoo_done_by());
        contentValues.put("coo_qry",m.getCoo_qry());
        contentValues.put("coo_res_msg",m.getCoo_res_msg());
        contentValues.put("coo_sts",m.getCoo_sts());
        contentValues.put("main_sts",m.getMain_sts());
        contentValues.put("property_photo_file_name",m.getProperty_photo_file_name());
        contentValues.put("residency_photo_file_name",m.getResidency_photo_file_name());
        contentValues.put("collateral_photo_file_name",m.getCollateral_photo_file_name());


        long rows = db.insert("MemberInfo", null, contentValues);

        System.out.println("inserted properly :"+rows);
        return true;
    }

    //-----------------------------------------data sync----------------------------------

    public JSONObject getDataToSync()
    {
        JSONObject data=new JSONObject();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT borrower_name,mobile,rec_address,prop_address,kyc_type,kyc_id,property_type,property_localtion,business_type,business_discription,business_address,loan_purpose,source_of_lead,lead_type,edd,next_followup,loan_amt,uploaded,inserted_at,uniqno FROM LeadGen where uploaded= 'Pending' ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        JSONArray leads=new JSONArray();
        if(cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do {
                    JSONObject leadgen = new JSONObject();
                    try {
                        leadgen.put(cursor.getColumnName(0).toString(),cursor.getString(0));
                        leadgen.put(cursor.getColumnName(1).toString(),cursor.getString(1));
                        leadgen.put(cursor.getColumnName(2).toString(),cursor.getString(2));
                        leadgen.put(cursor.getColumnName(3).toString(),cursor.getString(3));
                        leadgen.put(cursor.getColumnName(4).toString(),cursor.getString(4));
                        leadgen.put(cursor.getColumnName(5).toString(),cursor.getString(5));
                        leadgen.put(cursor.getColumnName(6).toString(),cursor.getString(6));
                        leadgen.put(cursor.getColumnName(7).toString(),cursor.getString(7));
                        leadgen.put(cursor.getColumnName(8).toString(),cursor.getString(8));
                        leadgen.put(cursor.getColumnName(9).toString(),cursor.getString(9));
                        leadgen.put(cursor.getColumnName(10).toString(),cursor.getString(10));
                        leadgen.put(cursor.getColumnName(11).toString(),cursor.getString(11));
                        leadgen.put(cursor.getColumnName(12).toString(),cursor.getString(12));
                        leadgen.put(cursor.getColumnName(13).toString(),cursor.getString(13));
                        leadgen.put(cursor.getColumnName(14).toString(),cursor.getString(14));
                        leadgen.put(cursor.getColumnName(15).toString(),cursor.getString(15));
                        leadgen.put(cursor.getColumnName(16).toString(),cursor.getString(16));
                        leadgen.put(cursor.getColumnName(17).toString(),cursor.getString(17));
                        leadgen.put(cursor.getColumnName(18).toString(),cursor.getString(18));
                        leadgen.put(cursor.getColumnName(19).toString(),cursor.getString(19));
                        leads.put(leadgen);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }while (cursor.moveToNext());
            }
        }
        try{
            data.put("leads",leads);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return data;
    }

    public boolean updateaftersycn()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "update LeadGen set uploaded='Fresh Lead' where uploaded='Pending'";
        db.rawQuery(selectQuery, null);
        return true;
    }
}
