package annotations;

//@RunMe
public class UnitUnderTest {
//    @RunMe
    private String name;

    @RunMe(text="This is the text")
    private void tryThis() {
        System.out.println("Running try this..");
    }

    public void dontDoThis() {
        System.out.println("oops, shouldn't be run");
    }

//    @RunMe/*(text="This is the text on doDoThis...")*/
    @RunMe("This is a value")
    public void doDoThis() {
        System.out.println("running dodo");
    }
}
