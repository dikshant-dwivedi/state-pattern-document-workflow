package example.document;

final class InReviewState implements DocumentState {
    @Override public Document.Status status() { return Document.Status.IN_REVIEW; }
}
