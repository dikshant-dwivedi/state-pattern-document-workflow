package example.document;

public final class Document {
    public enum Status { DRAFT, IN_REVIEW, PUBLISHED, ARCHIVED }

    private final String title;
    private String content;
    private DocumentState state = new DraftState();

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String title() { return title; }
    public String content() { return content; }
    public Status status() { return state.status(); }

    void transitionTo(DocumentState nextState) { state = nextState; }
    void replaceContent(String newContent) { content = newContent; }

    public void edit(String newContent) {
        state.edit(this, newContent);
    }

    public void submit() {
        state.submit(this);
    }

    public void approve() {
        state.approve(this);
    }

    public void reject() {
        state.reject(this);
    }

    public void archive() {
        state.archive(this);
    }

    public void restore() {
        state.restore(this);
    }
}
