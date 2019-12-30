package org.techtown.gps;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ParsingItem
{
    Map<String, Object> data = new HashMap<String, Object>();
    String xml;
    CurrentTime currentTime;

    ParsingItem(String xml)
    {
        if(xml == null) {
            Log.e("XML is NULL", xml);
            return;
        }
        this.xml = xml;

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            Document doc = documentBuilder.parse(is);
            Element element = doc.getDocumentElement();
            currentTime = new CurrentTime();

            NodeList list1 = element.getElementsByTagName("data");

            for(int i = 0; i < list1.getLength(); i++)
            {
                for(Node node = list1.item(i).getFirstChild(); node!= null; node = node.getNextSibling())
                {
                    if(node.getNodeName().equals("hour"))
                    {
                        data = new HashMap<String,Object>();
                        data.put("hour", node.getTextContent().toString());
                    }
                    if(node.getNodeName().equals("temp"))
                    {
                        data.put("temp", node.getTextContent().toString());
                    }
                    if(node.getNodeName().equals("reh"))
                    {
                        data.put("reh", node.getTextContent().toString());
                    }
                    if(node.getNodeName().equals(("wfEn")))
                    {
                        data.put("wfEn", node.getTextContent().toString());
                    }
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    public Map getWeatherParseItem()
    {
        return data;
    }


    public String getHumidity()
    {
        return data.get("reh").toString();
    }
    public String getHour()
    {
        return data.get("hour").toString();
    }



}
