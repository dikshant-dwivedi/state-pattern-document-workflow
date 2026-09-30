package example.document;

final class ArchivedState implements DocumentState {
    @Override public Document.Status status() { return Document.Status.ARCHIVED; }
}
