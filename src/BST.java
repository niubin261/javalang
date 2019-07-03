import sun.reflect.generics.tree.Tree;

public class BST {
    public TreeNode root ;
    static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int val) {
            this.left = null;
            this.right = null;
            this.val = val;
        }
    }
    private TreeNode search(TreeNode v, int target) {
        TreeNode u = v;
        while (true) {
            if (u.val > target) {
                if (u.left != null) {
                    u = u.left;
                } else {
                    return u;
                }
            } else if (u.val < target) {
                if (u.right != null) {
                    u = u.right;

                } else {
                    return u;
                }
            } else return u;
        }

    }
    private boolean insert(TreeNode root, int val) {
        if (root == null) {
            root = new TreeNode(val);
            return true;
        }
        TreeNode node = search(root, val);
        if (node.val != val) {
            TreeNode s = new TreeNode(val);
            s.left = null;
            s.right = null;
            if (val < node.val) {
                node.left = s;
            } else node.right = s;
            return true;
        }
        return false;
    }
    private TreeNode getParent(TreeNode root, TreeNode child) {
        if (root == null || root.left == child || root.right == child) return root;
        if (child.val < root.val) {

            return getParent(root.left, child);
        } else return getParent(root.right, child);
    }
    public boolean remove(TreeNode root, int val) {
        TreeNode v = search(root, val);
        if (v.val != val)return false;
        TreeNode parent = getParent(root,v);
        if (v.left != null && v.right != null) {
            TreeNode succ = v.right;
            while (succ.left != null)succ = succ.left;
            v.val = succ.val;
            v = succ;
        }
        //delete v
        return true;

    }
    public static void main(String [] args) {
        BST bst = new BST();
        TreeNode root = new TreeNode(0);
        bst.insert(root,6);
        bst.insert(root,11);
        bst.insert(root,7);
    }
}
