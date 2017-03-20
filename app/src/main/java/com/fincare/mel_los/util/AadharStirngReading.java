package com.fincare.mel_los.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fincare.mel_los.LeadGen;
import com.fincare.mel_los.data.Aadhar;
import com.google.zxing.integration.android.IntentIntegrator;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Phaneendra on 18-Aug-16.
 */
@SuppressLint("NewApi")
public class AadharStirngReading {



    public Aadhar getAadhar(String contents, Context context) {
        //contents=result.getContents().replace("\"", "'").toString();
        Aadhar a=new Aadhar();
        try {
            String[] str = contents.split(" ");
            //Toast.makeText(getApplicationContext(), str[0], Toast.LENGTH_LONG).show();
            Log.d("com.test", str[0]);
            if (!str[0].equals("<?xml")) {
                Toast.makeText(context, "Please scan Only Aadhar Card1", Toast.LENGTH_LONG).show();
                return null;
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document
            Document document = builder.parse(new ByteArrayInputStream(contents.getBytes()));

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();


            //Get all employees
            NodeList nList = document.getElementsByTagName("PrintLetterBarcodeData");

            if (nList.getLength() == 0) {
                Toast.makeText(context, "Please scan Only Aadhar Card", Toast.LENGTH_LONG).show();
                return null;
            }

            Node node = nList.item(0);
            NamedNodeMap nodeMap = node.getAttributes();

            if (nodeMap.getLength() == 0) {
                Toast.makeText(context, "Please scan Only Aadhar Card", Toast.LENGTH_LONG).show();
                return null;
            }

            for (int i = 0; i < nodeMap.getLength(); i++) {
                Node tn = nodeMap.item(i);
                switch (tn.getNodeName().toString()) {
                    case "co":
                        a.setCo( tn.getNodeValue().toString() == null ? " " : tn.getNodeValue().toString());
                        break;
                    case "dist":
                        a.setDist( tn.getNodeValue().toString() == null ? " " : tn.getNodeValue().toString());
                        break;
                    case "gender":
                        a.setGender( tn.getNodeValue().toString() == null ? " " : tn.getNodeValue().toString());
                        break;
                    case "house":
                        a.setHouse( tn.getNodeValue().toString() == null ? " " : tn.getNodeValue().toString());
                        break;
                    case "loc":
                        a.setLoc( tn.getNodeValue().toString() == null ? " " : tn.getNodeValue().toString());
                        break;
                    case "name":
                        a.setName ( tn.getNodeValue().toString() == null ? " " : (tn.getNodeValue().toString()));
                        break;
                    case "pc":
                        a.setPc (tn.getNodeValue().toString() == null ? " " : (tn.getNodeValue().toString()));
                        break;
                    case "po":
                        a.setPo (tn.getNodeValue().toString() == null ? " " : (tn.getNodeValue().toString()));
                        break;
                    case "state":
                        a.setState( (tn.getNodeValue().toString()) == null ? " " : (tn.getNodeValue().toString()));
                        break;
                    case "street":
                        a.setStreet( (tn.getNodeValue().toString()) == null ? " " : (tn.getNodeValue().toString()));
                        break;
                    case "uid":
                        a.setUid( (tn.getNodeValue().toString()) == null ? " " : (tn.getNodeValue().toString()));
                        break;
                    case "vtc":
                        a.setVtc( (tn.getNodeValue().toString()) == null ? " " : (tn.getNodeValue().toString()));
                        break;
                    case "yob":
                        a.setYob( (tn.getNodeValue().toString()) == null ? " " : (tn.getNodeValue().toString()));
                        break;
                    default:
                        break;
                }


            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return a;
    }
}
