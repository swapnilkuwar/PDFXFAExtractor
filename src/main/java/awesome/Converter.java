package awesome;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Converter
{
    public static void main(String[] args)
    {
        try
        {
            File file = new File("/home/swapnil/Desktop/form3.pdf");
            PDDocument document = PDDocument.load(file);
            
            byte[] xda = document.getDocumentCatalog().getAcroForm().getXFA().getBytes();
            
            String xmlStr = new String(xda);
            
            final Pattern pattern = Pattern.compile("<xfa:data\n>(.+?)</xfa:data", Pattern.DOTALL);
            final Matcher matcher = pattern.matcher(xmlStr);
            matcher.find();
            String xfaData = matcher.group(1).replace("\n", " ");
            
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode node = xmlMapper.readTree(xfaData);
            System.out.println(node);
            
        }
        catch(Exception ie)
        {
            ie.printStackTrace();
        }
    }
}