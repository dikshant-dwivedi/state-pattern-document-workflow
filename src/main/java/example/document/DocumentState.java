package example.document;

interface DocumentState {
    Document.Status status();

    default void edit(Document document, String newContent) {
        throw new IllegalStateException("Cannot edit a document in " + status());
    }
}
