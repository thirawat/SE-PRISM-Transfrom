package se.chula.pta.component;

import java.util.List;

public class Point {
    protected List<Transition> transitions;
    
    public List<Transition> getTransitions() {
        return transitions;
    }

    public void addTransition(Transition transition){
        this.transitions.add(transition);
    }

    protected String id;
    public String getId() {
        return id;
    }
}
