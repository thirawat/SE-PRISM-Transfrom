package se.chula.pta.component;

import java.util.*;
import org.w3c.dom.*;

public class Location extends Point{
    private Integer index;
    public Integer getIndex() {
        return index;
    }
    public void setIndex(Integer index) {
        this.index = index;
    }

    private String name;
    public String getName() {
        return name;
    }

    private String invariant;
    public String getInvariant() {
        return invariant;
    }


    public Location(Node item) {
        NamedNodeMap attr = item.getAttributes();
        this.id = attr.getNamedItem("id").getTextContent();
        this.name = attr.getNamedItem("id").getTextContent();
        this.transitions = new ArrayList<Transition>();
        NodeList child_nodes = item.getChildNodes();
        for (int i = 0; i < child_nodes.getLength(); i++) {
            switch (child_nodes.item(i).getNodeName()) {
                case "name":
                    this.name = child_nodes.item(i).getTextContent();
                    break;
                case "label":
                    switch (child_nodes.item(i).getAttributes().getNamedItem("kind").getTextContent()) {
                        case "invariant":
                            this.invariant = child_nodes.item(i).getTextContent();
                            break;
                    }
                    break;
            }
        }
    }
}
