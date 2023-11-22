package se.chula.pta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import se.chula.pta.component.Template;

public class PTAConverter {

    public List<Template> templates;
    public Node declarations;

    public void loadUppaalModel(String xmlPath) throws Exception   {
        File xmlFile = new File(xmlPath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        this.ignoreDoctype(dbf);
        DocumentBuilder db = dbf.newDocumentBuilder();  
        Document doc = db.parse(xmlFile);  
        Element root = doc.getDocumentElement();
        NodeList templateNodes = root.getElementsByTagName("template");
        if(templateNodes.getLength() > 0){
            this.setDeclaration(root);
            this.templates = new ArrayList<Template>();
            for(int i = 0; i < templateNodes.getLength();i++ ){
                this.addTemplate(templateNodes.item(i));
            }
        }else{
            throw new Exception("No template found");
        }
        
    }

    public void addTemplate(Node template) throws Exception{
        Template templateObj = new Template();
        templateObj.addDeclaration(this.declarations);
        templateObj.setupTemplate(template);
        this.templates.add(templateObj);
    }

    public void setDeclaration(Element root) {
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals("declaration")) {
                this.declarations = node;
            }
        }
    }

    private void ignoreDoctype(DocumentBuilderFactory dbf) throws ParserConfigurationException{

        dbf.setValidating(false);
        dbf.setNamespaceAware(true);
        dbf.setFeature("http://xml.org/sax/features/namespaces", false);
        dbf.setFeature("http://xml.org/sax/features/validation", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    }

    public String transform() throws Exception {
        String prism_format = "";
        for (Template prismTemplate : this.templates) {
            PRISMModel prismModel = new PRISMModel(prismTemplate);
            prism_format += prismModel.transform();
        } 
        return prism_format;
    }
}
