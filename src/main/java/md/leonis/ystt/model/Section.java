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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static md.leonis.ystt.utils.Config.gamePalette;
import static md.leonis.ystt.utils.Config.section;

//TODO think how to use SizeSequence
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
    //TODO may be lis will be better???
    public Map<KnownSections, SectionMetrics> sections = new HashMap<>();
    public List<SectionMetrics> sectionsList = new ArrayList<>();
    //TODO dtaDump
    public Dump dump;
    public Dump exeDump;
    public boolean[] tiles;
    //TODO zonesList
    public List<Zone> maps = new ArrayList<>();
    public String exeCrc32;
    public String dtaCrc32;
    public String revision;
    public String revisionId;
    public String version;
    public List<String> sounds = new ArrayList<>();
    public int tilesCount;
    public List<Puzzle> puzzles = new ArrayList<>();
    public List<Char> chars = new ArrayList<>();
    public List<Name> names = new ArrayList<>();

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

        sections.clear();
        sectionsList.clear();

        tiles = new boolean[0];
        version = "";
        //soundsCount = 0;
        sounds.clear();
        tilesCount = 0;
        maps.clear();
        puzzles.clear();
        chars.clear();
        names.clear();
    }

    public void addSection(KnownSections section, int dataSize, int fullSize, int dataOffset, int startOffset) {
        SectionMetrics sectionMetrics = new SectionMetrics(section, dataSize, fullSize, startOffset, dataOffset);
        sections.put(section, sectionMetrics);
        sectionsList.add(sectionMetrics);
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
                        addSection(sections, 4, 8, dump.getIndex(), dump.getIndex() - 4);
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

        sectionsList.forEach(s -> Log.debug(String.format("%7s %12x %11d %12x %11d",
                s.getSection(),
                s.getDataOffset(),
                s.getDataSize(),
                s.getStartOffset(),
                s.getFullSize()
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
        addSection(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        Log.debug(sectionName + " - what is it?...");
    }

    // 246 * 26 + size(4) + 'TNAM' + $FFFF = 6406
    public void ScanTNAM(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section TNAM
        addSection(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        //MoveIndex(sz);
        while (InBound(sectionName)) {
            int tileId = ReadWord();
            if (tileId == 0xFFFF) {//2 байта - номер персонажа (тайла)
                break;
            }
            int position = GetPosition();
            String name = ReadString(24);             //24 bytes - rest of current name length
            names.add(new Name(position, tileId, name.substring(0, name.indexOf(0x00))));
        }
        Log.debug(sectionName + ": " + names.size());
    }

    // 78 Characters
    public void ScanCHAR(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section CHAR
        addSection(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        while (InBound(sectionName)) {
            int id = ReadWord();
            if (id == 0xFFFF) {         //2 bytes - index of character
                break;
            }
            String icha = ReadString(4);            //4 bytes - 'ICHA'
            assert "ICHA".equals(icha);
            int csz = (int) ReadLongWord();        //4 bytes - rest of current character length; always 74
            assert csz == 74;
            //ReadString(csz);

            int position = GetPosition();

            StringBuilder name = new StringBuilder();
            int b = ReadByte();
            while (b != 0) {
                name.append((char) b);                          // Character name, ended with $00 <= 16
                b = ReadByte();
            }
            if (name.length() % 2 == 0) {
                section.ReadByte();
            }
            int k = ReadByte();
            int n = ReadByte();
            while (k != 0xFF && n != 0xFF) {                       // unknown data 2 bytes * x, ended with $FF FF
                k = ReadByte();
                n = ReadByte();
            }
            long empty = ReadLongWord();                             // 4 bytes 00 00 00 00
            assert 0 == empty;
            List<Integer> tileIds = new ArrayList<>();
            while (GetPosition() < position + csz) {
                int tileId = ReadWord();                // REST - sequence of tiles # (2 bytes), or $FF FF
                if (tileId != 0xFFFF) {
                    tileIds.add(tileId);
                }
            }
            tileIds = tileIds.stream().distinct().sorted().collect(Collectors.toList());
            chars.add(new Char(id, position, csz, tileIds, name.toString()));
        }
        Log.debug("Characters: " + chars.size());
    }

    // 77 * 6 = 462 + size(4) + 'CHWP' + FFFF = 472
    public void ScanCHWP(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section CHWP
        addSection(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        int count = (sz - 2) / 6;
        Log.debug(sectionName + ": " + count);
    }

    // 77 * 4 = 308 + size(4) + 'CAUX' + FFFF = 318
    public void ScanCAUX(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - length of section CAUX
        addSection(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        int count = (sz - 2) / 4;
        Log.debug(sectionName + ": " + count);
    }

    public void ScanPUZ2(KnownSections sectionName) {

        int sz = (int) ReadLongWord();           //4 bytes - full length of section PUZ2
        addSection(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        //puzzlesCount = 0;
        puzzles.clear();
        while (InBound(sectionName)) {
            int id = ReadWord();
            if (id == 0xFFFF) {    //2 bytes - index of puzzle (from 0)
                break;
            }
            ReadString(4);              //4 bytes - 'IPUZ'
            int size = (int) ReadLongWord();        //4 bytes - rest of current puzzle length
            puzzles.add(new Puzzle(id, GetPosition(), size));
            ReadString(size);
            //puzzlesCount++;
        }
        int position = GetPosition();
        puzzles.forEach(this::DumpText);
        SetPosition(position);
        Log.debug("Puzzles: " + puzzles.size());
    }

    private boolean idDeprecatedWords(String text) {

        //TODO static
        List<String> arr = Arrays.asList(
                "el:", "ckup L", "MS S", "ЂОЅ", "n 2", "######", "######", "######", "plac", "Sho",
                "Show", "opI", "ne ", "Red", "Set", "######", "up L", "######", "ng, ", "opIt",
                "######", "ckup", "Y: 12 ", "Bump", "Pick", "Bum", "Has", "ђ0n", "Pickup L", "SetHer",
                "BumpTi", "Repla", "MS San", "######", "d my j", "WaitF", "SetH", "SetHe", "######", "eIte",
                "n 1 an", "њњћ", "LчЅ", "6,12", "0, 1", "Door", "aySo", "c X:", "Game", "Р©µ",
                "######", "a l", "######", "PlayS", "Force ", " Lev", "ter:", "ter:F", "ЂЫ№", "o Na",
                "t•±", "4kЯ", "w on o", "ndN", "HotS", "ЂУґ", "perial", "12 Y", "Name", "sta",
                "Wait", "securi", "'s don", "cku", "ДFВ", "rTim", "Wai", "шн±", "Backgr", "e's",
                "MS ", " ’µ", "h th", "n't th", "u ta", "s! What ", "Pic", "¤K‡", "Wait", "Coun",
                "#####", "ґъm", "up ", "Sav", "яяяяяяяя", "Rep", "ёDґ", "!!!", "ф$‚", "Coun"
        );

        boolean result = arr.contains(text);

        if (!result) {
            result = text.startsWith("Remov") && text.length() <= 7;
        }
        if (!result) {
            result = text.startsWith("Redr") && text.length() <= 6;
        }
        if (!result) {
            result = text.startsWith("ShowT");
        }
        if (!result) {
            result = text.startsWith("awArea");
        }
        if (!result) {
            result = text.contains("ndN");
        }
        if (!result) {
            result = text.startsWith("HasI");
        }
        if (!result) {
            result = text.startsWith("ZX");
        }
        if (!result) {
            result = text.startsWith(" X:");
        }
        if (!result) {
            result = text.startsWith(",");
        }

        if (text.startsWith(",") || text.startsWith(":")) {
            result = true;
        }
        return result;
    }

    char[] restrictedChars = {
            (char) 0x00, (char) 0x01, (char) 0x02, (char) 0x03, (char) 0x04, (char) 0x05, (char) 0x06, (char) 0x07,
            (char) 0x08, (char) 0x09, /*(char) 0x0A,*/ (char) 0x0B, (char) 0x0C, /*(char) 0x0D,*/ (char) 0x0E, (char) 0x0F,
            (char) 0x10, (char) 0x11, (char) 0x12, (char) 0x13, (char) 0x14, (char) 0x15, (char) 0x16, (char) 0x17,
            (char) 0x18, (char) 0x19, (char) 0x1A, (char) 0x1B, (char) 0x1C, (char) 0x1D, (char) 0x1E, (char) 0x1F,
            '\\', '@', '^', '¶', '<', '»', '(', ')'
    };

    //TODO need refactor
    //TODO dump puzzle, phrases metrics
    private void DumpText(Puzzle puzzle) {

        //phase := 1;
        section.SetPosition(puzzle.getPosition());
        while (GetPosition() < puzzle.getPosition() + puzzle.getSize() - 2) {
            //System.out.println(String.format("%s: %s", section.intToHex(section.GetPosition(), 4), section.intToHex(position + size - 2, 4)));

            //Section.Log.debug("Start new scan: " + section.intToHex(section.GetPosition(), 6));
            int tempIndex = GetPosition();
            int sz = ReadWord();
            //Section.Log.debug("Supposed size: " + section.intToHex(sz, 4));
            if ((sz < 0x0300) && (sz > 0x0002)) { // correct max size
                byte phase = 2;                   // size correct, maybe text?
                // TODO Application.ProcessMessages;

                String phrase = "";
                int position = -1;

                if (GetPosition() + sz <= puzzle.getPosition() + puzzle.getSize()) {
                    position = GetPosition();
                    phrase = ReadString(sz);
                    if (StringUtils.containsAny(phrase, restrictedChars)) {
                        phase = 1;
                    }
                } else {
                    phase = 1;
                }

                if (phase == 2) {
                    //TODO may be not need, if we dump to tables
                    phrase = phrase.replace("\r\n", "[CR]"); // LF(\n), CR(\r)
                    phrase = phrase.replace("[CR][CR]", "[CR2]");
                    phrase = phrase.replace((char) (0xA5), '_');
                    if (!idDeprecatedWords(phrase)) {
                        puzzle.getPhrases().add(new Phrase(position, phrase));
                        tempIndex = section.GetPosition() - 1;
                    }
                }
            }
            section.SetPosition(tempIndex + 1);
        }
    }

    public void ScanZONE(KnownSections sectionName) {

        //Signature: String[4];       // 4 bytes: "ZONE" - уже прочитано
        int ind = GetPosition();
        int mapsCount = ReadWord();        // 2 bytes - maps count $0291 = 657 items
        // Next repeated data of TZone
        for (int i = 0; i < mapsCount; i++) {
            ReadWord();                //unknown:word;          01 00 - unknown 2 bytes
            int sz = (int) ReadLongWord();        //size:longword;         size of current map (4b)
            Log.debug(i + " Offset:Size: " + intToHex(GetPosition() - 4, 4) + ':' + intToHex(sz, 4));
            MovePosition(sz);
        }

        addSection(sectionName, GetPosition() - ind, GetPosition() - ind + 4, ind, ind - 4);
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
        addSection(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
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
        addSection(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        ReadWord();
        while (InBound(sectionName)) {
            int msz = ReadWord(); // length
            sounds.add(ReadString(msz).trim());
        }

        Log.debug("Sounds, melodies: " + sounds.size());
    }

    public void ScanSTUP(KnownSections sectionName) {

        int sz = (int) ReadLongWord();             //4 bytes - length of section STUP
        addSection(sectionName, sz, sz + 4 + 4, GetPosition(), GetPosition() - 4 - 4);
        MovePosition(sz);
        Log.debug("Title screen: exists");
    }

    public void ScanVERS(KnownSections sectionName) {

        addSection(sectionName, 4, 4 + 4, GetPosition(), GetPosition() - 4);
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
        for (int i = 0; i < maps.size(); i++) {
            Log.debug("-izon #" + i + ": " + intToHex(offset, 4) + '~' + intToHex(maps.get(i).getIzon().getPosition(), 4));
            if (offset < maps.get(i).getIzon().getPosition()) {
                break;
            }
            result = i;
        }
        return result;
    }

    //TODO unused?????????????????
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

        Release release = Config.releases.stream().filter(r -> r.getExeCrc32().equals(exeCrc32) && r.getDtaCrc32().equals(dtaCrc32)).findFirst().orElse(null);

        if (null == release) {
            revision = "Unknown combination of files";
            revisionId = "unk";
        } else {
            revision = release.getTitle();
            revisionId = release.getId();
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
        Log.debug("DTA revision: " + revision);
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
        Log.debug("DTA revision: " + revision);
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
        return StringUtils.leftPad(result, size, '0');
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

        //TODO
        public static void clear() {
        }
    }
}
