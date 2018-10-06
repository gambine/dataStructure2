import java.util.ArrayList;
import java.util.List;


public class Assignment implements PrefixMap {

	//TODO implement a nested Node class for your linked tree structure
	private int keyNum;
	private Node root;
	private int index;
	private class Node {
		
		Node[] children;
		String value;
		
		public Node() {
			
			children = new Node[4];
			value = null;
		}
		
	}
	/*
	 * The default constructor will be called by the tests on Ed
	 */
	public Assignment() {
		// TODO Initialise your data structure here
//		root = new Node();
//		root.parent = null;
//		root.children = new Node[4];
//		root.value = null;
	    keyNum = 0;
	    index = 0;
	    
		
	}

	@Override
	public int size() {
		
		return keyNum;
	}

	@Override
	public boolean isEmpty() {
		// TODO Implement this, then remove this comment
		if(keyNum == 0){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public int getIndex(char ch){
		if(ch == 'A'){
			return 0;
		}
		else if(ch == 'C'){
			return 1;
		}
		else if(ch == 'G'){
			return 2;
		}
		else if(ch == 'T'){
			return 3;
		}
		else{
			return 4;
		}
		
	}
	
	@Override
	public String get(String key) {
		if(key == null){
			throw new IllegalArgumentException();
		}
		if(!key.matches("[ACGT]+")){
			throw new MalformedKeyException();
		}
		Node currentNode = root;
		int charIndex = 0;
		while(currentNode != null && charIndex <  key.length()){
			int childIndex = getIndex(key.charAt(charIndex));
			if(childIndex<0 || childIndex >= 4){
				return null;
			}
			currentNode = currentNode.children[childIndex];
			if(currentNode == null){
				return null;
			}
			charIndex = charIndex + 1;
			
		}
//		if(currentNode !=null){
//			return currentNode.value;
//		}
		
		return currentNode.value;
	}

	@Override
	public String put(String key, String value) {
		
	
		if(key == null||value == null){
			throw new IllegalArgumentException();
		}
		if(!key.matches("[ACGT]+")){
			throw new MalformedKeyException();
			
		}
		
		
		Node currentNode = root;
		int charIndex = 0;
		while(charIndex<key.length()){
			int childIndex = getIndex(key.charAt(charIndex));
			if(childIndex<0 || childIndex >= 4){
				return null;
			}
			if(currentNode.children[childIndex] == null){
				currentNode.children[childIndex] = new Node();
			}
			currentNode = currentNode.children[childIndex];
			charIndex = charIndex + 1;
		}
		currentNode.value = value;
		
		return currentNode.value;
	}

	@Override
	public String remove(String key) {
		if(key == null){
			throw new IllegalArgumentException();
		}
		if(!key.matches("[ACGT]+")){
			throw new MalformedKeyException();
		}
		return remove(key, root, key.length(), 0);
	}
	private boolean removable(String key, Node currentNode, int length, int charIndex){
		
		if(currentNode == null){
			return false;
		}
		if(charIndex == length){
			for(int i = 0;i < currentNode.children.length;i++){
				if(currentNode.children[i] == null){
					return true;
					
				}
				else{
					return false;
				}
			}
		}
		return false;
	}
	
	private String remove(String key, Node currentNode, int length, int charIndex) {
		if(key == null){
			throw new IllegalArgumentException();
		}
		if(!key.matches("[ACGT]+")){
			throw new MalformedKeyException();
		}
		
		
		while(currentNode != null && charIndex <  key.length()){
			int childIndex = getIndex(key.charAt(charIndex));
			if(childIndex<0 || childIndex >= key.length()){
				return null;
			}
			currentNode = currentNode.children[childIndex];
			charIndex = charIndex + 1;
			
		}
		if (currentNode == null){
			return null;
		}
		if(charIndex == length){
			for(int i = 0;i < currentNode.children.length;i++){
				if(currentNode.children[i] == null){
					currentNode = null;
					
				}
				else{
					currentNode.value = null;
				}
			}
		}
		else{
			Node childNode = currentNode.children[getIndex(key.charAt(charIndex))];
			boolean childRemovable = removable(key, childNode, key.length(), charIndex + 1);
			if(childRemovable){
				currentNode.children[getIndex(key.charAt(charIndex))] = null;
				for (int i = 0; i < currentNode.children.length; i++){
			            if ((currentNode.children[i]) == null){
			            	currentNode = null;
			            }
				}
			}	
		}
		
		return currentNode.value;
	}
	

	@Override
	public int countKeysMatchingPrefix(String prefix) {
		if(prefix == null){
			throw new IllegalArgumentException();
		}
		int count = 0;
		
		Node currentNode = root;
		int charIndex = 0;
		while(currentNode != null && charIndex <  prefix.length()){
			int childIndex = getIndex(prefix.charAt(charIndex));
			if(childIndex<0 || childIndex >= prefix.length()){
				return 0;
			}
			currentNode = currentNode.children[getIndex(prefix.charAt(charIndex))];
			charIndex = charIndex + 1;
			if(currentNode == null){
				return 0;
			}
		}
		
		return countKeysMatchingPrefix(currentNode);
	}
	
	private int countKeysMatchingPrefix(Node node){
		if(node == null){
			return 0;
		}
		int count = 0;
		if(node.value != null){
			count++;
		}
		count = count+ 
				countKeysMatchingPrefix(node.children[0])+
				countKeysMatchingPrefix(node.children[1])+
				countKeysMatchingPrefix(node.children[2])+
				countKeysMatchingPrefix(node.children[3]);
		return count;
	}

	@Override
	public List<String> getKeysMatchingPrefix(String prefix) {
		
		if(prefix == null){
			throw new IllegalArgumentException();
		}
		
		
		Node currentNode = root;
		int charIndex = 0;
		String ch = "";
		ch = ch + prefix;
		while(currentNode != null && charIndex <  prefix.length()){
			int childIndex = getIndex(prefix.charAt(charIndex));
			if(childIndex<0 || childIndex >= prefix.length()){
				return null;
			}
			currentNode = currentNode.children[getIndex(prefix.charAt(charIndex))];
			charIndex = charIndex + 1;
			if(currentNode == null){
				return null;
			}
			
		}
		
		return getKeysMatchingPrefix(currentNode, ch);
	}
	private List<String> getKeysMatchingPrefix(Node node, String ch){
		ArrayList<String> list = new ArrayList<String>();
		if(node.children[0].value != null){
			list.add(ch + "A");
		}
		if(node.children[1].value != null){
			list.add(ch + "C");
		}
		if(node.children[2].value != null){
			list.add(ch + "G");
		}
		if(node.children[3].value != null){
			list.add(ch + "T");
		}
		list.addAll(getKeysMatchingPrefix(node.children[0], ch + "A"));
		list.addAll(getKeysMatchingPrefix(node.children[1], ch + "C"));
		list.addAll(getKeysMatchingPrefix(node.children[2], ch + "G"));
		list.addAll(getKeysMatchingPrefix(node.children[3], ch + "T"));
		
		return list;
	}

	@Override
	public int countPrefixes() {
		// TODO Implement this, then remove this comment
		
		Node currentNode = root;
		int count = 0;
		int charIndex = 0;	
		
		return countPrefixes(currentNode, count, charIndex);
	}
	private int countPrefixes(Node currentNode, int count, int charIndex) {
		if(charIndex < 4){
			if(currentNode == null){
				
				return count;
			}
			else{
				
				count++;
				currentNode = currentNode.children[charIndex];
				charIndex++;
			}
			
		}
		else{
			return count-1;
		}
		return count+countPrefixes(currentNode, count, charIndex);
	}	
	@Override
	public int sumKeyLengths() {
		
		return 0;
	}
	
	
}

