package se.chula.pta.component;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Transition {
    private Point sourceObj;

    public Point getSourceObj() {
        return sourceObj;
    }

    public void setSourceObj(Point sourceObj) {
        this.sourceObj = sourceObj;
    }

    private Point targetObj;

    public Point getTargetObj() {
        return targetObj;
    }

    public void setTargetObj(Point targetObj) {
        this.targetObj = targetObj;
    }

    private String source;

    public String getSource() {
        return source;
    }

    private String target;

    public String getTarget() {
        return target;
    }

    private String guard;

    public String getGuard() {
        return guard != null
                ? guard
                        .replaceAll("&&", "&")
                        .replaceAll("\\|\\|", "|")
                : null;
    }

    private String assignment;

    public String getAssignment() {
        return assignment;
    }

    private String probability;
    public Double getProbability() throws Exception {
        if (probability == null) {
            throw new Exception("No probability on branch point transition");
        }
        return Double.parseDouble(probability);
    }

    public Transition(Node item) {
        NodeList child_nodes = item.getChildNodes();
        for (int i = 0; i < child_nodes.getLength(); i++) {
            switch (child_nodes.item(i).getNodeName()) {
                case "source":
                    this.source = child_nodes.item(i).getAttributes().getNamedItem("ref").getTextContent();
                    break;
                case "target":
                    this.target = child_nodes.item(i).getAttributes().getNamedItem("ref").getTextContent();
                    break;
                case "label":
                    switch (child_nodes.item(i).getAttributes().getNamedItem("kind").getTextContent()) {
                        case "guard":
                            this.guard = child_nodes.item(i).getTextContent();
                            break;
                        case "assignment":
                            this.assignment = child_nodes.item(i).getTextContent();
                            break;
                        case "probability":
                            this.probability = child_nodes.item(i).getTextContent();
                            break;
                    }
                    break;
            }
        }
    }
}
