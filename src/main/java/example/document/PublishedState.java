package example.document;

final class PublishedState implements DocumentState {
    @Override public Document.Status status() { return Document.Status.PUBLISHED; }

    @Override public void archive(Document document) {
        document.transitionTo(new ArchivedState());
    }
}
