package example.document;

interface DocumentState {
    Document.Status status();

    default void edit(Document document, String newContent) {
        throw new IllegalStateException("Cannot edit a document in " + status());
    }

    default void submit(Document document) {
        throw new IllegalStateException("Cannot submit a document in " + status());
    }

    default void approve(Document document) {
        throw new IllegalStateException("Cannot approve a document in " + status());
    }

    default void reject(Document document) {
        throw new IllegalStateException("Cannot reject a document in " + status());
    }

    default void archive(Document document) {
        throw new IllegalStateException("Cannot archive a document in " + status());
    }

    default void restore(Document document) {
        throw new IllegalStateException("Cannot restore a document in " + status());
    }
}
