package trie;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class TrieNode<T>
{
	private Map<Character,TrieNode<T>> m_children;
	private TrieNode<T> n_parent;
	private boolean b_endState;
	private char c_key;
	private T t_value;
	
	public TrieNode(TrieNode<T> parent, char key)
	{
		m_children = new HashMap<Character,TrieNode<T>>();
		setEndState(false);
		setParent(parent);
		setKey(key);
		setValue(null);
	}
	
//	============================== Getters ==============================
	public TrieNode<T> getParent()
	{
		return n_parent;
	}
	
	public char getKey()
	{
		return c_key;
	}
	
	public T getValue()
	{
		return t_value;
	}
	
	/** @return the map whose keys and values are children's characters and nodes. */
	public Map<Character,TrieNode<T>> getChildrenMap()
	{
		return m_children;
	}
	
	public TrieNode<T> getChild(char key)
	{
		return m_children.get(key);
	}
	
//	============================== Setters ==============================
	public void setParent(TrieNode<T> node)
	{
		n_parent = node;
	}
	
	public void setKey(char key)
	{
		c_key = key;
	}
	
	public T setValue(T value)
	{
		T tmp = t_value;
		t_value = value;
		return tmp;
	}
	
	public void setEndState(boolean isEndState)
	{
		b_endState = isEndState;
	}
	
	public TrieNode<T> addChild(char key)
	{
		TrieNode<T> child = getChild(key);
		
		if (child == null)
		{
			child = new TrieNode<T>(this, key);
			m_children.put(key, child);
		}
		
		return child;
	}
	
//	============================== Checks ==============================
	/** @return {@code true}} if this node is an end state; otherwise, {@code false}. */
	public boolean isEndState()
	{
		return b_endState;
	}
	
	public boolean hasValue()
	{
		return t_value != null;
	}
	
	public boolean hasChildren()
	{
		return !m_children.isEmpty();
	}
	
//	=================================================================	
	public TrieNode<T> removeChild(char key)
	{
		return m_children.remove(key);
	}
}
