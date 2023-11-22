package se.chula.pta.component;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Branchpoint extends Point {
    

    public Branchpoint(Node item) {
        NamedNodeMap attr = item.getAttributes();
        this.transitions = new ArrayList<Transition>();
        this.id = attr.getNamedItem("id").getTextContent();
    }
}
