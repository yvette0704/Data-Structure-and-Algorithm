package trie;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import utils.StringUtils;


public class RunAutocomplete
{
	private IAutocomplete<?> t_auto;
	
	public RunAutocomplete()
	{
		t_auto = new HuangAutocomplete();
	}
	
	public void putDictionary(InputStream in) throws Exception
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		
		while ((line = reader.readLine()) != null)
			t_auto.put(line.trim(), null);
	}
	
	public void run() throws Exception
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		List<String> candidates;
		String prefix, pick;
		
		do 
		{
			System.out.print("Enter a prefix: ");
			prefix = reader.readLine();
			
			// TODO: print out the top 10 candidates
			candidates = t_auto.getCandidates(prefix);
			System.out.println(StringUtils.join(candidates, "\n"));
			
			System.out.print("Pick: ");
			pick = reader.readLine();
			
			// TODO: update your Trie with this pick.
			t_auto.pickCandidate(prefix, pick);
			System.out.println("\""+pick+"\" is learned.\n");
		}
		while (true);
	}
	
	static public void main(String[] args) throws Exception
	{
		String dictFile = "dict.txt";
		RunAutocomplete tr = new RunAutocomplete();
		
		tr.putDictionary(new FileInputStream(dictFile));
		tr.run();
	}
}
