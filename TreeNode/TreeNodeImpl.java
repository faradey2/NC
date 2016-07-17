import java.util.ArrayList;
import java.util.Iterator;

public class TreeNodeImpl implements TreeNode {

    TreeNode _parent;
    ArrayList<TreeNode> _children;
    Object  _data;
    boolean _expanded;

    public TreeNodeImpl(){
        _children = new ArrayList<TreeNode>();
        _expanded = false;
    }

    @Override
    public TreeNode getParent() {
        return _parent;
    }

    @Override
    public void setParent(TreeNode parent) {
        _parent = parent;
    }

    @Override
    public TreeNode getRoot() {
        if(_parent == null) return null;
        else {
            TreeNode root =  _parent.getRoot();
            if(root == null)
                return _parent;
            else return root;
        }
    }

    @Override
    public boolean isLeaf() {
        if(_children.size() == 0) return true;
        else return false;
    }

    @Override
    public int getChildCount() {
        return _children.size();
    }

    @Override
    public Iterator<TreeNode> getChildrenIterator() {
        return _children.iterator();
    }

    @Override
    public void addChild(TreeNode child) {
        _children.add(child);
        child.setParent(this);
    }

    @Override
    public boolean removeChild(TreeNode child) {
        try{
            if(_children.remove(child)) {
                child.setParent(null);
                return true;
            }
            else    return false;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean isExpanded() {
        return _expanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        _expanded = expanded;
        for(int i = 0 ; i < getChildCount(); i++)
            _children.get(i).setExpanded(expanded);
    }

    @Override
    public Object getData() {
        return _data;
    }

    @Override
    public void setData(Object data) {
        _data = data;
    }

    @Override
    public String getTreePath() {
        String path = "";
        if(_parent != null)
            path = _parent.getTreePath() + "->";
        if(getData() == null)   path += "empty";
        else    path += getData().toString();
        return path;
    }

    @Override
    public TreeNode findParent(Object data) {
        if(getData().equals(data)) return this;
        if (_parent == null) return null;
        else return _parent.findParent(data);
    }

    @Override
    public TreeNode findChild(Object data) {
        TreeNode result;
        for(int i = 0;i<_children.size();i++) {
            if(_children.get(i).getData() == null){
                if(data == null)    return _children.get(i);}
            else if(_children.get(i).getData().equals(data)) return _children.get(i);
            result = _children.get(i).findChild(data);
            if (result != null)
                return result;
        }
        return null;
    }
}
