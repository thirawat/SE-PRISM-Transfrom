package se.chula.pta.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

public class Declaration {
    private List<Object> states;

    private List<String> params;

    private List<String> transformString;
    private String declarationString;
    public String getDeclarationString() {
        return declarationString;
    }

    public Declaration() {
        this.declarationString = "";        
    }    

    private void prepare() {
        String clean = this.declarationString.replaceAll("\\s{2,}", " ").trim();
        List<String> definitionList = Arrays.asList(clean.split(";"));
        this.states = new ArrayList<Object>();
        // this.transformString = new ArrayList<String>();
        
        this.params = new ArrayList<String>();
        for (String definition : definitionList) {
            String[] withAssign = definition.trim().split("=");
            String[] defElement = withAssign[0].split(" ");
            Map<String,Object> mapData = new HashMap<String,Object>();
            Integer last = defElement.length - 1;
            this.params.add(defElement[last]);
            mapData.put("param", defElement);
            mapData.put("value", withAssign.length > 1?withAssign[1].trim():null);
            this.states.add(mapData);
        }
    }

    public List<String> transform(){
        this.transformString = new ArrayList<String>();
        for(Object state : this.states){
            Map<String,Object> stateMap = (Map<String,Object>)state;
            String[] defElement = (String[]) stateMap.get("param");
            String value = (String) stateMap.get("value");
            Integer last = defElement.length - 1;
            String defSring = defElement[last]+": "+defElement[last-1];
            this.transformString.add(defSring +(value != null?" init "+value:"")+ ";");   
        }
        return this.transformString;
    }
    
    public boolean hasParam(String param_name){
        return this.params.contains(param_name);
    }
    
    public void addNode(Node item) {
        this.declarationString += item.getTextContent();
        this.prepare();
    }
}
