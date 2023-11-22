package se.chula.pta.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Template {
    private String name;
    private List<Location> locations;
    private List<Transition> transitions;
    private List<Branchpoint> branchpoints;
    private Declaration declaration;
    private String init;
    private String state_param_name;

    private Map<String, Point> map_location;

    public Template() throws Exception {
        this.locations = new ArrayList<Location>();
        this.transitions = new ArrayList<Transition>();
        this.branchpoints = new ArrayList<Branchpoint>();
        this.declaration = new Declaration();
    }


    public String getName() {
        return name;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<Branchpoint> getBranchpoints() {
        return branchpoints;
    }
    
    public String getInit() {
        return init;
    }

    public String getStateParamName() {
        return state_param_name;
    }

    public Declaration getDeclaration() {
        return declaration;
    }

    public void setupTemplate(Node template) throws Exception {
        NodeList nodes = template.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            switch (nodes.item(i).getNodeName()) {
                case "name":
                    this.name = nodes.item(i).getTextContent();
                    break;
                case "declaration":
                    this.declaration.addNode(nodes.item(i));
                    break;
                case "location":
                    this.locations.add(new Location(nodes.item(i)));
                    break;
                case "init":
                    this.init = nodes.item(i).getAttributes().getNamedItem("ref").getTextContent();
                    break;
                case "transition":
                    this.transitions.add(new Transition(nodes.item(i)));
                    break;
                case "branchpoint":
                    this.branchpoints.add(new Branchpoint(nodes.item(i)));
                    break;
            }
        }
        this.setupStructure();
    }

    public void addDeclaration(Node declaration) throws Exception {
        if(declaration != null){
            this.declaration.addNode(declaration);
        }
    }

    private void setupStructure() throws Exception {
        String state_name = "s";
        this.state_param_name = state_name;
        this.map_location = new HashMap<String, Point>();
        for (Integer number = 1; this.declaration.hasParam(this.state_param_name); number++) {
            this.state_param_name = state_name + number;
        }
        for (Integer index = 0; index < this.locations.size(); index++) {
            Location location = this.locations.get(index);
            location.setIndex(index);
            location.setParamName(this.state_param_name);
            this.addMapLocation(location.getId(), location);
        }
        for (Branchpoint branchpoint : branchpoints) {
            this.addMapLocation(branchpoint.getId(), branchpoint);
        }

        for (Transition transition : this.transitions) {
            if (this.map_location.containsKey(transition.getSource())
                    && this.map_location.containsKey(transition.getSource())) {
                Point sourceLocation = this.map_location.get(transition.getSource());
                Point targetLocation = this.map_location.get(transition.getTarget());
                transition.setSourceObj(sourceLocation);
                transition.setTargetObj(targetLocation);
                
                if (sourceLocation instanceof Location) {
                    Location location = (Location) sourceLocation;
                    location.addTransition(transition);
                }
                if (sourceLocation instanceof Branchpoint) {
                    Branchpoint branchpoint = (Branchpoint) sourceLocation;
                    branchpoint.addTransition(transition);
                    if (!(targetLocation instanceof Location)) {
                        throw new Exception("The Transition from the branchpoint must connect to the location. on "
                                + transition.getSource() + " to " + transition.getTarget());
                    }
                }
            } else {
                throw new Exception("The location on transition not found");
            }
        }

    }

    private void addMapLocation(String id, Point location) throws Exception {
        if (!this.map_location.containsKey(id)) {
            this.map_location.put(id, location);
        } else {
            throw new Exception("map location duplicate");
        }
    }
    
}
