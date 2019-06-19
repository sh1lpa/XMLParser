

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

public class StaxXMLReader extends SampleBase{

    public StaxXMLReader(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
    
	public static void main(String[] args) throws IOException {
    		String fileName = "/example.xml";
    		
    				
		System.setProperty("jdk.xml.totalEntitySizeLimit", "500000000");

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
    		 XMLInputFactory xif = null;
    	        try {
    	            xif = XMLInputFactory.newInstance();
    	            xif.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
    	            xif.setProperty(JDK_ENTITY_EXPANSION_LIMIT, "1000");
    	            xif.setProperty(JDK_TOTAL_ENTITY_SIZE_LIMIT, "100000000");
    	            parseXML(fileName);
    	        } catch (Exception e) {
    	            
    	        }
    		
         
        		
       
    }

    private static void parseXML(String fileName) throws IOException {
    
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        File dir = new File("/OUTPUT");
        System.out.println(dir.mkdir());
        System.out.println(dir.getPath());
        FileWriter fw1 = null ; 
        Integer count = 0, count_ = 0;
        Integer text_t =0, sku = 0;
        String str = "";
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
               if (xmlEvent.isStartElement()){
                   StartElement startElement = xmlEvent.asStartElement();
                   if(startElement.getName().getLocalPart().equals("str")){
                      
                       Attribute text_tAttr = startElement.getAttributeByName(new QName("name"));
                       if(text_tAttr != null){
                    	   
                           if(text_tAttr.getValue().equals("text_t") || text_tAttr.getValue().equals("sku")) {
                        	   count++;
                        	   if(text_t == sku ) {
                        		   fw1=new FileWriter(dir.getPath()+"/tags_"+count+".txt");
                        	   }
                        	   xmlEvent = xmlEventReader.nextEvent();
                        	   if(xmlEvent.isCharacters()) {
                        		   try{  
	                        			   if(text_tAttr.getValue().equals("text_t") && fw1 != null) {
	                        				   text_t++;
	                        				   fw1.write(xmlEvent.asCharacters().toString()+ "\n");    
	                            	         
	                        			   }
	                        			   else if (text_tAttr.getValue().equals("sku") && fw1 != null)  {
	                        				   sku++;
	                        					StringTokenizer token = new StringTokenizer(xmlEvent.asCharacters().toString(), "/");
	                        					while(token.hasMoreTokens()) {
	                        						str = token.nextToken();
	                        						count_++;
	                        						System.out.println(count_);
	                        						if (count_ == 2) {
	                        							fw1.write( str	+ "\n");    
	                        							count_ =0;
	                        							break;
	                        						}
	                        					
	                        						
	                        					}
	                        				   
	                        			   }        
	                        			   
	                        			   if(text_t == sku && text_t > 0) {
	                                    	   fw1.close();
	                                       }
                        		   }
                        		   catch(Exception e) {
                        			   System.out.println(e);
                        		   }    
                        	   }
                        	   
                           }
                           
                        }
                   }
                  
               }
               //if Employee end element is reached, add employee object to list
               if(xmlEvent.isEndElement()){
                   EndElement endElement = xmlEvent.asEndElement();
               }
               
               
            }
           
            
            System.out.println("Done");
            
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        finally {
        	
			
		}
    }

}
