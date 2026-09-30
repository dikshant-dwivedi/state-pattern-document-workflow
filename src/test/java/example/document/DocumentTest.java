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
        checkEveryInvalidActionIsRejected();
        System.out.println("All checks passed");
    }

    private static void checkEveryInvalidActionIsRejected() {
        Document draft = new Document("draft", "text");
        expectInvalid(draft::approve);
        expectInvalid(draft::reject);
        expectInvalid(draft::archive);
        expectInvalid(draft::restore);

        Document review = new Document("review", "text");
        review.submit();
        expectInvalid(() -> review.edit("changed"));
        expectInvalid(review::submit);
        expectInvalid(review::archive);
        expectInvalid(review::restore);

        Document published = new Document("published", "text");
        published.submit();
        published.approve();
        expectInvalid(() -> published.edit("changed"));
        expectInvalid(published::submit);
        expectInvalid(published::approve);
        expectInvalid(published::reject);
        expectInvalid(published::restore);

        Document archived = new Document("archived", "text");
        archived.submit();
        archived.approve();
        archived.archive();
        expectInvalid(() -> archived.edit("changed"));
        expectInvalid(archived::submit);
        expectInvalid(archived::approve);
        expectInvalid(archived::reject);
        expectInvalid(archived::archive);
        check(archived.status() == Document.Status.ARCHIVED, "invalid actions never change state");
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
