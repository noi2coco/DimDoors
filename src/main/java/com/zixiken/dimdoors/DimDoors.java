package com.zixiken.dimdoors;

import com.zixiken.dimdoors.client.controlpanel.Component;
import com.zixiken.dimdoors.client.controlpanel.ComponentRegistry;
import com.zixiken.dimdoors.client.controlpanel.ControlPanelRenderManager;
import com.zixiken.dimdoors.client.controlpanel.components.DefaultConsoleComponent;
import com.zixiken.dimdoors.shared.commands.PocketCommand;
import com.zixiken.dimdoors.shared.commands.TeleportCommand;
import com.zixiken.dimdoors.shared.DDConfig;
import com.zixiken.dimdoors.shared.DDProxyCommon;
import com.zixiken.dimdoors.shared.PocketRegistry;
import com.zixiken.dimdoors.shared.items.ModItems;
import com.zixiken.dimdoors.shared.RiftRegistry;
import com.zixiken.dimdoors.shared.SchematicHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import static org.lwjgl.input.Keyboard.*;

@Mod(modid = DimDoors.MODID, name = DimDoors.NAME, version = DimDoors.VERSION)
public class DimDoors {

    public static final String VERSION = "${version}";
    public static final String MODID = "dimdoors";
    public static final String NAME = "Dimensional Doors";

    @SidedProxy(clientSide = "com.zixiken.dimdoors.client.DDProxyClient",
            serverSide = "com.zixiken.dimdoors.server.DDProxyServer")
    public static DDProxyCommon proxy;

    @Mod.Instance(DimDoors.MODID)
    public static DimDoors instance;

