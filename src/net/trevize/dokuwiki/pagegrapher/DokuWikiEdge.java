package net.trevize.dokuwiki.pagegrapher;

public class DokuWikiEdge {

	public static final String LINK_TYPE_WIKILINK1 = "LINK_TYPE_WIKILINK1";
	public static final String LINK_TYPE_IDX_DIR = "LINK_TYPE_IDX_DIR";

	private String link_type;

	public DokuWikiEdge(String link_type) {
		this.link_type = link_type;
	}

	public String getLink_type() {
		return link_type;
	}

	public void setLink_type(String link_type) {
		this.link_type = link_type;
	}

}
