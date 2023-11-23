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

    public Declaration() {
        this.states = new ArrayList<Object>();
        this.params = new ArrayList<String>();
    }    

    public List<Object> getStates() {
        return states;
    }
    
    public boolean hasParam(String param_name){
        return this.params.contains(param_name);
    }
    
    public void addNode(Node item) {
        String declarationString = item.getTextContent();
        String clean = declarationString.replaceAll("\\s{2,}", " ").trim();
        List<String> definitionList = Arrays.asList(clean.split(";"));
        for (String definition : definitionList) {
            String[] withAssign = definition.trim().split("=");
            String[] defElement = withAssign[0].split(" ");
            Map<String,Object> mapData = new HashMap<String,Object>();
            Integer last = defElement.length - 1;
            String parameterName = defElement[last];
            if(!this.hasParam(parameterName)){
                this.params.add(parameterName);
                mapData.put("param", defElement);
                mapData.put("value", withAssign.length > 1?withAssign[1].trim():null);
                this.states.add(mapData);
            }else{
                throw new RuntimeException("Duplicate parameter name: "+parameterName);
            }
        }
    }
}
