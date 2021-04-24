package md.leonis.ystt.model;

import md.leonis.bin.ByteOrder;
import md.leonis.bin.Dump;
import md.leonis.ystt.utils.BinaryUtils;
import md.leonis.ystt.utils.Config;
import md.leonis.ystt.utils.PaletteUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static md.leonis.ystt.utils.Config.gamePalette;

public class Section {

    // ShortInt                 -128..127                                   byte        8
    // Byte                     0..255;                                     int/char    8
    // SmallInt                 -32768..32767;                              int         16
    // Word                     0...65535                                   int/char    16
    // Integer, LongInt         -2147483648...2147483647                    int         32
    // Cardinal, LongWord       0..4294967295;                              long        32
    // Int64                    -9223372036854775808..9223372036854775807   long        64

    public static final String[] PLANETS = {"", "desert", "snow", "forest", "unknown", "swamp"};

    //TODO read palette from EXE

    //TODO getters
    public Map<KnownSections, SectionMetrics> sections = new HashMap<>();
    //TODO dtaDump
    public Dump dump;
    public Dump exeDump;
    public boolean[] tiles;
    //TODO zonesList
    public List<Zone> maps = new ArrayList<>();
    //public Map<Integer, Zone> maps = new HashMap<>();
    public String exeCrc32;
    public String dtaCrc32;
    public String dtaRevision;
    public String version;
    public byte soundsCount;
    public List<String> sounds = new ArrayList<>();
    public int tilesCount;
    //TODO zonesCount, or delete, we have list and size
    public int mapsCount;
    public int puzzlesCount;
    public int charsCount;
    public int namesCount;

    public Section() {
    }

    public String GetTileFlagS(int id) {
        SetPosition(GetDataOffset(KnownSections.TILE) + id * 0x404);
        return dump.getLongWordS();
    }

    public long GetTileFlag(int id) {
        SetPosition(GetDataOffset(KnownSections.TILE) + id * 0x404);
        return ReadLongWord();
    }

    public void SetTileFlag(int id, long flag) {
        SetPosition(GetDataOffset(KnownSections.TILE) + id * 0x404);
        WriteLongWord(flag);
    }

    //TODO do we need this?
    public void clear() {

        sections = new HashMap<>();
        maps = new ArrayList<>();
        //maps = new HashMap<>();
        tiles = new boolean[0];
        version = "";
        soundsCount = 0;
        sounds = new ArrayList<>();
        tilesCount = 0;
        mapsCount = 0;
        puzzlesCount = 0;
        charsCount = 0;
        namesCount = 0;
    }

    public void Add(KnownSections section, int dataSize, int fullSize, int dataOffset, int startOffset) {
        sections.put(section, new SectionMetrics(section, dataSize, fullSize, startOffset, dataOffset));
    }

    public int GetStartOffset(KnownSections section) {
        return sections.get(section).getStartOffset();
    }

    public int GetDataOffset(KnownSections section) {
        return sections.get(section).getDataOffset();
    }

    public int GetDataSize(KnownSections section) {
        return sections.get(section).getDataSize();
    }

    public int GetFullSize(KnownSections section) {
        return sections.get(section).getFullSize();
    }

    public boolean Have(KnownSections section) {
        return sections.containsKey(section);
    }

    public int GetSize() {
        return dump.size();
    }

    public boolean InBound(KnownSections section) {
        return dump.getIndex() < GetDataOffset(section) + GetDataSize(section);
    }

