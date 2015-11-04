package trie;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class Trie<T>
{
	private TrieNode<T> n_root;
	
	public Trie()
	{
		n_root = new TrieNode<>(null, (char)0);
	}
	
	public TrieNode<T> getRoot()
	{
		return n_root;
	}
	
	public T get(String key)
	{
		TrieNode<T> node = find(key);
		return (node != null && node.isEndState()) ? node.getValue() : null;
	}
	
	public boolean contains(String key)
	{
		return get(key) != null;
	}
	
	/** @return the previously inserted value for the specific key if exists; otherwise, {@code null}. */
	public T put(String key, T value)
	{
		char[] array = key.toCharArray();
		int i, len = key.length();
		TrieNode<T> node = n_root;
		
		for (i=0; i<len; i++)
			node = node.addChild(array[i]); // if find return the child, if not find create a new branch

		node.setEndState(true);
		return node.setValue(value);
	}
	
	/** @return the node that contains the last character in the key; otherwise, {@code null}. */
	public TrieNode<T> find(String key)
	{
		char[] array = key.toCharArray();
		int i, len = key.length();
		TrieNode<T> node = n_root;
		for (i=0; i<len; i++)
		{
			node = node.getChild(array[i]);//get the child contains the key array[i]
			if (node == null) return null;//if not contain the whole prefix
		}
		
		return node; 	
	}
	
	/** @return {@code true} if a node with the specific key if exists; otherwise, {@code false}. */
	public boolean remove(String key)
	{
		TrieNode<T> node = find(key);
		
		if (node == null || !node.isEndState())
			return false;
		
		if (node.hasChildren())
		{
			node.setEndState(false);
			return true;
		}

		TrieNode<T> parent = node.getParent();

		while (parent != null)
		{
			parent.removeChild(node.getKey());
			
			if (parent.hasChildren() || parent.isEndState())
				break;
			else
			{
				node   = parent;
				parent = parent.getParent();
			}	
		}
		
		return true;
	}
}
