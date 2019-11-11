package awesome;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.json.JSONObject;
import org.json.XML;

public class Converter
{
    public static void main(String[] args)
    {
        try
        {
            File file = new File(args[0]);
            PDDocument document = PDDocument.load(file);
            
            byte[] xda = document.getDocumentCatalog().getAcroForm().getXFA().getBytes();
            
            String xmlStr = new String(xda);

            final Pattern pattern = Pattern.compile("<xfa:data\n>(.+?)</xfa:data", Pattern.DOTALL);
            final Matcher matcher = pattern.matcher(xmlStr);
            matcher.find();
            String xfaData = matcher.group(1).replace("\n", " ").replace("\r", "");
            
            JSONObject xmlJSONObj = XML.toJSONObject(xfaData, true);
            String output = xmlJSONObj.toString(0);
            System.out.println(output);            
        }
        catch(Exception ie){}
    }
}