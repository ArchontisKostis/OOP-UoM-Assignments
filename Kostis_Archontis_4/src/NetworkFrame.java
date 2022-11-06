import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.algorithms.shortestpath.ShortestPathUtils;

public class NetworkFrame extends JFrame{
	private Registry registry;
	private UndirectedSparseGraph<String, String> graph;
	private JTextField diameterField;
	private JPanel mainPanel;
	private VisualizationImageServer<String, String> vs;
	
	private ImageIcon icon = new ImageIcon("logo.png");
	
	public NetworkFrame(Registry aRegistry) {
		this.registry = aRegistry;
		
		// Graphic Components creation
		this.mainPanel = new JPanel();
		this.graph = new UndirectedSparseGraph<String, String>();
		
		this.diameterField = new JTextField("Dimension: ");
		this.diameterField.setEditable(false);
		this.diameterField.setBackground(Color.WHITE);
		
		// Graph Creation and calculation of its diameter
		this.vs = this.createNetworkGraph();
		double max = this.findGraphDiameter();
		
		this.diameterField.setText("Diameter: " + max);
		
		// Create Border Layout Manager for panel
		BorderLayout border = new BorderLayout();
		this.mainPanel.setLayout(border);
		
		// Add Graphic Components to main panel
		this.mainPanel.add(vs, BorderLayout.CENTER);
		this.mainPanel.add(diameterField, BorderLayout.PAGE_END);
		
		this.setContentPane(mainPanel);
		
		// Create Window
		this.setResizable(false);
		this.setVisible(false);
		this.setSize(500, 500);
		this.setTitle("Suspects Network");
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	private double findGraphDiameter() {
		// Find Diameter
		int max = graph.getNeighbors(registry.suspects.get(0).getCodeName()).size();
		for(Suspect suspect: registry.suspects) {
			int size = graph.getNeighbors(suspect.getCodeName()).size();
				if(size>max)
					max = size;
		}
		return max;
	}
	
	private VisualizationImageServer<String, String> createNetworkGraph() {
		int i = 0;
		for(Suspect suspect: this.registry.suspects) {
			this.graph.addVertex(suspect.getCodeName());
			i++;
			for(Suspect partner: suspect.getPartnersList()) {
				if(!this.graph.containsEdge("Edge " + i))
					this.graph.addEdge("Edge " + i, suspect.getCodeName(), partner.getCodeName());
			}
		}
		
		 VisualizationImageServer<String, String> vs =
			      new VisualizationImageServer<String, String>(
			        new CircleLayout<String, String>(graph), new Dimension(400, 400)
			       );
		 vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
		
		 // return the visualized graph
		 return vs;
	}
	
}
