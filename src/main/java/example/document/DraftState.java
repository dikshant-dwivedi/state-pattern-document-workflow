package example.document;

final class DraftState implements DocumentState {
    @Override public Document.Status status() { return Document.Status.DRAFT; }

    @Override public void edit(Document document, String newContent) {
        document.replaceContent(newContent);
    }
}
