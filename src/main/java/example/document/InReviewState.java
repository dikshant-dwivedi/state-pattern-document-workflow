package example.document;

final class InReviewState implements DocumentState {
    @Override public Document.Status status() { return Document.Status.IN_REVIEW; }

    @Override public void approve(Document document) {
        document.transitionTo(new PublishedState());
    }

    @Override public void reject(Document document) {
        document.transitionTo(new DraftState());
    }
}
