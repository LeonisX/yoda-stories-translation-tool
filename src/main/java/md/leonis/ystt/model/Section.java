package md.leonis.ystt.model;

import md.leonis.bin.Dump;
import md.leonis.ystt.utils.BinaryUtils;
import md.leonis.ystt.utils.Config;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Section {

    // ShortInt                 -128..127                                   byte        8
    // Byte                     0..255;                                     int/char    8
    // SmallInt                 -32768..32767;                              int         16
    // Word                     0...65535                                   int/char    16
    // Integer, LongInt         -2147483648...2147483647                    int         32
    // Cardinal, LongWord       0..4294967295;                              long        32
    // Int64                    -9223372036854775808..9223372036854775807   long        64

    private static final String[] PLANETS = {"", "desert", "snow", "forest", "unknown", "swamp"};

    //TODO getters
    public Map<KnownSections, SectionMetrics> sections = new HashMap<>();
    //TODO dtaDump, exeDump also
    public Dump dump;
    public boolean[] tiles;
    public Map<Integer, MapEntry> maps = new HashMap<>();
    public String exeCrc32;
    public String dtaCrc32;
    public String dtaRevision;
    public String version;
    public byte soundsCount;
    public int tilesCount;
    public int mapsCount;
    public int puzzlesCount;
    public int charsCount;
    public int namesCount;

    public Section(File file) throws IOException {

        dump = new Dump(file);
        //crcs.LoadFromFile('./conf/crcs.cfg'); we read this in config
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
        maps = new HashMap<>();
        tiles = new boolean[0];
        version = "";
        soundsCount = 0;
        tilesCount = 0;
        mapsCount = 0;
        puzzlesCount = 0;
        charsCount = 0;
        namesCount = 0;
    }

    public void AddMap(int id) {
        maps.put(id, new MapEntry());
    }

    public void Add(KnownSections section, int dataSize, int fullSize, int dataOffset, int startOffset) {
        sections.put(section, new SectionMetrics(dataSize, fullSize, dataOffset, startOffset));
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

        SetPosition(0);
        boolean keepReading = true;
        while (keepReading) {
            //TODO
            //Application.ProcessMessages;
            String s = ReadString(4);
            Log.Debug(s);
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
            } catch (Exception e) {
                ShowMessage("Неизвестная секция: 0x" + IntToHex(GetPosition(), 4) + ": \"" + s + '"');
                keepReading = false;
            }
        }
        Log.NewLine();
        Log.Debug("Sections detailed:");
        Log.Debug("------------------");
        Log.NewLine();
        Log.Debug(String.format("%7s %12s %11s %12s %11s", "Section", "Data offset", "Data size", "Start offset", "Full size"));

        sections.forEach((key, value) -> Log.Debug(String.format("%7s %12x %11d %12x %11d",
                key,
                value.getDataOffset(),
                value.getDataSize(),
                value.getStartOffset(),
                value.getFullSize()
        )));

        Log.NewLine();
        Log.Debug("Maps offsets, sizes detailed:");
        Log.Debug("------------------");
        Log.NewLine();
        Log.Debug(String.format("%3s %-13s %-13s  %-16s  %-13s  %-13s  %-13s  %-13s  %-13s", "#", "MAP", "IZON", "OIE", "IZAX", "ISX2", "IZX3", "IZX4", "IACT"));

        maps.forEach((key, value) -> Log.Debug(String.format("%3d %-13s %-13s  %-16s  %-13s  %-13s  %-13s  %-13s  %-13s", key,
                IntToHex(value.getMapOffset(), 8) + ':' + IntToHex(value.getMapSize(), 4),
                IntToHex(value.getIzonOffset(), 8) + ':' + IntToHex(value.getIzonSize(), 4),
                IntToHex(value.getOieOffset(), 8) + ':' + IntToHex(value.getOieSize(), 4) + ':' + IntToHex(value.getOieCount(), 2),
                IntToHex(value.getIzaxOffset(), 8) + ':' + IntToHex(value.getIzaxSize(), 4),
                IntToHex(value.getIzx2Offset(), 8) + ':' + IntToHex(value.getIzx2Size(), 4),
                IntToHex(value.getIzx3Offset(), 8) + ':' + IntToHex(value.getIzx3Size(), 4),
                IntToHex(value.getIzx4Offset(), 8) + ':' + IntToHex(value.getIzx4Size(), 4),
                IntToHex(value.getIactOffset(), 8) + ':' + IntToHex(value.getIactSize(), 4)
        )));
    }

    public void ScanTGEN(KnownSections sectionName) {

        int sz = (int) ReadLongWord();            //4 байта - длина блока TGEN
        //Add(sectionName, 4 + sz, index);
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        Log.Debug(sectionName + " - what is it?...");
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
        Log.Debug(sectionName + ": " + count);
    }

    // 77 * 4 = 308 + size(4) + 'CAUX' + FFFF = 318
    public void ScanCAUX(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section CAUX
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        int count = (sz - 2) / 4;
        Log.Debug(sectionName + ": " + count);
    }

    // 77 * 6 = 462 + size(4) + 'CHWP' + FFFF = 472
    public void ScanCHWP(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section CHWP
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        int count = (sz - 2) / 6;
        Log.Debug(sectionName + ": " + count);
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
        Log.Debug("Characters: " + charsCount);
    }

    public void ScanPUZ2(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section PUZ2
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        int puzzlesCount = 0;
        while (InBound(sectionName)) {
            if (ReadWord() == 0xFFFF) {    //2 bytes - index of puzzle (from 0)
                break;
            }
            ReadString(4);              //4 bytes - 'IPUZ'
            int psz = (int) ReadLongWord();        //4 bytes - rest of current puzzle length
            ReadString(psz);
            puzzlesCount++;
        }
        Log.Debug("Puzzles: " + puzzlesCount);
    }

    public void ScanZONE(KnownSections sectionName) {

        //Signature: String[4];       // 4 bytes: "ZONE" - уже прочитано
        int ind = GetPosition();
        mapsCount = ReadWord();        // 2 bytes - maps count $0291 = 657 items
        // Next repeated data of TZone
        for (int i = 0; i < mapsCount; i++) {
            ReadWord();                //unknown:word;          01 00 - unknown 2 bytes
            int sz = (int) ReadLongWord();        //size:longword;         size of current map (4b)
            Log.Debug((i - 1) + " Offset:Size: " + IntToHex(GetPosition() - 4, 4) + ':' + IntToHex(sz, 4));
            MovePosition(sz);
        }

        Add(sectionName, GetPosition() - ind, GetPosition() - ind + 4, ind, ind - 4);
        Log.Debug("Maps (zones): " + mapsCount);
        //Log.NewLine();


        SetPosition(KnownSections.ZONE);   // ZONE
        ReadWord();                     // 2 bytes - maps count $0291 = 657 items
        ShowMessage("scan zone ok");
        for (int i = 0; i < mapsCount; i++) {
            ScanIZON(i);
        }
        //Log.NewLine();

    }

    public void ScanIZON(int id) {

        // Repeated data of TZone
        AddMap(id);
        int uw = ReadWord();               // unknown:word; //01 00 // map type (desert, ...)
        if (uw > 0x0005) {
            ShowMessage("ID: " + id + " UNK: " + IntToHex(uw, 4) + " > " + 0x0005);
        }
        int sz = (int) ReadLongWord();           // size:longword; size of the current map
        maps.get(id).setMapOffset(GetPosition());
        maps.get(id).setMapSize(sz + 6);
        int pn = ReadWord();               // number:word; //2 bytes - serial number of the map starting with 0
        if (pn != id) {
            ShowMessage("ID: " + pn + " <> " + id);
        }
        ReadString(4);                // izon:string[4]; //4 bytes: "IZON"
        int size = (int) ReadLongWord();         // longword; //4 bytes - size of block IZON (include 'IZON') until object info entry count
        maps.get(id).setIzonOffset(GetPosition());
        maps.get(id).setIzonSize(size - 6);

        //TODO
        //Application.ProcessMessages;

        int w = ReadWord();                // width:word; //2 bytes: map width (W)
        int h = ReadWord();                // height:word; //2 bytes: map height (H)
        int flags = ReadWord();             // flags:word; //2 byte: map flags (unknown meanings)* добавил байт снизу
        long unk2 = ReadLongWord();         // unused:longword; //5 bytes: unused (same values for every map)
        int p = ReadWord();               // planet:word; //1 byte: planet (0x01 = desert, 0x02 = snow, 0x03 = forest, 0x05 = swamp)* добавил следующий байт

        Log.Debug("Map #" + pn + ": " + PLANETS[p] + " (" + w + "x" + h + ")");
        Log.Debug("Flags: " + flags + "; unknown value: 0x" + LongToHex(unk2, 4));
        if (unk2 != 0xFFFF0000) {
            ShowMessage(LongToHex(unk2, 8));
        }

        MovePosition(w * h * 6);

        int oieCount = ReadWord();         //2 bytes: object info entry count (X)
        maps.get(id).setOieOffset(GetPosition());
        maps.get(id).setOieCount(oieCount);
        maps.get(id).setOieSize(oieCount * 12);
        MovePosition(oieCount * 12);     //X*12 bytes: object info data

        Log.Debug("Object info entries count: " + oieCount);

        ScanIZAX(id);
        ScanIZX2(id);
        ScanIZX3(id);
        ScanIZX4(id);
        ScanIACT(id);
    }

    public void ScanIZAX(int id) {

        ReadString(4);                //4 bytes: "IZAX"
        int size = (int) ReadLongWord();         //4 bytes: length (X)
        maps.get(id).setIzaxOffset(GetPosition());
        maps.get(id).setIzaxSize(size - 8);
        MovePosition(size - 8);          //X-8 bytes: IZAX data
    }

    public void ScanIZX2(int id) {

        ReadString(4);                //4 bytes: "IZX2"
        int size = (int) ReadLongWord();         //4 bytes: length (X)
        maps.get(id).setIzx2Offset(GetPosition());
        maps.get(id).setIzx2Size(size - 8);
        MovePosition(size - 8);          //X-8 bytes: IZX2 data
    }

    public void ScanIZX3(int id) {

        ReadString(4);                //4 bytes: "IZX3"
        int size = (int) ReadLongWord();         //4 bytes: length (X)
        maps.get(id).setIzx3Offset(GetPosition());
        maps.get(id).setIzx3Size(size - 8);
        MovePosition(size - 8);          //X-8 bytes: IZX3 data
    }

    public void ScanIZX4(int id) {

        ReadString(4);                //4 bytes: "IZX4"
        maps.get(id).setIzx4Offset(GetPosition());
        maps.get(id).setIzx4Size(8);
        MovePosition(8);          //8 bytes: IZX4 data
    }

    public void ScanIACT(int id) {

        int idx = dump.getIndex();
        maps.get(id).setIactOffset(GetPosition());

        List<Integer> iacts = maps.get(id).getIACTS();

        while (true) {
            String title = ReadString(4); //4 bytes: "IACT"
            if (title.equals("IACT")) {
                iacts.add(GetPosition());
                int size = (int) ReadLongWord();   //4 bytes: length (X)
                Log.Debug(title + ' ' + IntToHex(size, 4));
                MovePosition(size);
            } else {
                break;
            }
        }
        MovePosition(-4);
        iacts.set(iacts.size() - 1, GetPosition() - idx);
    }

    public void ScanTILE(KnownSections sectionName) {

        int sz = (int) ReadLongWord();             //4 bytes - length of section TILE
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        int tilesCount = sz / 0x404;
        tiles = new boolean[tilesCount];
        for (int i = 0; i < tilesCount; i++) {
            tiles[i] = false;
        }

        Log.Debug("Sprites, tiles: " + tilesCount);
    }

    public void ScanSNDS(KnownSections sectionName) {

        int sz = (int) ReadLongWord();             //4 bytes - length of section SNDS
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        soundsCount = 0;
        ReadWord();
        while (InBound(sectionName)) {
            int msz = ReadWord();

            ReadString(msz);
            soundsCount++;
        }

        Log.Debug("Sounds, melodies: " + soundsCount);
    }

    public void ScanSTUP(KnownSections sectionName) {

        int sz = (int) ReadLongWord();             //4 bytes - length of section STUP
        Add(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        Log.Debug("Title screen: exists");
    }

    public void ScanVERS(KnownSections sectionName) {

        Add(sectionName, 4, 4 + 4, GetPosition(), GetPosition() - 4);
        long ver = dump.getLongWord();
        if (ver == 0x0200) {
            version = "2.0";
        } else {
            version = "x.x";
        }
        Log.Debug("File version: " + version);
        System.out.println("vers");
    }

    public int GetIZON(int offset) {
        Log.Debug(maps.size());
        for (int i = 0; i < 5; i++) {
            Log.Debug("====izon #" + i + ": " + IntToHex(maps.get(i).getIzonOffset(), 4));
        }

        int result = 0;
        for (int i = 0; i < mapsCount; i++) {
            Log.Debug("-izon #" + i + ": " + IntToHex(offset, 4) + '~' + IntToHex(maps.get(i).getIzonOffset(), 4));
            if (offset < maps.get(i).getIzonOffset()) {
                break;
            }
            result = i;
        }
        return result;
    }

    public int GetIACT(int offset) {

        Log.Debug("GetIACT. Offset: " + IntToHex(offset, 4));
        int izon = GetIZON(offset);
        Log.Debug("GetIZON: " + izon);
        int result = 0;
        for (int i = 0; i < maps.get(izon).getIACTS().size(); i++) {
            Log.Debug(i + ": " + IntToHex(maps.get(izon).getIACTS().get(i), 4));
            if (offset < maps.get(izon).getIACTS().get(i)) {
                break;
            }
            result = i;
        }

        Log.Debug("IZON/IACT: " + izon + '/' + result);
        return result;
    }

    //TODO DTA, validate CRC32 also
    public void LoadFileToArray(String fileName) throws IOException {

        Log.Debug("DTA file internal structure");
        Log.Debug("===========================");
        Log.NewLine();

        Path exePath = Paths.get(fileName);
        Path dtaPath = getDtaPath(exePath.getParent());

        exeCrc32 = IntToHex(BinaryUtils.crc32(Paths.get(fileName)), 8);
        dtaCrc32 = IntToHex(BinaryUtils.crc32(Paths.get(fileName)), 8);

        Release release = Config.releases.stream().filter(r -> r.getExe().equals(exeCrc32) && r.getDta().equals(dtaCrc32)).findFirst().orElse(null);

        if (null == release) {
            dtaRevision = "Unknown";
        } else {
            dtaRevision = release.getTitle();
        }

        dump = new Dump(new File(fileName));

        Log.NewLine();
        Log.Debug("Sections:");
        Log.Debug("---------");
        Log.NewLine();
        //TODO exe
        Log.Debug("Size: " + dump.size());
        Log.Debug("CRC-32: " + dtaCrc32);
        Log.Debug("DTA revision: " + dtaRevision);
        SetPosition(0);
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

    public String IntToHex(int value, int size) {

        String result = Integer.toHexString(value);
        return StringUtils.leftPad(result, size - result.length());
    }

    public String LongToHex(long value, int size) {

        String result = Long.toHexString(value);
        return StringUtils.leftPad(result, size - result.length());
    }

    //TODO
    private void ShowMessage(String text) {
        Log.Message(text);
    }

    //TODO
    public static class Log {

        public static void Debug(String message) {
            System.out.println("DEBUG: " + message);
        }

        public static void Debug(int integer) {
            Debug(Integer.toString(integer));
        }

        public static void NewLine() {
            System.out.println("");
        }

        public static void Message(String message) {
            System.out.println("MESSAGE: " + message);
        }
    }
}
