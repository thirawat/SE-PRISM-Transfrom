package se.chula.pta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.chula.pta.component.Branchpoint;
import se.chula.pta.component.Declaration;
import se.chula.pta.component.Location;
import se.chula.pta.component.Point;
import se.chula.pta.component.Template;
import se.chula.pta.component.Transition;

public class PRISMModel {
    Template template;
    public PRISMModel(Template template) {
        this.template = template;
    }
    
    public String transform() throws Exception {
        String prism_code = "pta\n";
        String name = this.template.getName();
        prism_code += "module " + name + "\n";
        prism_code += "\n";
        prism_code += this.formatDefinition();
        prism_code += "\n";
        prism_code += this.formatInvariant();
        prism_code += "\n";
        prism_code += this.formatTransition();
        prism_code += "\n";
        prism_code += "endmodule\n";
        return prism_code;
    }

    private String formatTransition() throws Exception {
        List<String> result = new ArrayList<String>();
        List<Location> locations = this.template.getLocations();
        String state_param = this.template.getStateParamName();
        for(Location location : locations){
            List<Transition>  transitions = location.getTransitions();
            for(Transition transition : transitions){
                Point targetObj = transition.getTargetObj();
                List<String> transitionList = new ArrayList<String>();
                transitionList.add("[]");
                transitionList.add(state_param+"="+location.getIndex());
                String guard = transition.getGuard();
                if(guard != null){
                    transitionList.add("& ("+guard+")");
                }
                if(targetObj instanceof Location){
                    Location targetLocation = (Location)targetObj;
                    transitionList.add("->");    
                    transitionList.add("("+state_param+"'="+targetLocation.getIndex()+")");
                    String assignment =  this.formatTransitionAssignment(transition.getAssignment());
                    if(assignment != null){
                        transitionList.add("& "+assignment);
                    };
                    transitionList.add(";");
                    result.add(String.join(" ", transitionList));
                }
                else if(targetObj instanceof Branchpoint){
                    List<Transition>  branchTransitions = targetObj.getTransitions();
                    transitionList.add("\n     ->");    
                    List<String> branchTransitionList = new ArrayList<String>();
                    for(Transition branchTransition : branchTransitions){
                        Location targetLocationObj = (Location)branchTransition.getTargetObj();
                        Double probability = branchTransition.getFormatProbability();
                        String branchTransitionString = probability+" : ("+state_param+"'="+targetLocationObj.getIndex()+")";
                        String assignment =  this.formatTransitionAssignment(branchTransition.getAssignment());
                        if(assignment != null){
                            branchTransitionString += " & "+assignment;
                        };
                        branchTransitionList.add(branchTransitionString);
                    }
                    if(branchTransitionList.size() > 0){
                        transitionList.add(String.join("\n      + ", branchTransitionList));
                    }else{
                        throw new Exception("No branch transition");
                    }
                    transitionList.add(";");
                    result.add(String.join(" ", transitionList));
                }
            }
        }
        
        return this.formatResult(result);
    }

    private String formatInvariant() {
        List<String> invariantList = new ArrayList<String>();
        List<Location> locations = this.template.getLocations();
        for (Location location : locations) {
            String invariantString = location.formatInvariant();
            if (invariantString != null)
                invariantList.add(invariantString);
        }
        List<String> result = new ArrayList<String>();
        if (invariantList.size() > 0) {
            result.add("invariant");
            result.add("  " + String.join(" & ", invariantList));
            result.add("endinvariant");
        }
        return this.formatResult(result);
    }

    private String formatDefinition() {
        Declaration declaration = this.template.getDeclaration();
        List<String> declarations = declaration.transform();

        List<String> def_list = this.formatStateDefinition();
        def_list.addAll(declarations);
        return this.formatResult(def_list);
    }

    private List<String> formatStateDefinition() {

        Integer init = 0;
        List<String> stateName = new ArrayList<String>();
        List<Location> locations = this.template.getLocations();
        String state_param = this.template.getStateParamName();
        String templateInit = this.template.getInit();
        for (Location location : locations) {
            if (templateInit.equals(location.getId()))
                init = location.getIndex();
            stateName.add("// " + location.getName() + " : " + location.getIndex());
        }
        List<String> result = new ArrayList<String>();
        result.add(state_param + ": [0.." + (locations.size() - 1) + "] init " + init + ";");
        result.addAll(stateName);
        return result;
    }

    private String formatResult(List<String> result) {
        return result.size() > 0 ? "  " + String.join("\n  ", result) + "\n" : "";
    }

    private String formatTransitionAssignment(String assignment) {
        String result = assignment;
        if(assignment != null){
            List<String> assignmentResult = new ArrayList<String>();
            String[] assignList = assignment.split(",");
            for(String assignString : assignList){
                String[] dataAssign = assignString.split("(:=|=)");
                if(dataAssign.length == 2){
                    String param = dataAssign[0].trim();
                    String value = dataAssign[1].trim();
                    Character operation = param.charAt(param.length() - 1);
                    List<Character> operations = Arrays.asList(new Character[] { '+', '-', '*', '/' });
                    if(operations.contains(operation)){
                        param = param.substring(0, param.length() - 1).trim();
                        assignmentResult.add("("+param+"'="+param+operation+value+")");
                    }else{
                        assignmentResult.add("("+param+"'="+value+")");
                    }
                }else{
                    String param = dataAssign[0].trim();
                    if(param.indexOf("++") > 0){
                        param = param.replace("++", "");   
                        assignmentResult.add("("+param+"'="+param+"+1)");           
                    }else if(param.indexOf("--") > 0){
                        param = param.replace("++", "");   
                        assignmentResult.add("("+param+"'="+param+"-1)");           
                    }
                }
            }
            result = String.join(" & ", assignmentResult);
        }
        return result;
    }
}
