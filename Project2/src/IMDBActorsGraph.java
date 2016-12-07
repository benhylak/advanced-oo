import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class IMDBActorsGraph implements Graph
{
	HashMap<String, ActorNode> nodeRecord; 
	GraphParser p;

	public IMDBActorsGraph()
	{
		nodeRecord = new HashMap<String, ActorNode>();
	}

	public IMDBActorsGraph(String actorsPath, String actressesPath) throws IOException
	{
		p = new GraphParser();

		p.parseFile(actorsPath);
		p.parseFile(actressesPath);

		this.nodeRecord = p.getActorGraph().nodeRecord;
	}
	
	@Override
	public ActorNode getNodeByName(String name) 
	{
		return nodeRecord.get(name);
	}

	public void addNode(ActorNode n) 
	{
		nodeRecord.put(n.getName(), n);
	}

	@Override
	public Collection<? extends Node> getNodes()
	{
		return nodeRecord.values();
	}
}