    public void readDTAMetricks() {

        clear();

        SetPosition(0);
        boolean keepReading = true;
        while (keepReading) {
            //TODO
            //Application.ProcessMessages;
            String s = ReadString(4);
            Log.debug(s);
            try {
                KnownSections sections = KnownSections.valueOf(s);
                switch (sections) {
                    case VERS:
                        ScanVERS(sections); //версия файла
                        break;
                    case STUP:
                        ScanSTUP(sections); //чтение титульной картинки
                        break;
                    case SNDS:
                        ScanSNDS(sections); //SNDS, 4 байта размер блока, C0FF, размер названия сэмпла+$0, размер названия сэмпла+$0,... пока не надо
                        break;
                    case TILE:
                        ScanTILE(sections); //тайлы
                        break;
                    case ZONE:
                        ScanZONE(sections); //локации
                        break;
                    case PUZ2:
                        ScanPUZ2(sections); //ещё диалоги
                        break;
                    case CHAR:
                        ScanCHAR(sections); //персонажи?
                        break;
                    case CHWP:
                        ScanCHWP(sections); //персонажи?????
                        break;
                    case CAUX:
                        ScanCAUX(sections); //персонажи?????
                        break;
                    case TNAM:
                        ScanTNAM(sections); //названия+тайлы
                        break;
                    case TGEN:
                        ScanTGEN(sections); //встречается в германском образе. Что такое - не знаю.
                        break;
                    case ENDF:
                        Add(sections, 4, 8, dump.getIndex(), dump.getIndex() - 4);
                        keepReading = false;
                        break;
                }
            } catch (IllegalArgumentException e) {
                showMessage("Неизвестная секция: 0x" + intToHex(GetPosition(), 4) + ": \"" + s + '"');
                keepReading = false;
            } catch (Exception e) {
                Log.error(e.getClass().getName() + " " + e.getMessage());
                e.printStackTrace();
                keepReading = false;
            }
        }
        Log.newLine();
        Log.debug("Sections detailed:");
        Log.debug("------------------");
        Log.newLine();
        Log.debug(String.format("%7s %12s %11s %12s %11s", "Section", "Data offset", "Data size", "Start offset", "Full size"));

        sections.forEach((key, value) -> Log.debug(String.format("%7s %12x %11d %12x %11d",
                key,
                value.getDataOffset(),
                value.getDataSize(),
                value.getStartOffset(),
                value.getFullSize()
        )));

        Log.newLine();
        Log.debug("Maps offsets, sizes detailed:");
        Log.debug("------------------");
        Log.newLine();
        Log.debug(String.format("%3s   %-13s %-13s  %-16s  %-13s  %-13s  %-13s  %-13s  %-13s", "#", "MAP", "IZON", "OIE", "IZAX", "ISX2", "IZX3", "IZX4", "IACT"));

        maps.forEach(value -> Log.debug(String.format("%3d   %-13s %-13s  %-16s  %-13s  %-13s  %-13s  %-13s  %-13s", value.getId(),
                intToHex(value.getPosition(), 6) + ':' + intToHex(value.getSize(), 4),
                intToHex(value.getIzon().getPosition(), 6) + ':' + intToHex(value.getIzon().getSize(), 4),
                intToHex(value.getOie().getPosition(), 6) + ':' + intToHex(value.getOie().getSize(), 4) + ':' + intToHex(value.getOie().getCount(), 2),
                intToHex(value.getIzax().getPosition(), 6) + ':' + intToHex(value.getIzax().getSize(), 4),
                intToHex(value.getIzx2().getPosition(), 6) + ':' + intToHex(value.getIzx2().getSize(), 4),
                intToHex(value.getIzx3().getPosition(), 6) + ':' + intToHex(value.getIzx3().getSize(), 4),
                intToHex(value.getIzx4().getPosition(), 6) + ':' + intToHex(value.getIzx4().getSize(), 4),
                intToHex(value.getIactPosition(), 6) + ':' + intToHex(value.getIactSize(), 4)
        )));
    }

    public void ScanTGEN(KnownSections sectionName) {

        int sz = (int) ReadLongWord();            //4 байта - длина блока TGEN
        //Add(sectionName, 4 + sz, index);
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        Log.debug(sectionName + " - what is it?...");
    }

    // 246 * 26 + size(4) + 'TNAM' + $FFFF = 6406
    public void ScanTNAM(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section TNAM
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        //MoveIndex(sz);
        int count = 0;
        while (InBound(sectionName)) {
            if (ReadWord() == 0xFFFF) {//2 байта - номер персонажа (тайла)
                break;
            }
            ReadString(24);             //24 bytes - rest of current name length
            count++;
        }
        namesCount = count;
        Log.debug(sectionName + ": " + count);
    }

    // 77 * 4 = 308 + size(4) + 'CAUX' + FFFF = 318
    public void ScanCAUX(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section CAUX
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        int count = (sz - 2) / 4;
        Log.debug(sectionName + ": " + count);
    }

    // 77 * 6 = 462 + size(4) + 'CHWP' + FFFF = 472
    public void ScanCHWP(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section CHWP
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        int count = (sz - 2) / 6;
        Log.debug(sectionName + ": " + count);
    }

    // 78 Characters
    public void ScanCHAR(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section CHAR
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        charsCount = 0;
        while (InBound(sectionName)) {
            if (ReadWord() == 0xFFFF) {         //2 bytes - index of character
                break;
            }
            ReadString(4);              //4 bytes - 'ICHA'
            int csz = (int) ReadLongWord();        //4 bytes - rest of current character length
            ReadString(csz);
            charsCount++;
        }
        Log.debug("Characters: " + charsCount);
    }

