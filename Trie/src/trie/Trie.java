package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
    
    // prevent instantiation
    private Trie() { }
    
    /**
     * Builds a trie by inserting all words in the input array, one at a time,
     * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
     * The words in the input array are all lower case.
     * 
     * @param allWords Input array of words (lowercase) to be inserted.
     * @return Root of trie with all words inserted from the input array
     */
    public static TrieNode buildTrie(String[] allWords) {
        /** COMPLETE THIS METHOD **/
        
        // FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
        // MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
        if(allWords.length == 0)
            return null;
        TrieNode root = new TrieNode(null, null, null);
        root.firstChild = new TrieNode(new Indexes(0, (short)0, (short)(allWords[0].length() -1)), null, null);

        for (int i = 1; i < allWords.length; i++){
            insert(root, allWords[i], allWords, i);
        }
        return root;
    }

    private static int prefixLength(String a, String b) {
        int count = 0;
        for (int i = 0; i < Math.min(a.length(), b.length()); i++) {
            if(a.charAt(i) != b.charAt(i)) {
                return count;
            }
            count++;
        }
        return count;
    }

    private static void insert(TrieNode root, String word, String[] allWords, int curIndex) {
        TrieNode prev = root;
        TrieNode cur = prev.firstChild;
        TrieNode prefixNode = null;
        Boolean insert = false;
        Boolean child = true;

        while(cur != null) {
            String a = allWords[cur.substr.wordIndex].substring(0, cur.substr.endIndex+1);
            String b = word;
            
            int currentPrefixLength = prefixLength(a, b);
            
            if(currentPrefixLength == 0) {
                prev = cur;
                cur = cur.sibling;
                child = false;
                continue;
            }

            if(currentPrefixLength-1 == cur.substr.endIndex) {
                prev = cur;
                cur = cur.firstChild;
                child = true;
                prefixNode = prev;
                        
            } else {
                if(prefixNode == null) {
                    Indexes prefixIndex = new Indexes(cur.substr.wordIndex, (short) 0, (short) (currentPrefixLength-1));
                    Indexes newNodeIndex = new Indexes(curIndex, (short) currentPrefixLength, (short) (allWords[curIndex].length() - 1));
                    Indexes newCurIndex = new Indexes(cur.substr.wordIndex, (short) currentPrefixLength, (short) cur.substr.endIndex);
                    TrieNode newNode = new TrieNode(newNodeIndex, null, null);
                    cur = new TrieNode(newCurIndex, cur.firstChild, cur.sibling);
                    TrieNode newPrefix = new TrieNode(prefixIndex, cur, cur.sibling);
                    cur.sibling = newNode;
                    if(!child)
                        prev.sibling = newPrefix;
                    else {
                        prev.firstChild = newPrefix;
                    }
                    insert = true;
                    break;
                } else {
                    if(currentPrefixLength-1 != prefixNode.substr.endIndex) {
                        Indexes prefixIndex = new Indexes(cur.substr.wordIndex, (short) (prefixNode.substr.endIndex + 1), (short) (currentPrefixLength - 1));
                        Indexes newNodeIndex = new Indexes(curIndex, (short) currentPrefixLength, (short) (allWords[curIndex].length() - 1));
                        Indexes newCurIndex = new Indexes(cur.substr.wordIndex, (short) currentPrefixLength, (short) cur.substr.endIndex);
                        
                        TrieNode newNode = new TrieNode(newNodeIndex, null, null);
                        cur = new TrieNode(newCurIndex, cur.firstChild, cur.sibling);
                        TrieNode newPrefix = new TrieNode( prefixIndex, cur, cur.sibling);
                        cur.sibling = newNode;
                        if(!child) {
                            prev.sibling = newPrefix;
                        } else {
                            prev.firstChild = newPrefix;
                        }
                        insert = true;
                        break;
                        
                    } else {
                        prev = cur;
                        cur = cur.sibling;
                        child = false;
                    }
                }
            }

        }
        
        if(!insert) {
            if(prefixNode == null) {
                TrieNode newWord = new TrieNode(new Indexes(curIndex, (short) 0, (short) (allWords[curIndex].length() - 1)), null, null);
                prev.sibling = newWord;
            } else {
                TrieNode newWord = new TrieNode(new Indexes(curIndex, (short) (prefixNode.substr.endIndex+1), (short) (allWords[curIndex].length() - 1)), null, null);
                prev.sibling = newWord;
            }
        }
    }
    
    /**
     * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
     * trie whose words start with this prefix. 
     * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
     * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
     * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
     * and for prefix "bell", completion would be the leaf node that holds "bell". 
     * (The last example shows that an input prefix can be an entire word.) 
     * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
     * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
     *
     * @param root Root of Trie that stores all words to search on for completion lists
     * @param allWords Array of words that have been inserted into the trie
     * @param prefix Prefix to be completed with words in trie
     * @return List of all leaf nodes in trie that hold words that start with the prefix, 
     *          order of leaf nodes does not matter.
     *         If there is no word in the tree that has this prefix, null is returned.
     */
    public static ArrayList<TrieNode> completionList(TrieNode root,
                                        String[] allWords, String prefix) {
        ArrayList<TrieNode> list = new ArrayList<>();
        if (prefix.length()==0)
            return findNodes(root, allWords, prefix);
        if (root.substr==null) {
            if (root.firstChild == null)
            	return null;
            return completionList(root.firstChild, allWords, prefix);
        }
        
        String word = allWords[root.substr.wordIndex];
        if (prefix.equals(word.substring(0, Math.min(root.substr.endIndex+1, prefix.length())))) {
        	if (root.firstChild!=null) {
                list.addAll(findNodes(root.firstChild, allWords, prefix));
            } else {
                list.add(root);
            }
        }
        else if (prefix.length()-1 > root.substr.endIndex && prefix.substring(0, root.substr.endIndex+1).equals(word.substring(0, root.substr.endIndex+1))){
        	if (root.firstChild==null)
        		return null;
        	
        	return completionList (root.firstChild, allWords, prefix);
        } else {
        	if (root.sibling==null)
                return null;
        	
        	return completionList(root.sibling, allWords, prefix);
        }
        return list;
        
    }
    
    private static ArrayList<TrieNode> findNodes (TrieNode cur, String[] allWords, String prefix) {

        ArrayList<TrieNode> nodes = new ArrayList<>();
        if (cur.sibling != null) {
            nodes.addAll(findNodes(cur.sibling, allWords, prefix));
        }
        if (cur.firstChild != null) {
            nodes.addAll(findNodes(cur.firstChild, allWords, prefix));
        } else {
        	nodes.add(cur);
            return nodes;
        }
        return nodes;
    }
    
    public static void print(TrieNode root, String[] allWords) {
        System.out.println("\nTRIE\n");
        print(root, 1, allWords);
    }
    
    private static void print(TrieNode root, int indent, String[] words) {
        if (root == null) {
            return;
        }
        for (int i=0; i < indent-1; i++) {
            System.out.print("    ");
        }
        
        if (root.substr != null) {
            String pre = words[root.substr.wordIndex]
                            .substring(0, root.substr.endIndex+1);
            System.out.println("      " + pre);
        }
        
        for (int i=0; i < indent-1; i++) {
            System.out.print("    ");
        }
        System.out.print(" ---");
        if (root.substr == null) {
            System.out.println("root");
        } else {
            System.out.println(root.substr);
        }
        
        for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
            for (int i=0; i < indent-1; i++) {
                System.out.print("    ");
            }
            System.out.println("     |");
            print(ptr, indent+1, words);
        }
    }
 }