package net.trevize.dokuwiki.pagegrapher;

public class DokuWikiVertex {

	public static final String VERTEX_PROPERTY_VERTEX_TYPE = "VERTEX_PROPERTY_VERTEX_TYPE";
	public static final String VERTEX_PROPERTY_VERTEX_URL = "VERTEX_PROPERTY_VERTEX_URL";
	public static final String VERTEX_PROPERTY_VERTEX_TITLE = "VERTEX_PROPERTY_VERTEX_TITLE";

	public static final String VERTEX_TYPE_PAGE = "VERTEX_TYPE_PAGE";
	public static final String VERTEX_TYPE_NAMESPACE = "VERTEX_TYPE_NAMESPACE";

	private String id;
	private String vertex_type;
	private String url;
	private String title;

	public DokuWikiVertex(String id, String vertex_type, String url,
			String title) {
		this.id = id;
		this.vertex_type = vertex_type;
		this.url = url;
		this.title = title;
	}

	public String getVertex_type() {
		return vertex_type;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public void setVertex_type(String vertex_type) {
		this.vertex_type = vertex_type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
