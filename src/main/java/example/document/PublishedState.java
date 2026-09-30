package example.document;

final class PublishedState implements DocumentState {
    @Override public Document.Status status() { return Document.Status.PUBLISHED; }
}
