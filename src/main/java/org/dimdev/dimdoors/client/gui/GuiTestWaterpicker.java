package org.dimdev.dimdoors.client.gui;

import com.teamwizardry.librarianlib.features.gui.component.GuiComponent;
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents;
import com.teamwizardry.librarianlib.features.gui.layers.TextLayer;
import com.teamwizardry.librarianlib.features.gui.layout.Flexbox;
import com.teamwizardry.librarianlib.features.gui.provided.pastry.components.PastryButton;
import com.teamwizardry.librarianlib.features.gui.provided.pastry.windows.PastryWindow;
import com.teamwizardry.librarianlib.features.gui.windows.GuiWindow;
import com.teamwizardry.librarianlib.features.math.Cardinal2d;
import com.teamwizardry.librarianlib.features.math.Vec2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.dimdev.dimdoors.shared.sound.ModSounds;

import java.util.function.Consumer;

public class GuiTestWaterpicker extends PastryWindow {
    private Flexbox flex;

    public GuiTestWaterpicker(int width, int height) {
        super(width, height, Style.PANEL, false);
        // Style.DEFAULT = MC rounded style
        // panel = square w/ subtle bevel
        // dialog = rounded w/ subtle bevel and no hard border between header/content (just a little grey line in the middle)

        this.flex = new Flexbox(0, 0, 0, 0, Cardinal2d.POSITIVE_Y);
        flex.setJustifyContent(Flexbox.Justify.START);

        flex.add(new ListItem("Neat"));
        flex.add(new ListItem("is a"));
        flex.add(new ListItem("mod by"));
        flex.add(new ListItem("Vazkii"));

        this.getContent().add(flex);
    }

    @Override
    public void layoutChildren() {
        super.layoutChildren();
        flex.setFrame(getContent().getBounds());
    }

    private static class ListItem extends GuiComponent {
        private String text;
        private TextLayer label = new TextLayer(0, 0, 0, 16);
        private PastryButton button;

        public ListItem(String text) {
            super(0, 0, 0, 16);
            this.text = text;
            label.setText(text);

            this.button = new PastryButton("Do " + text, 0, 0);
            this.button.BUS.hook(GuiComponentEvents.MouseClickEvent.class, (event) -> {
                YourPanel newWindow = new YourPanel(this.text, (newText) -> {
                    this.text = newText;
                    this.label.setText(newText);
                });

                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(ModSounds.RIFT, 1.0F));
                newWindow.open();
            });
            this.button.setAnchor(new Vec2d(1, 0));
            Flexbox.Data flexData = new Flexbox.Data(0,
                    null,
                    0, 0,
                    0, 2,
                    16, 16,
                    0, 0,
                    0, Integer.MAX_VALUE,
                    null, null
            );
            this.setData(Flexbox.Data.class, flexData);

            this.add(label, button);
        }

        @Override
        public void layoutChildren() {
            super.layoutChildren();
            label.setFrame(this.getBounds());
            button.setPos(new Vec2d(this.getWidth(), 0));
        }
    }

    private static class YourPanel extends PastryWindow {
        private Consumer<String> callback;
        private TextLayer label;

        public YourPanel(String text, Consumer<String> callback) {
            super(100, 100, Style.PANEL, true);
            this.callback = callback;

            this.label = new TextLayer(0, 0, 0, 0);
            this.label.setText(text);
            this.label.fitToText();

            this.setMinContentSize(this.getContentSize());
            this.setMaxContentSize(this.getContentSize());

            this.BUS.hook(GuiWindow.CloseEvent.class, (event) -> {
                callback.accept(text + " run");
            });

            this.getContent().add(label);
        }
    }
}