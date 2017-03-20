package com.fincare.mel_los.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Phaneendra on 02-Sep-16.
 */
public class MelProvider extends ContentProvider{

    public static final String PROVIDER_NAME = "com.fincare.provider.mellos";
    static final String URL = "content://" + PROVIDER_NAME ;
    public static final Uri CONTENT_URI1 = Uri.parse(URL+"/BRANCHS");
    public static final Uri CONTENT_URI2 = Uri.parse(URL+"/MASTER");
    public static final Uri CONTENT_URI3 = Uri.parse(URL+"/LEAD");
    public static final Uri CONTENT_URI4 = Uri.parse(URL+"/MEMBER");
    public static final Uri CONTENT_URI5 = Uri.parse(URL+"/USER");
    public static final Uri CONTENT_URI6 = Uri.parse(URL+"/SYNC");

    private static HashMap<String, String> BRANCHS_PROJECTION_MAP;
    private static HashMap<String, String> MASTER_PROJECTION_MAP;
    private static HashMap<String, String> LEAD_PROJECTION_MAP;
    private static HashMap<String, String> MEMBER_PROJECTION_MAP;
    private static HashMap<String, String> USER_PROJECTION_MAP;
    private static HashMap<String, String> SYNC_PROJECTION_MAP;

    static final int BRANCHS = 1;
    static final int BRANCHS_ID = 2;
    static final int MASTER = 3;
    static final int MASTER_ID = 4;
    static final int LEAD = 5;
    static final int LEAD_ID = 6;
    static final int MEMBER = 7;
    static final int MEMBER_ID = 8;
    static final int USER=9;
    static final int USER_ID=10;
    static final int SYNC=11;


    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "BRANCHS", BRANCHS);
        uriMatcher.addURI(PROVIDER_NAME, "BRANCHS/#", BRANCHS_ID);
        uriMatcher.addURI(PROVIDER_NAME, "MASTER", MASTER);
        uriMatcher.addURI(PROVIDER_NAME, "MASTER/#", MASTER_ID);
        uriMatcher.addURI(PROVIDER_NAME, "LEAD", LEAD);
        uriMatcher.addURI(PROVIDER_NAME, "LEAD/#", LEAD_ID);
        uriMatcher.addURI(PROVIDER_NAME, "MEMBER", MEMBER);
        uriMatcher.addURI(PROVIDER_NAME, "MEMBER/#", MEMBER_ID);
        uriMatcher.addURI(PROVIDER_NAME, "USER", USER);
        uriMatcher.addURI(PROVIDER_NAME, "USER/#", USER_ID);
        uriMatcher.addURI(PROVIDER_NAME, "SYNC", SYNC);
    }
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "Mellos.db";
    static final int DATABASE_VERSION = 1;

    static final String brn_qry="create table if not exists branches (id int,branch_name text,division_name text,zone_name text)";
    static final String master_qry="create table if not exists master (id int,name text,type text)";
    static final String lead_qry="create table if not exists LeadGen (id INTEGER PRIMARY KEY NOT NULL,borrower_name text, mobile text, rec_address text, prop_address text, kyc_type text, kyc_id text, property_type text, property_localtion text, business_type text, business_discription text, business_address text, loan_purpose text, source_of_lead text, lead_type text, edd text, next_followup text, loan_amt text,uploaded text,inserted_at text,uniqno text)";
    static final String member_qry="create table if not exists MemberInfo (branch text, Unique_Lead_No text, c_first_name text, c_dob text, c_contact_mobile text, c_pancard text, c_voter_id text, c_aadhar_card text, g_first_name text, g_dob text, g_contact_mobile text, g_pancard text, g_voter_id text, g_aadhar_card text, tenure text, roi text, applied_amt text, rec_amt text, cibil_score text, score text, ltv text, foir text, pdcam_remarks text, dcm_remarks text, zcm_remarks text, ncm_remarks text, coo_remarks text, added_at text, product text, loan_purpose text, cm_amt text, dcm_amt text, zcm_amt text, ncm_amt text, cro_amt text, dcm_done_at text, dcm_done_by text, dcm_qry text, dcm_res_msg text, dcm_status text, zcm_done_at text, zcm_done_by text, zcm_qry text, zcm_res_msg text, zcm_sts text, ncm_done_at text, ncm_done_by text, ncm_qry text, ncm_res_msg text, ncm_msg text, coo_done_at text, coo_done_by text, coo_qry text, coo_res_msg text, coo_sts text, main_sts text, property_photo_file_name text, residency_photo_file_name text, collateral_photo_file_name text,uploaded text)";
    static final String user_qry="create table if not exists User_Info (id INTEGER PRIMARY KEY NOT NULL,mfi_emp_name text,mfi_emp_user_name text,mfi_emp_user_pass text,mfi_emp_active text,route_access text,main_screen_access text,sub_l1_screen_access text)";
    static final String sync_qry="create table if not exists sync_info (id INTEGER PRIMARY KEY NOT NULL,master_vertion text,lastsync_at text)";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(brn_qry);
            db.execSQL(master_qry);
            db.execSQL(lead_qry);
            db.execSQL(member_qry);
            db.execSQL(user_qry);
            db.execSQL(sync_qry);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS branches");
            db.execSQL("DROP TABLE IF EXISTS master");
            db.execSQL("DROP TABLE IF EXISTS LeadGen");
            db.execSQL("DROP TABLE IF EXISTS MemberInfo");
            db.execSQL("DROP TABLE IF EXISTS User_Info");
            db.execSQL("DROP TABLE IF EXISTS sync_info");
            onCreate(db);
        }
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false : true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();


        switch (uriMatcher.match(uri)) {
            case BRANCHS:
                qb.setTables("branches");
                qb.setProjectionMap(BRANCHS_PROJECTION_MAP);
                break;

            case BRANCHS_ID:
                qb.setTables("branches");
                qb.appendWhere( "id = " + uri.getPathSegments().get(1));
                break;

            case MASTER:
                qb.setTables("master");
                qb.setProjectionMap(MASTER_PROJECTION_MAP);
                break;

            case MASTER_ID:
                qb.setTables("master");
                qb.appendWhere( "id = " + uri.getPathSegments().get(1));
                break;
            case LEAD:
                qb.setTables("LeadGen");
                qb.setProjectionMap(LEAD_PROJECTION_MAP);
                break;

            case LEAD_ID:
                qb.setTables("LeadGen");
                qb.appendWhere( "id = " + uri.getPathSegments().get(1));
                break;

            case MEMBER:
                qb.setTables("MemberInfo");
                qb.setProjectionMap(MEMBER_PROJECTION_MAP);
                break;

            case MEMBER_ID:
                qb.setTables("MemberInfo");
                qb.appendWhere( "Unique_Lead_No = " + uri.getPathSegments().get(1));
                break;

            case USER:
                qb.setTables("User_Info");
                qb.setProjectionMap(USER_PROJECTION_MAP);
                break;

            case USER_ID:
                qb.setTables("User_Info");
                qb.appendWhere( "id = " + uri.getPathSegments().get(1));
                break;

            case SYNC:
                qb.setTables("sync_info");
                qb.setProjectionMap(SYNC_PROJECTION_MAP);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }


        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null,sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all student records
             */
            case BRANCHS:
                return "vnd.android.cursor.dir/vnd.mellos.branches";


            case BRANCHS_ID:
                return "vnd.android.cursor.item/vnd.mellos.branches";

            case MASTER:
                return "vnd.android.cursor.dir/vnd.mellos.master";


            case MASTER_ID:
                return "vnd.android.cursor.item/vnd.mellos.master";

            case LEAD:
                return "vnd.android.cursor.dir/vnd.mellos.LeadGen";


            case LEAD_ID:
                return "vnd.android.cursor.item/vnd.mellos.LeadGen";

            case MEMBER:
                return "vnd.android.cursor.dir/vnd.mellos.MemberInfo";


            case MEMBER_ID:
                return "vnd.android.cursor.item/vnd.mellos.MemberInfo";


            case USER:
                return "vnd.android.cursor.dir/vnd.mellos.User_Info";


            case USER_ID:
                return "vnd.android.cursor.item/vnd.mellos.User_Info";

            case SYNC:
                return "vnd.android.cursor.dir/vnd.mellos.sync_info";


            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        switch (uriMatcher.match(uri)) {

            case BRANCHS:
                long rowID = db.insert("branches", "", contentValues);
                Log.d("get_data", "received");
                if (rowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI1, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
            break;
            case MASTER:
                long rowID1 = db.insert("master", "", contentValues);
                Log.d("get_data", "received");
                if (rowID1 > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI2, rowID1);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;
            case LEAD:
                long rowID2 = db.insert("LeadGen", "", contentValues);
                Log.d("get_data", "received");
                if (rowID2 > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI3, rowID2);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;
            case MEMBER:
                long rowID3 = db.insert("MemberInfo", "", contentValues);
                Log.d("get_data", "received");
                if (rowID3 > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI4, rowID3);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;

            case USER:
                long rowID4 = db.insert("User_Info", "", contentValues);
                Log.d("get_data", "received");
                if (rowID4 > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI5, rowID4);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;
            case SYNC:
                long rowID5 = db.insert("sync_info", "", contentValues);
                Log.d("get_data", "received");
                if (rowID5 > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI6, rowID5);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case BRANCHS:
                count = db.delete("branches", selection, selectionArgs);
                break;

            case BRANCHS_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( "branches",   "id  = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case MASTER:
                count = db.delete("master", selection, selectionArgs);
                break;

            case MASTER_ID:
                String id1 = uri.getPathSegments().get(1);
                count = db.delete( "master",   "id  = " + id1 +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case LEAD:
                count = db.delete("LeadGen", selection, selectionArgs);
                break;

            case LEAD_ID:
                String id2 = uri.getPathSegments().get(1);
                count = db.delete( "LeadGen",   "id  = " + id2 +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case MEMBER:
                count = db.delete("MemberInfo", selection, selectionArgs);
                break;

            case MEMBER_ID:
                String id3 = uri.getPathSegments().get(1);
                count = db.delete( "MemberInfo",   "Unique_Lead_No  = " + id3 +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case USER:
                count = db.delete("User_Info", selection, selectionArgs);
                break;

            case USER_ID:
                String id4 = uri.getPathSegments().get(1);
                count = db.delete( "User_Info",   "Unique_Lead_No  = " + id4 +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case SYNC:
                count = db.delete("sync_info", selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case BRANCHS:
                count = db.update("branches", values, selection, selectionArgs);
                break;

            case BRANCHS_ID:
                count = db.update("branches", values,  "id = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            case MASTER:
                count = db.update("master", values, selection, selectionArgs);
                break;

            case MASTER_ID:
                count = db.update("master", values,  "id = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            case LEAD:
                count = db.update("LeadGen", values, selection, selectionArgs);
                break;

            case LEAD_ID:
                count = db.update("LeadGen", values,  "id = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            case MEMBER:
                count = db.update("MemberInfo", values, selection, selectionArgs);
                break;

            case MEMBER_ID:
                count = db.update("MemberInfo", values,  "id = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            case USER:
                count = db.update("User_Info", values, selection, selectionArgs);
                break;

            case USER_ID:
                count = db.update("User_Info", values,  "id = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            case SYNC:
                count = db.update("sync_info", values, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
