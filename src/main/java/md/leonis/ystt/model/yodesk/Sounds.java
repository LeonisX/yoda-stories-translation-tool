package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.sounds.PrefixedStrz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This section declares sounds used in the game. The actual audio data is
 * stored in wav files on the disk (in a directory named `sfx`) so this
 * section contains paths to each sound file.
 * Sounds can be referenced from the scripting language (see `play_sound`
 * instruction opcode below) and from weapon (see `character` structure).
 * Some sound ids (like the one when the hero is hit, or can't leave a
 * zone) are hard coded in the game.
 */
public class Sounds extends KaitaiStruct {

    private short count;
    private List<PrefixedStrz> prefixedSounds;

    private final transient Yodesk root;
    private final transient CatalogEntry parent;

    public static Sounds fromFile(String fileName) throws IOException {
        return new Sounds(new ByteBufferKaitaiInputStream(fileName));
    }

    public Sounds(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Sounds(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Sounds(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.count = (short) -this.io.readS2le();
        prefixedSounds = new ArrayList<>(((Number) count).intValue());
        for (int i = 0; i < count; i++) {
            PrefixedStrz prefixedStrz = new PrefixedStrz(this.io, this, root);
            prefixedSounds.add(prefixedStrz);
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeS2le((short) -count);
        prefixedSounds.forEach(s -> s.write(os));
    }

    public List<String> getSounds() {
        return prefixedSounds.stream().map(PrefixedStrz::getContent).collect(Collectors.toList());
    }

    public short getCount() {
        return count;
    }

    public List<PrefixedStrz> getPrefixedSounds() {
        return prefixedSounds;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
