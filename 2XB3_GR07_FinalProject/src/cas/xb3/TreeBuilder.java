package cas.xb3;

import java.util.ArrayList;
import java.util.List;

public class TreeBuilder {

    //TreeNodes is the dataStructure
    private class TreeNodes {
        private List<TreeNodes> childrenNodes;
        private String string;

        //TreeNodes Constructor
        public TreeNodes(List<TreeNodes> childrenNodes, TreeNodes parentNode, String string) {
            this.childrenNodes = childrenNodes;
            this.string = string;
        }

        //Adds to the childrenNodes of a given TreeNode
        public void addNode(TreeNodes newNode) {
            try {
                this.childrenNodes.add(newNode);
            } catch (NullPointerException e) {
                this.childrenNodes = new ArrayList<>();
                this.childrenNodes.add(newNode);
            }
        }

        //Returns childrenNodes
        public List<TreeNodes> getChildrenNodes() {
            return childrenNodes;
        }

        //Returns name of node
        public String getString() {
            return string;
        }
    }

    private TreeNodes root;

    //Initializes a new TreeBuilder
    public TreeBuilder() {
        root = new TreeNodes(null, null, "root");
    }

    //Adds a category
    public void addCategory(String category) {
        root.addNode(new TreeNodes(null, root, category));
    }

    //Adds a food under a given category
    public void addFood(String food, String category) {
        for(TreeNodes treeNodes : root.getChildrenNodes())
            if(treeNodes.getString().equals(category))
                treeNodes.addNode(new TreeNodes(null, treeNodes, food));
    }

    //Returns categories
    public List<String> getCategories() {
        List<String> mStrings = new ArrayList<>();
        for(TreeNodes treeNode : root.getChildrenNodes())
            mStrings.add(treeNode.getString());
        return mStrings;
    }

    //Returns foods under a given category
    public List<String> getFoods(String category) {
        List<String> mStrings = new ArrayList<>();
        for(TreeNodes treeNode : root.getChildrenNodes())
            if(treeNode.getString().equals(category))
                for(TreeNodes treeNodes : treeNode.getChildrenNodes())
                    mStrings.add(treeNodes.getString());
        return mStrings;
    }
}
