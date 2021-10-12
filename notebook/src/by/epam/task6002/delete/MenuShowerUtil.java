package by.epam.task6002.delete;

public class MenuShowerUtil {
    static final String INDENT = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    private static final String NOTEBOOK =
            "------------------------------- NOTEBOOK -------------------------------";
    private static final String ADDING_NOTE =
            "------------------------------- ADDING NOTE -------------------------------";
    private static final String SEARCHING_NOTE =
            "------------------------------ SEARCHING NOTE ------------------------------";


    public static void showMainMenu() {
        System.out.println(INDENT + NOTEBOOK + "\n");
        System.out.println(" \"Add\" - \"1\"");
        System.out.println(" \"View\" - \"2\"");
        System.out.println(" \"Search\" - \"3\"\n");
        System.out.println(" \"Exit\" - \"0\"\n");
        System.out.print("Enter here: ");
    }

    public static void showTitleOfAddingNote() {
        System.out.println(INDENT + ADDING_NOTE);
    }

    public static void showExtraMenuOfAddingNote() {
        System.out.println("\n \"Add more\" - \"1\"");
        System.out.println(" Enter any other symbol to return\n");
        System.out.print("Enter here: ");
    }

    public static void showMenuOfSearchingNote() {
        System.out.println(INDENT + SEARCHING_NOTE);
        System.out.println(" - Search options - ");
        System.out.println(" \"By id\" - \"1\"");
        System.out.println(" \"By subject\" - \"2\"");
        System.out.println(" \"By email\" - \"3\"");
        System.out.println(" \"By date of creation\" - \"4\"");
        System.out.println(" \"By text\" - \"5\"");
        System.out.print("\n Enter option number or any other symbol to return in main menu: ");
    }

    public static void showExtraMenuOfSearchingNote() {
        System.out.println("\n - Select more options:\n");
        System.out.println(" \"By id\" - \"1\"");
        System.out.println(" \"By subject\" - \"2\"");
        System.out.println(" \"By email\" - \"3\"");
        System.out.println(" \"By date of creation\" - \"4\"");
        System.out.println(" \"By text\" - \"5\"");
        System.out.print("\n Enter option number or any other symbol to return in main menu: ");
    }

    public static void showMenuOfSortingNote() {
        System.out.println(" Sort:  \"id↑\" - \"1\" | \"id↓\" - \"01\" | \"subject[A-Z]\" - \"2\" | \"subject[Z-A]\" - \"02\"");
        System.out.println(" \"date↑\" - \"3\" | \"date↓\" - \"03\" | \"email[A-Z]\" - \"4\" | \"email[Z-A]\" - \"04\"");
        System.out.println(" \"text[A-Z]\" - \"5\" | \"text[Z-A]\" - \"05\"");
        System.out.print("\n Enter option number or any other symbol to return: ");
    }
}