    public void ScanPUZ2(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section PUZ2
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        puzzlesCount = 0;
        while (InBound(sectionName)) {
            if (ReadWord() == 0xFFFF) {    //2 bytes - index of puzzle (from 0)
                break;
            }
            ReadString(4);              //4 bytes - 'IPUZ'
            int psz = (int) ReadLongWord();        //4 bytes - rest of current puzzle length
            ReadString(psz);
            puzzlesCount++;
        }
        Log.debug("Puzzles: " + puzzlesCount);
    }

    public void ScanZONE(KnownSections sectionName) {

        //Signature: String[4];       // 4 bytes: "ZONE" - уже прочитано
        int ind = GetPosition();
        mapsCount = ReadWord();        // 2 bytes - maps count $0291 = 657 items
        // Next repeated data of TZone
        for (int i = 0; i < mapsCount; i++) {
            ReadWord();                //unknown:word;          01 00 - unknown 2 bytes
            int sz = (int) ReadLongWord();        //size:longword;         size of current map (4b)
            Log.debug(i + " Offset:Size: " + intToHex(GetPosition() - 4, 4) + ':' + intToHex(sz, 4));
            MovePosition(sz);
        }

        Add(sectionName, GetPosition() - ind, GetPosition() - ind + 4, ind, ind - 4);
        Log.debug("Maps (zones): " + mapsCount);
        //Log.NewLine();


        SetPosition(KnownSections.ZONE);   // ZONE
        ReadWord();                     // 2 bytes - maps count $0291 = 657 items
        showMessage("scan zone ok");
        for (int i = 0; i < mapsCount; i++) {
            ScanIZON(i);
        }
        Log.newLine();
    }

    public void ScanIZON(int id) {

        // Repeated data of TZone
        addZone(id);
        int uw = ReadWord();               // unknown:word; //01 00 // map type (desert, ...)
        if (uw > 0x0005) {
            showMessage("ID: " + id + " UNK: " + intToHex(uw, 4) + " > " + 0x0005);
        }
        int sz = (int) ReadLongWord();           // size:longword; size of the current map
        maps.get(id).setPosition(GetPosition());
        maps.get(id).setSize(sz + 6);
        int pn = ReadWord();               // number:word; //2 bytes - serial number of the map starting with 0
        if (pn != id) {
            showMessage("ID: " + pn + " <> " + id);
        }
        ReadString(4);                // izon:string[4]; //4 bytes: "IZON"
        int size = (int) ReadLongWord();         // longword; //4 bytes - size of block IZON (include 'IZON') until object info entry count
        maps.get(id).setIzon(new Izon(GetPosition(), size - 6));

        //TODO
        //Application.ProcessMessages;

        int w = ReadWord();                // width:word; //2 bytes: map width (W)
        int h = ReadWord();                // height:word; //2 bytes: map height (H)
        int flags = ReadWord();             // flags:word; //2 byte: map flags (unknown meanings)* добавил байт снизу
        long unk2 = ReadLongWord();         // unused:longword; //5 bytes: unused (same values for every map)
        int p = ReadWord();               // planet:word; //1 byte: planet (0x01 = desert, 0x02 = snow, 0x03 = forest, 0x05 = swamp)* добавил следующий байт

        Log.debug("Map #" + pn + ": " + PLANETS[p] + " (" + w + "x" + h + ")");
        Log.debug("Flags: " + flags + "; unknown value: 0x" + longToHex(unk2, 4));
        if (unk2 != 0xFFFF0000) {
            showMessage(longToHex(unk2, 8));
        }

        MovePosition(w * h * 6);

        int oieCount = ReadWord();         //2 bytes: object info entry count (X)
        maps.get(id).setOie(new Oie(GetPosition(), oieCount * 12, oieCount));
        MovePosition(oieCount * 12);     //X*12 bytes: object info data

        Log.debug("Object info entries count: " + oieCount);

        ScanIZAX(id);
        ScanIZX2(id);
        ScanIZX3(id);
        ScanIZX4(id);
        ScanIACT(id);
    }

    public void addZone(int id) {
        maps.add(new Zone(id));
        //maps.put(id, new Zone(id));
    }

    public void ScanIZAX(int id) {

        ReadString(4);                //4 bytes: "IZAX"
        int size = (int) ReadLongWord();         //4 bytes: length (X)
        maps.get(id).setIzax(new Izon(GetPosition(), size - 8));
        MovePosition(size - 8);          //X-8 bytes: IZAX data
    }

