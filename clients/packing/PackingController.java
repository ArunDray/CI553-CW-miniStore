package clients.packing;

/**
 * The Packing Controller
 */
public class PackingController {
    private PackingModel model;
    private PackingView view;

    /**
     * Constructor
     *
     * @param model The model
     * @param view  The view from which the interaction came
     */
    public PackingController(PackingModel model, PackingView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    /**
     * Returns the PackingModel instance associated with this controller.
     *
     * @return The PackingModel instance.
     */
    public PackingModel getPackingModel() {
        return model;
    }

    /**
     * Handles the packed action.
     */
    public void doPacked() {
        model.doPacked();
    }
}
