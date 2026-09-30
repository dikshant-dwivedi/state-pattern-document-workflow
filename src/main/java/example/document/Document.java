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
        if (status() != Status.DRAFT) {
            throw new IllegalStateException("Only a draft can be submitted");
        }
        transitionTo(new InReviewState());
    }

    public void approve() {
        if (status() != Status.IN_REVIEW) {
            throw new IllegalStateException("Only a document in review can be approved");
        }
        transitionTo(new PublishedState());
    }

    public void reject() {
        if (status() == Status.DRAFT) {
            throw new IllegalStateException("A draft is not in review");
        } else if (status() == Status.IN_REVIEW) {
            transitionTo(new DraftState());
        } else if (status() == Status.PUBLISHED) {
            throw new IllegalStateException("A published document cannot be rejected");
        } else if (status() == Status.ARCHIVED) {
            throw new IllegalStateException("An archived document cannot be rejected");
        }
    }

    public void archive() {
        if (status() != Status.PUBLISHED) {
            throw new IllegalStateException("Only a published document can be archived");
        }
        transitionTo(new ArchivedState());
    }

    public void restore() {
        if (status() != Status.ARCHIVED) {
            throw new IllegalStateException("Only an archived document can be restored");
        }
        transitionTo(new DraftState());
    }
}