    public void ScanIZX2(int id) {

        ReadString(4);                //4 bytes: "IZX2"
        int size = (int) ReadLongWord();         //4 bytes: length (X)
        maps.get(id).setIzx2(new Izon(GetPosition(), size - 8));
        MovePosition(size - 8);          //X-8 bytes: IZX2 data
    }

    public void ScanIZX3(int id) {

        ReadString(4);                //4 bytes: "IZX3"
        int size = (int) ReadLongWord();         //4 bytes: length (X)
        maps.get(id).setIzx3(new Izon(GetPosition(), size - 8));
        MovePosition(size - 8);          //X-8 bytes: IZX3 data
    }

    public void ScanIZX4(int id) {

        ReadString(4);                //4 bytes: "IZX4"
        maps.get(id).setIzx4(new Izon(GetPosition(), 8));
        MovePosition(8);          //8 bytes: IZX4 data
    }

    public void ScanIACT(int id) {

        int idx = dump.getIndex();
        maps.get(id).setIactPosition(GetPosition());

        while (true) {
            //Log.debug(idx);
            String title = ReadString(4); //4 bytes: "IACT"
            if (title.equals("IACT")) {
                int position = GetPosition();
                int size = (int) ReadLongWord();   //4 bytes: length (X)
                maps.get(id).getIacts().add(new Izon(position, size));
                Log.debug(title + ' ' + intToHex(size, 4));
                MovePosition(size);
            } else {
                break;
            }
        }
        MovePosition(-4);
        maps.get(id).setIactSize(GetPosition() - idx);
    }

    public void ScanTILE(KnownSections sectionName) {

        int sz = (int) ReadLongWord();             //4 bytes - length of section TILE
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        tilesCount = sz / 0x404;
        tiles = new boolean[tilesCount];
        for (int i = 0; i < tilesCount; i++) {
            tiles[i] = false;
        }

        Log.debug("Sprites, tiles: " + tilesCount);
    }

    public void ScanSNDS(KnownSections sectionName) {

        int sz = (int) ReadLongWord();             //4 bytes - length of section SNDS
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        soundsCount = 0;
        ReadWord();
        while (InBound(sectionName)) {
            int msz = ReadWord(); // length
            sounds.add(ReadString(msz).trim());
            soundsCount++;
        }

        Log.debug("Sounds, melodies: " + soundsCount);
    }

    public void ScanSTUP(KnownSections sectionName) {

        int sz = (int) ReadLongWord();             //4 bytes - length of section STUP
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        Log.debug("Title screen: exists");
    }

    public void ScanVERS(KnownSections sectionName) {

        Add(sectionName, 4, 4 + 4, GetPosition(), GetPosition() - 4);
        long ver = dump.getLongWord();
        if (ver == 0x0200) {
            version = "2.0";
        } else {
            version = "x.x";
        }
        Log.debug("File version: " + version);
        System.out.println("vers");
    }

    public int GetIZON(int offset) {
        Log.debug(maps.size());
        for (int i = 0; i < 5; i++) {
            Log.debug("====izon #" + i + ": " + intToHex(maps.get(i).getIzon().getPosition(), 4));
        }

        int result = 0;
        for (int i = 0; i < mapsCount; i++) {
            Log.debug("-izon #" + i + ": " + intToHex(offset, 4) + '~' + intToHex(maps.get(i).getIzon().getPosition(), 4));
            if (offset < maps.get(i).getIzon().getPosition()) {
                break;
            }
            result = i;
        }
        return result;
    }

    public int GetIACT(int offset) {

        Log.debug("GetIACT. Offset: " + intToHex(offset, 4));
        int izon = GetIZON(offset);
        Log.debug("GetIZON: " + izon);
        int result = 0;
        for (int i = 0; i < maps.get(izon).getIacts().size(); i++) {
            Log.debug(i + ": " + intToHex(maps.get(izon).getIacts().get(i).getPosition(), 4));
            if (offset < maps.get(izon).getIacts().get(i).getPosition()) {
                break;
            }
            result = i;
        }

        Log.debug("IZON/IACT: " + izon + '/' + result);
        return result;
    }

