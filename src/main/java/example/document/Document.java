package example.document;

public final class Document {
    public enum Status { DRAFT, IN_REVIEW, PUBLISHED }

    private final String title;
    private String content;
    private Status status = Status.DRAFT;

    public Document(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String title() { return title; }
    public String content() { return content; }
    public Status status() { return status; }

    public void submit() {
        if (status != Status.DRAFT) {
            throw new IllegalStateException("Only a draft can be submitted");
        }
        status = Status.IN_REVIEW;
    }

    public void approve() {
        if (status != Status.IN_REVIEW) {
            throw new IllegalStateException("Only a document in review can be approved");
        }
        status = Status.PUBLISHED;
    }
}
