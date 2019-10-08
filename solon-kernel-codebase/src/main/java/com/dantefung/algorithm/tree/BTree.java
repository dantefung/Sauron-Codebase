package com.dantefung.algorithm.tree;

/**
 * 在计算机科学中，B树（英语：B-tree）是一种自平衡的树，能够保持数据有序。这种数据结构能够让查找数据、顺序访问、插入数据及删除的动作，都在对数时间内完成。
 * B 树是为了磁盘或其它存储设备而设计的一种多叉平衡查找树。
 *
 * 我们需要注意一个概念，描述一颗B树时需要指定它的阶数，阶数表示了一个节点最多有多少个孩子节点，一般用字母m表示阶数。
 *
 * 一棵m阶的B-Tree有如下特性：
 * 1. 每个节点最多有m个孩子。
 * 2. 除了根节点和叶子节点外（即内部节点），其它每个节点至少有Ceil(m/2)个孩子。
 * 3. 若根节点不是叶子节点，则至少有2个孩子
 * 4. 所有叶子节点都在同一层，且不包含其它关键字信息
 * 5. 每个非终端节点包含n个关键字信息（P0,P1,…Pn, k1,…kn）
 * 6. 关键字的个数n满足：ceil(m/2)-1 <= n <= m-1
 * 7. ki(i=1,…n)为关键字，且关键字升序排序。
 * 8. Pi(i=1,…n)为指向子树根节点的指针。P(i-1)指向的子树的所有节点关键字均小于ki，但都大于k(i-1)
 *
 *  Ceil向上取整
 *
 *
 * @param <Key>
 * @param <Value>
 */
public class BTree<Key extends Comparable<Key>, Value>  {
    private static final int M = 4;    // max children per B-tree node = M-1

    private Node root;             // root of the B-tree
    private int HT;                // height of the B-tree
    private int N;                 // number of key-value pairs in the B-tree

    // helper B-tree node data type
    private static final class Node {
        private int m;                             // number of children
        private Entry[] children = new Entry[M];   // the array of children
        private Node(int k) { m = k; }             // create a node with k children
    }

    // internal nodes: only use key and next  内部结点:至少有一个孩子的结点,与分支结点定义相同;
    // external nodes: only use key and value 外部结点:也即叶子结点;有趣的是,维基百科关于外部结点的定义特地标注了不统一;
    private static class Entry {
        private Comparable key;
        private Object value;
        private Node next;     // helper field to iterate over array entries
        public Entry(Comparable key, Object value, Node next) {
            this.key   = key;
            this.value = value;
            this.next  = next;
        }
    }

    // constructor
    public BTree() { root = new Node(0); }

    // return number of key-value pairs in the B-tree
    public int size() { return N; }

    // return height of B-tree
    public int height() { return HT; }


    // search for given key, return associated value; return null if no such key
    public Value get(Key key) { return search(root, key, HT); }
    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) return (Value) children[j].value;
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.m; j++) {
                if (j+1 == x.m || less(key, children[j+1].key))
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
    }


    // insert key-value pair
    // add code to check for duplicate keys
    public void put(Key key, Value value) {
        Node u = insert(root, key, value, HT); 
        N++;
        if (u == null) return;

        // need to split root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        HT++;
    }


    private Node insert(Node h, Key key, Value value, int ht) {
        int j;
        Entry t = new Entry(key, value, null);

        // external node
        if (ht == 0) {
            int numberOfChildren = h.m;
            for (j = 0; j < numberOfChildren; j++) {
                Entry child = h.children[j];
                if (less(key, child.key)) break;
            }
        }

        // internal node
        else {
            for (j = 0; j < h.m; j++) {
                if ((j+1 == h.m) || less(key, h.children[j+1].key)) {
                    Node u = insert(h.children[j++].next, key, value, ht-1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--) h.children[i] = h.children[i-1];
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null;
        else         return split(h);
    }

    // split node in half
    private Node split(Node h) {
        Node t = new Node(M/2);
        h.m = M/2;
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j]; 
        return t;    
    }

    // for debugging
    public String toString() {
        return toString(root, HT, "") + "\n";
    }
    private String toString(Node h, int ht, String indent) {
        String s = "";
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s += indent + children[j].key + " " + children[j].value + "\n";
            }
        }
        else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s += indent + "(" + children[j].key + ")\n";
                s += toString(children[j].next, ht-1, indent + "     ");
            }
        }
        return s;
    }


    // comparison functions - make Comparable instead of Key to avoid casts
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }


   /*************************************************************************
    *  test client
    *************************************************************************/
    public static void main(String[] args) {
//        BTree<String, String> st = new BTree<String, String>();

//      st.put("www.cs.princeton.edu", "128.112.136.12");
//        st.put("www.cs.princeton.edu", "128.112.136.11");
//        st.put("www.princeton.edu",    "128.112.128.15");
//        st.put("www.yale.edu",         "130.132.143.21");
//        st.put("www.simpsons.com",     "209.052.165.60");
//        st.put("www.apple.com",        "17.112.152.32");
//        st.put("www.amazon.com",       "207.171.182.16");
//        st.put("www.ebay.com",         "66.135.192.87");
//        st.put("www.cnn.com",          "64.236.16.20");
//        st.put("www.google.com",       "216.239.41.99");
//        st.put("www.nytimes.com",      "199.239.136.200");
//        st.put("www.microsoft.com",    "207.126.99.140");
//        st.put("www.dell.com",         "143.166.224.230");
//        st.put("www.slashdot.org",     "66.35.250.151");
//        st.put("www.espn.com",         "199.181.135.201");
//        st.put("www.weather.com",      "63.111.66.11");
//        st.put("www.yahoo.com",        "216.109.118.65");
//
//
//        System.out.println("cs.princeton.edu:  " + st.get("www.cs.princeton.edu"));
//        System.out.println("hardvardsucks.com: " + st.get("www.harvardsucks.com"));
//        System.out.println("simpsons.com:      " + st.get("www.simpsons.com"));
//        System.out.println("apple.com:         " + st.get("www.apple.com"));
//        System.out.println("ebay.com:          " + st.get("www.ebay.com"));
//        System.out.println("dell.com:          " + st.get("www.dell.com"));
//        System.out.println();

        BTree<Integer, Integer> st = new BTree<Integer, Integer>();
        System.out.println("root:"+st);
        st.put(18,18);
        st.put(40,40);
        st.put(50,50);
        st.put(70,70);
        st.put(22,22);
        st.put(23,23);
        st.put(25,25);
        st.put(29,29);

        System.out.println("size:    " + st.size());
        System.out.println("height:  " + st.height());
        System.out.println(st);
        System.out.println();

    }

}