    public void LoadFileToArray(File file) throws IOException {

        Log.debug("DTA file internal structure");
        Log.debug("===========================");
        Log.newLine();

        Path exePath = file.toPath();
        Path dtaPath = getDtaPath(exePath.getParent());

        exeCrc32 = intToHex(BinaryUtils.crc32(exePath), 8);
        dtaCrc32 = intToHex(BinaryUtils.crc32(dtaPath), 8);

        Release release = Config.releases.stream().filter(r -> r.getExe().equals(exeCrc32) && r.getDta().equals(dtaCrc32)).findFirst().orElse(null);

        if (null == release) {
            dtaRevision = "Unknown combination of files";
        } else {
            dtaRevision = release.getTitle();
        }

        exeDump = new Dump(exePath);
        exeDump.setByteOrder(ByteOrder.LITTLE_ENDIAN);

        int paletteStartIndex = exeDump.findAddress(PaletteUtils.getBGRASample(gamePalette));

        if (paletteStartIndex < 0) {
            throw new RuntimeException("Palette isn't found in EXE file");
        }

        //TODO const
        boolean otherPaletteLocation = (0x550F0 != paletteStartIndex);

        Log.newLine();
        Log.debug("EXE:");
        Log.debug("---------");
        Log.newLine();
        Log.debug("Size: " + exeDump.size());
        Log.debug("CRC-32: " + exeCrc32);
        Log.debug("DTA revision: " + dtaRevision);
        Log.debug("Palette address: " + intToHex(paletteStartIndex, 6));
        exeDump.setIndex(0);
        if (otherPaletteLocation) {
            Log.debug("Unknown palette location. Standard is: 0x550F0");
        }

        dumpPalette(paletteStartIndex);

        dump = new Dump(dtaPath);
        dump.setByteOrder(ByteOrder.LITTLE_ENDIAN);

        Log.newLine();
        Log.debug("Sections:");
        Log.debug("---------");
        Log.newLine();
        Log.debug("Size: " + dump.size());
        Log.debug("CRC-32: " + dtaCrc32);
        Log.debug("DTA revision: " + dtaRevision);
        SetPosition(0);
    }

    private void dumpPalette(int paletteStartIndex) {

        gamePalette = PaletteUtils.dumpBGRAPalette(exeDump.getDump(), paletteStartIndex);
        Config.updatePalette();
    }

    public Path getDtaPath(Path dir) throws IOException {

        try (Stream<Path> stream = Files.list(dir)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.getFileName().toString().toLowerCase().endsWith(".dta"))
                    .findFirst().orElse(null);
        }
    }

    public void SaveToFile(String fileName) throws IOException {
        dump.saveToFile(fileName);
    }


    public void SetPosition(int offset) {
        dump.setIndex(offset);
    }

    public void SetPosition(KnownSections section) {
        dump.setIndex(GetDataOffset(section));
    }

    public void MovePosition(int offset) {
        dump.moveTo(offset);
    }

    public int GetPosition() {
        return dump.getIndex();
    }

    public void ReplaceArea(String oldString, String newString) {
        int delta = oldString.length() - newString.length();
        if (delta > 0) {
            dump.deleteArea(delta);
        } else if (delta < 0) {
            InsertArea(Math.abs(delta));
        }
        dump.setString(newString);
    }

    public void DeleteArea(int length) {
        dump.deleteArea(length);
    }

    public void InsertArea(int length) {
        dump.insertEmptyArea(length);
    }

    public void InsertEmptyArea(int length) {
        dump.insertEmptyArea(length);
    }

    public char ReadChar() {
        return dump.getChar();
    }

    public int ReadByte() {
        return dump.getByte();
    }

    public int ReadWord() {
        return dump.getWord();
    }

    public long ReadLongWord() {
        return dump.getLongWord();
    }

    public String ReadString(int size) {
        return dump.readString(size);
    }

    public void WriteByte(int value) {
        dump.setByte(value);
    }

    public void WriteWord(int value) {
        dump.setWord(value);
    }

    public void WriteLongWord(long value) {
        dump.setLongWord(value);
    }

    public void WriteString(String value) {
        dump.setString(value);
    }

    public String intToHex(int value, int size) {

        String result = Integer.toHexString(value).toUpperCase();
        return /*"$" +*/ StringUtils.leftPad(result, size, '0');
    }

    public String longToHex(long value, int size) {

        String result = Long.toHexString(value);
        return StringUtils.leftPad(result, size - result.length());
    }

    //TODO
    private void showMessage(String text) {
        Log.message(text);
    }

    //TODO
    public static class Log {

        public static void error(String message) {
            System.out.println("ERROR: " + message);
        }

        public static void debug(String message) {
            System.out.println("DEBUG: " + message);
        }

        public static void debug(int integer) {
            debug(Integer.toString(integer));
        }

        public static void newLine() {
            System.out.println("");
        }

        public static void message(String message) {
            System.out.println("MESSAGE: " + message);
        }
    }
}