    public static CreativeTabs dimDoorsCreativeTab = new CreativeTabs("dimDoorsCreativeTab") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return ModItems.itemDimDoor;
        }
    };


    //The following is stuff for the control panel windows
    private final KeyBinding controlPanelKeyBinding = new KeyBinding("Window Control Key", KEY_GRAVE, DimDoors.NAME);
    private final HashMap<Integer, List<Consumer<Integer>>> controlPanelKeyRegistry = new HashMap<>();
    private final HashMap<Integer, List<Consumer<Integer>>> controlPanelMouseRegistry = new HashMap<>();
    private boolean controlPanelActive = false;
    private int eventButton;
    private long lastMouseEvent;
    private Component lastComponent;
    //endcontrol panel section

    @Mod.EventHandler
    public void onPreInitialization(FMLPreInitializationEvent event) {
        proxy.onPreInitialization(event);
        DDConfig.loadConfig(event);
    }

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {
        proxy.onInitialization(event);
        MinecraftForge.EVENT_BUS.register(ControlPanelRenderManager.getInstance());
        MinecraftForge.EVENT_BUS.register(this);
        ClientRegistry.registerKeyBinding(this.controlPanelKeyBinding);
        ComponentRegistry.getInstance().addComponents(new DefaultConsoleComponent()); // Probably should go in PostInit...
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        registerCommands(event);
        RiftRegistry.INSTANCE.reset();
        PocketRegistry.INSTANCE.reset();
        SchematicHandler.INSTANCE.loadSchematics();
    }

    private void registerCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(new TeleportCommand());
        event.registerServerCommand(new PocketCommand());
        //@todo event.registerServerCommand( new DDCommand() ); //to register commands that this mod offers?
    }

    public static boolean isClient() {
        return proxy.isClient();
    }

    public static boolean isServer() {
        return !isClient();
    }

    public static World getDefWorld() {
        return proxy.getDefWorld(); //gets the server or client world dim 0 handler
    }

    public static void chat(EntityPlayer player, String text) {
        player.sendMessage(new TextComponentString("[DimDoors] " + text));
    }

    public static void warn(Class classFiredFrom, String text) {
        FMLLog.warning("[DimDoors] " + text + " (" + classFiredFrom.toString() + " )", 0);
    }

    public static void log(Class classFiredFrom, String text) {
        FMLLog.info("[DimDoors] " + text + " (" + classFiredFrom.toString() + " )", 0);
    }

    public static void translateAndAdd(String key, List<String> list) {
        for (int i = 0; i < 10; i++) {
            if (I18n.canTranslate(key + Integer.toString(i))) {
                String line = I18n.translateToLocal(key + Integer.toString(i));
                list.add(line);
            } else {
                break;
            }
        }
    }

    //Next sections are for handling control panel stuff

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (this.controlPanelKeyBinding.isPressed()) {
            if (!this.controlPanelActive) {
                Minecraft.getMinecraft().setIngameNotInFocus();
                this.controlPanelActive = true;
                KeyBinding.unPressAllKeys();
            }
        }
    }

    @SubscribeEvent
    public void onTickEvent(final TickEvent event) {
        if (this.controlPanelActive) {
            while (Mouse.next()) {
                final Point point = calculateMouseLocation();
                final Component basePart = ComponentRegistry.getInstance().getComponentUnder(point.x, point.y);

                if (basePart != null) {
                    this.lastComponent = basePart;
                }

                if (this.lastComponent != null) {
                    this.lastComponent.mouseMoved(point.x - this.lastComponent.getPositionX(), point.y - this.lastComponent.getPositionY());
                }

                int k = Mouse.getEventButton();

                if (Mouse.getEventButtonState()) {
                    this.eventButton = k;
                    this.lastMouseEvent = Minecraft.getSystemTime();
                    if (this.lastComponent != null) {
                        ComponentRegistry.getInstance().getComponentList().use(this.lastComponent);
                        this.lastComponent.mouseClicked(point.x - this.lastComponent.getPositionX(), point.y - this.lastComponent.getPositionY(), k);
                    }
                } else if (k != -1) {
                    this.eventButton = -1;
                    if (this.lastComponent != null) {
                        this.lastComponent.mouseReleased(point.x - this.lastComponent.getPositionX(), point.y - this.lastComponent.getPositionY(), k);
                    }
                    this.lastComponent = null;
                } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
                    long l = Minecraft.getSystemTime() - this.lastMouseEvent;
                    if (l > 100 && this.lastComponent != null) {
                        this.lastComponent.mouseDrag(point.x - this.lastComponent.getPositionX(), point.y - this.lastComponent.getPositionY(), this.eventButton);
                    }
                }
            }
            while (Keyboard.next()) {
                if (Keyboard.isKeyDown(Keyboard.getEventKey())) {
                    if (Keyboard.getEventKey() == KEY_ESCAPE || Keyboard.getEventKey() == this.controlPanelKeyBinding.getKeyCode()) {
                        this.controlPanelActive = false;
                        Minecraft.getMinecraft().setIngameFocus();
                        KeyBinding.unPressAllKeys();
                    } else if (Keyboard.getEventKey() == KEY_BACKSLASH) {
                        if (Keyboard.isKeyDown(KEY_RSHIFT)) {
                            //DUBUG for refreshinglists and such
                        }
                    } else {
                        if (this.controlPanelKeyRegistry.containsKey(Keyboard.getEventKey())) {
                            this.controlPanelKeyRegistry.get(Keyboard.getEventKey()).iterator().forEachRemaining(con -> con.accept(Keyboard.getEventKey()));
                        }
                    }
                }
            }
        }
    }

    public boolean isControlPanelActive() {
        return controlPanelActive;
    }

    //End controlpanel stuff

    public static Point calculateMouseLocation() {
        Minecraft minecraft = Minecraft.getMinecraft();
        int scale = minecraft.gameSettings.guiScale;
        if (scale == 0)
            scale = 1000;
        int scaleFactor = 0;
        while (scaleFactor < scale && minecraft.displayWidth / (scaleFactor + 1) >= 320 && minecraft.displayHeight / (scaleFactor + 1) >= 240) {
            scaleFactor++;
        }
        return new Point(Mouse.getX() / scaleFactor, minecraft.displayHeight / scaleFactor - Mouse.getY() / scaleFactor - 1);
    }
}
