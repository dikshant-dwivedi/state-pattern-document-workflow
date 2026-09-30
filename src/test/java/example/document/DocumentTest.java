package example.document;

public final class DocumentTest {
    public static void main(String[] args) {
        Document document = new Document("State pattern", "First draft");
        check(document.status() == Document.Status.DRAFT, "starts as a draft");
        document.submit();
        check(document.status() == Document.Status.IN_REVIEW, "submit begins review");
        document.approve();
        check(document.status() == Document.Status.PUBLISHED, "approve publishes");
        expectInvalid(document::submit);
        System.out.println("All checks passed");
    }

    static void check(boolean condition, String description) {
        if (!condition) throw new AssertionError(description);
    }

    static void expectInvalid(Runnable action) {
        try {
            action.run();
            throw new AssertionError("Expected an invalid action to fail");
        } catch (IllegalStateException expected) {
            // Correct: the workflow rejected the action.
        }
    }
}
