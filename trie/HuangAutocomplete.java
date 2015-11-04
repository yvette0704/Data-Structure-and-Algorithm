package trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.AbstractMap.SimpleEntry;

public class HuangAutocomplete extends Trie<List<String>> implements IAutocomplete<List<String>> {
	
	Map<String,String[]> current = new HashMap<String,String[]>();
	List<String> listnew = new ArrayList<String>();

	@Override
	public void pickCandidate(String prefix, String candidate) {
		// TODO Auto-generated method stub
		
			if(!listnew.contains(candidate)){
				this.put(candidate, null);
			}

			if(current.containsKey(prefix) && !candidate.equals(current.get(prefix)[0])){
				String[] a = {candidate,current.get(prefix)[0]};
				current.put(prefix,a);	
			}
			else
			{
				String[] a = {candidate,null};
				current.put(prefix,a);
			}
	}

	@Override
	public List<String> getCandidates(String prefix){
		TrieNode<List<String>> node = this.find(prefix);//find the node contains that last character in prefix
		if(node == null) 
		{
			System.out.println("The prefix does not exist.");
			System.exit(-1);
		}
		List<String> list = new ArrayList<>();
		Queue<Entry<TrieNode<List<String>>,String>> p = new LinkedList<>();
		p.add(new SimpleEntry<>(node,prefix));
		while(!p.isEmpty())
		{
			Entry<TrieNode<List<String>>,String> e = p.poll();
			if(e.getKey().isEndState() && list.contains(e.getValue()));
			{
				list.add(e.getValue());	
				if(list.size() >= 20)
					break;
			}
			List<Entry<Character,TrieNode<List<String>>>> children = new ArrayList<>(node.getChildrenMap().entrySet());

			for(Entry<Character,TrieNode<List<String>>> g: children)
				p.add(new SimpleEntry<>(g.getValue(),e.getValue()+g.getKey()));	
		}
		
		//reordering the list
		Boolean b1 = false, b2 = false;
		List<String> listold = new ArrayList<String>(list);
		if(current.containsKey(prefix)){
			listnew = new ArrayList<String>();
			listnew.add(0,null);
			listnew.add(1,null);
			for(String b: list)
			{
				if(b.equals(current.get(prefix)[0])){
					listnew.remove(0);
					listnew.add(0,b);
					listold.remove(b);
					b1 = true;
				}
				if(b.equals(current.get(prefix)[1])){
					listnew.remove(1);
					listnew.add(1,b);
					listold.remove(b);
					b2 = true;
				}
			}
			
			if(!b1){
				listnew.remove(0);
				listnew.add(0,current.get(prefix)[0]);
			}	
			
			if(!b2 && current.get(prefix)[1] != null){
				listnew.remove(1);
				listnew.add(1,current.get(prefix)[1]);
			}else if(!b2)
				listnew.remove(1);
			
			for(String b: listold)
			{
				listnew.add(b);
			}
			return listnew;
		}
		return list;
	}
}
