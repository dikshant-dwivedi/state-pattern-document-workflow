package example.document;

public final class DocumentTest {
    public static void main(String[] args) {
        Document document = new Document("State pattern", "First draft");
        check(document.status() == Document.Status.DRAFT, "starts as a draft");
        document.edit("Revised draft");
        check(document.content().equals("Revised draft"), "draft can be edited");
        document.submit();
        check(document.status() == Document.Status.IN_REVIEW, "submit begins review");
        expectInvalid(() -> document.edit("Too late"));
        document.reject();
        check(document.status() == Document.Status.DRAFT, "rejection returns to draft");
        document.submit();
        document.approve();
        check(document.status() == Document.Status.PUBLISHED, "approve publishes");
        expectInvalid(() -> document.edit("Too late"));
        expectInvalid(document::reject);
        expectInvalid(document::submit);
        document.archive();
        check(document.status() == Document.Status.ARCHIVED, "archive changes the state");
        expectInvalid(() -> document.edit("An archived document must be read-only"));
        expectInvalid(document::reject);
        document.restore();
        check(document.status() == Document.Status.DRAFT, "restore returns to draft");
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
