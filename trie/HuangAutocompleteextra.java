package trie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.AbstractMap.SimpleEntry;

public class HuangAutocompleteextra extends Trie<List<Integer>> implements IAutocomplete<List<Integer>> {
	
	Entry<String,Integer> freq1 = new SimpleEntry<String,Integer>(null, null);
	Entry<String,Integer> freq2 = new SimpleEntry<String,Integer>(null, null);
	List<String> listnew = new ArrayList<String>();
	Boolean a = false;
	@Override
	public void pickCandidate(String prefix, String candidate) {
		
		// Renew the frequency in dictionary trie 
			List<Integer> a = new ArrayList<Integer>();
			a.add(1);
			TrieNode<List<Integer>> b = this.find(candidate);
			int freq = 1;
			if( b != null){
					List<Integer> c = b.getValue();
					if(c != null){
						c.add(0,c.get(0)+1);
						put(candidate,c);
						freq = c.get(0)+1;
					}	
					else
						put(candidate,a);
				}
			renewfreq(checkiffreq(freq,candidate),candidate,freq);
		}

	public void renewfreq(int i,String candidate,int freq){
		if(i == 1){
			freq2 = new SimpleEntry<String,Integer>(freq1);
			freq1 = new SimpleEntry<String,Integer>(candidate,freq);
		}
		else if( i == 2){
			freq2 = new SimpleEntry<String,Integer>(candidate,freq);
		}
		else if( i == 3)
			freq1 = new SimpleEntry<String,Integer>(candidate,freq);
	}
	
	public int checkiffreq(int f,String candidate){
		
		
		if(freq2.getValue() != null){
			if(freq2.getKey().equals(candidate))
				return 2;
			if(freq1.getKey().equals(candidate)) 
				return 3;
			if( freq2.getValue() >= f)
				return 0;
			else if(freq1.getValue() >= f)
					return 2;
			else
				return 1;
		}
		else if (freq2.getValue() == null && freq1.getValue() != null){
			if(freq1.getKey().equals(candidate)) 
				return 3;
			if(freq1.getValue() >= f)
				return 2;
			else
				return 1;
		}
		else
			return 1;
	}
	@Override
	public List<String> getCandidates(String prefix){
		if(!a){
			System.out.println("*****It displayed the most frequent two items on the top*****");
			a=true;
		}
		TrieNode<List<Integer>> node = this.find(prefix);//find the node contains that last character in prefix
		if(node == null) 
		{
			System.out.println("The prefix does not exist.");
			System.exit(-1);
		}
		List<String> list = new ArrayList<>();
		Queue<Entry<TrieNode<List<Integer>>,String>> p = new LinkedList<>();
		p.add(new SimpleEntry<>(node,prefix));
		while(!p.isEmpty())
		{
			Entry<TrieNode<List<Integer>>,String> e = p.poll();
			if(e.getKey().isEndState() && list.contains(e.getValue()));
			{
				list.add(e.getValue());
				if(list.size() >= 20)
					break;
			}
			List<Entry<Character,TrieNode<List<Integer>>>> children = new ArrayList<>(node.getChildrenMap().entrySet());

			for(Entry<Character,TrieNode<List<Integer>>> g: children)
				p.add(new SimpleEntry<>(g.getValue(),e.getValue()+g.getKey()));	
		}
		
		//reordering the list
		Boolean b1 = false, b2 = false;
		List<String> listold = new ArrayList<String>(list);
		listnew = new ArrayList<String>();
		listnew.add(0,null);
		listnew.add(1,null);
		for(String b: list)
		{
			if(b.equals(freq1.getKey())){
				listnew.remove(0);
				listnew.add(0,b);
				listold.remove(b);
				b1 = true;
				}
			if(b.equals(freq2.getKey())){
				listnew.remove(1);
				listnew.add(1,b);
				listold.remove(b);
					b2 = true;
				}
		}
		if(!b2 ){
			if(freq2.getKey() != null){
			listnew.remove(1);
			listnew.add(1,freq2.getKey());
			}else
				listnew.remove(1);
		}
		if(!b1){
			listnew.remove(0);
			if(freq1.getKey()!= null)
				listnew.add(0,freq1.getKey());
		}	
			
		
			
		for(String b: listold)
		{
			listnew.add(b);
		}
			return listnew;
	}
}
