import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Tree implements Runnable{
    Node root;

    public void run(){
        try {
            Tree tree1 = new Tree(0L);
            Scanner sc1 = new Scanner(new File("input.txt"));
            Long temp=sc1.nextLong();
            sc1.nextLine();
            int i = 0;
            while (sc1.hasNext()) {
                if (i == 0) {
                    tree1 = new Tree(sc1.nextLong());
                    i++;
                } else tree1.root.add(sc1.nextLong());
            }
            tree1.root=tree1.root.delete(tree1.root,temp);
            FileWriter wr = new FileWriter("output.txt");
            tree1.root.write(tree1.root, wr);
            wr.close();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    Tree(){}

    Tree(Long val) {
        root = new Node(val);
    }

    class Node {
        Long value;
        Node right;
        Node left;

        Node(Long val, Node l, Node r) {
            value = val;
            left = l;
            right = r;
        }

        Node(Long val) {
            value = val;
        }

        public Long get_value() {
            return value;
        }

        public void set_value(Long value1) {
            value = value1;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void setLeft(Node left1) {
            left = left1;
        }

        public void setRight(Node right1) {
            right = right1;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", right=" + right +
                    ", left=" + left +
                    '}';
        }

        public void add(Long val) {
            if (val.compareTo(this.value) < 0) {
                if (this.left == null) {
                    this.left = new Node(val);
                }
                else this.left.add(val);
            }
            else if (val.compareTo(this.value) > 0) {
                if (this.right == null) {
                    this.right = new Node(val);
                } else this.right.add(val);
            }
        }

        public void write(Node r, FileWriter w) throws IOException {
            if (r != null) {
                w.write(r.value.toString() + "\n");
                write(r.left, w);
                write(r.right, w);
            }
        }

        public Node find_min(Node r){
            if(r.left!=null){
                return find_min(r.left);
            }
            else return r;
        }

        public Node delete(Node v1,Long v2){
            if(v1==null){return null;}
            if(v1.value.compareTo(v2)<0){
                v1.right=delete(v1.right,v2);
                return v1;
            }
            else if(v1.value.compareTo(v2)>0){
                v1.left=delete(v1.left,v2);
                return v1;
            }
            if(v1.left==null){
                return v1.right;
            }
            else if(v1.right==null){
                return v1.left;
            }
            else {
                Long temp=find_min(v1.right).value;
                v1.value=temp;
                v1.right=delete(v1.right,temp);
                return v1;
            }
        }
    }

    public static void main(String[] args) {
        new Thread(null, new Tree(), "", 256 * 1024 * 1024).start();
    }
}