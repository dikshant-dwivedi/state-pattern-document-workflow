package example.document;

public final class Demo {
    public static void main(String[] args) {
        Document document = new Document("State pattern", "Rough notes");
        show("created", document);
        document.edit("A clearer draft");
        document.submit();
        show("submitted", document);
        document.reject();
        show("rejected", document);
        document.submit();
        document.approve();
        show("approved", document);
        document.archive();
        show("archived", document);
        document.restore();
        show("restored", document);
    }

    private static void show(String action, Document document) {
        System.out.printf("%-10s -> %-10s | %s%n", action, document.status(), document.content());
    }
}
