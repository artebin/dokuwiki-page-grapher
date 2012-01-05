package net.trevize.dokuwiki.pagegrapher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.apache.commons.collections15.Transformer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.io.GraphMLWriter;


public class PageGrapher {

	public static void main(String[] args) {
		/*
		 * Get all the page with the DokuWiki index. 
		 */

		Document doc = null;
		try {
			doc = Jsoup.connect("http://localhost/~nicolas/njwiki/?do=index")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements wikilink1_links = doc.select("a[href].wikilink1");

		/*
		 * Build a DirectedSpaceGraph and add a vertex per page in the index.
		 */

		DirectedSparseGraph<DokuWikiVertex, DokuWikiEdge> graph = new DirectedSparseGraph<DokuWikiVertex, DokuWikiEdge>();
		HashMap<String, DokuWikiVertex> vertices = new HashMap<String, DokuWikiVertex>();

		int id = 0;
		for (Element e : wikilink1_links) {
			//System.out.println(e.getElementsByAttribute("href"));

			String url = "http://localhost/" + e.attr("href").trim();
			String title = e.attr("title").trim();
			DokuWikiVertex vertex = new DokuWikiVertex("" + id++,
					DokuWikiVertex.VERTEX_TYPE_PAGE, url, title);
			graph.addVertex(vertex);
			vertices.put(title, vertex);
		}

		/*
		 * for all vertices, get the related page, parse it and search for
		 * wikilink1 links, create edges and add them to the graph.
		 */

		for (DokuWikiVertex v_source : vertices.values()) {
			try {
				doc = Jsoup.connect(v_source.getUrl()).get();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			wikilink1_links = doc.select("a[href].wikilink1");

			for (Element e : wikilink1_links) {
				String title = e.attr("title").trim();
				DokuWikiVertex v_target = vertices.get(title);

				if (v_target != null) {
					DokuWikiEdge edge = new DokuWikiEdge(
							DokuWikiEdge.LINK_TYPE_WIKILINK1);
					graph.addEdge(edge, v_source, v_target);
				} else {
					System.out.println("Unknown internal link: "
							+ e.getElementsByAttribute("href"));
				}
			}
		}

		//		JFrame f = new JFrame();
		//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		f.getContentPane().add(new SatelliteView(graph));
		//		f.pack();
		//		f.setVisible(true);
		PageGrapher.saveGraphInGraphML(graph, "dokuwiki.graphml.xml");
	}

	public static void saveGraphInGraphML(
			DirectedSparseGraph<DokuWikiVertex, DokuWikiEdge> g,
			String graphml_path) {

		GraphMLWriter<DokuWikiVertex, DokuWikiEdge> graphml_writer = new GraphMLWriter<DokuWikiVertex, DokuWikiEdge>();

		graphml_writer.setVertexIDs(new Transformer<DokuWikiVertex, String>() {
			@Override
			public String transform(DokuWikiVertex arg0) {
				return arg0.getId();
			}
		});

		graphml_writer.addVertexData(
				DokuWikiVertex.VERTEX_PROPERTY_VERTEX_TYPE, "", "",
				new Transformer<DokuWikiVertex, String>() {
					@Override
					public String transform(DokuWikiVertex arg0) {
						return arg0.getVertex_type();
					}
				});

		graphml_writer.addVertexData(DokuWikiVertex.VERTEX_PROPERTY_VERTEX_URL,
				"", "", new Transformer<DokuWikiVertex, String>() {
					@Override
					public String transform(DokuWikiVertex arg0) {
						return arg0.getUrl();
					}
				});

		graphml_writer.addVertexData(
				DokuWikiVertex.VERTEX_PROPERTY_VERTEX_TITLE, "", "",
				new Transformer<DokuWikiVertex, String>() {
					@Override
					public String transform(DokuWikiVertex arg0) {
						return arg0.getTitle();
					}
				});

		try {
			graphml_writer.save(g, new PrintWriter(graphml_path));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
