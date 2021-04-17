unit DataStructureUnit;

interface

uses classes, dialogs, sysutils, CRCUnit, LoggerUnit, Forms, Graphics;

const
  knownSections: Array[1..12] of String[4] = ('VERS','STUP','SNDS','TILE','ZONE','PUZ2','CHAR','CHWP','CAUX','TNAM','TGEN','ENDF');
                       //                       1      2      3      4      5      6     7      8      9       10    11     12
  planets: Array[1..5] of String = ('desert', 'snow', 'forest', 'unknown', 'swamp');

type
  TSectionMetricks = class
    public
    dataSize: Cardinal;
    fullSize: Cardinal;
    startOffset: Cardinal;
    dataOffset: Cardinal;
    constructor Create(dataSize, fullSize, dataOffset, startOffset: Cardinal);
  end;

  TMap = class
    public
    mapOffset, izonOffset, izaxOffset, izx2Offset, izx3Offset, izx4Offset, iactOffset: Cardinal;
    mapSize, izonSize, izaxSize, izx2Size, izx3Size, izx4Size, iactSize, oieOffset, oieSize, oieCount: Cardinal;
    IACTS: Array of Cardinal;
    constructor Create;
  end;

  TSection = class
  private
    crcs: TStringList;
  public
    sections: TStringList;
    data: TMemoryStream;
    tiles: array of Boolean;
    maps: TStringList;
    crc32: String;
    dtaRevision: String;
    version: String;
    soundsCount: Byte;
    tilesCount: Integer;
    mapsCount: Integer;
    puzzlesCount: Integer;
    charsCount: Integer;
    namesCount: Integer;
    procedure Clear;

    procedure AddMap(id: Word);

    procedure Add(section: String; dataSize, fullSize, dataOffset, startOffset: Cardinal);
    function GetStartOffset(section: String): Cardinal;
    function GetDataOffset(section: String): Cardinal;
    function GetDataSize(section: String): Cardinal;
    function GetFullSize(section: String): Cardinal;
    function Have(section: String): Boolean;

    procedure ReadDTAMetricks;
    procedure LoadFileToArray(fileName: String);
    procedure SaveToFile(fileName: String);

    procedure SetPosition(offset: Cardinal); overload;
    procedure SetPosition(section: String); overload;
    procedure MovePosition(offset: Integer);
    function GetPosition: Cardinal;


    function ReadByte: Byte;
    function ReadWord: Word;
    function ReadLongWord: Longword;
    function ReadString(size: Cardinal): String;
    function ReadChar: Char;

    procedure WriteByte(value: Byte);
    procedure WriteWord(value: Word);
    procedure WriteLongWord(value: LongWord);
    procedure WriteString(value: String);

    constructor Create;
    destructor Destroy; override;

    procedure ScanVERS(sectionName: String);
    procedure ScanSTUP(sectionName: String);
    procedure ScanSNDS(sectionName: String);
    procedure ScanTILE(sectionName: String);
    procedure ScanZONE(sectionName: String);
    procedure ScanPUZ2(sectionName: String);
    procedure ScanCHAR(sectionName: String);
    procedure ScanCHWP(sectionName: String);
    procedure ScanCAUX(sectionName: String);
    procedure ScanTNAM(sectionName: String);
    procedure ScanTGEN(sectionName: String);

    procedure ScanIZON(id: Word);
    procedure ScanIZAX(id: Word);
    procedure ScanIZX2(id: Word);
    procedure ScanIZX3(id: Word);
    procedure ScanIZX4(id: Word);
    procedure ScanIACT(id: Word);

    function ChunkIndex(s: String): Byte;
    function InBound(section: String): Boolean;

    function GetTileFlag(id: Word): Cardinal;
    procedure SetTileFlag(id: Word; flag: Cardinal);

    procedure DeleteArea(length: Cardinal);
    procedure InsertArea(length: Cardinal);
    procedure InsertEmptyArea(length: Cardinal);

    function GetSize: Cardinal;

    function GetIZON(offset : Cardinal): Word;
    function GetIACT(offset : Cardinal): Word;

    procedure ReplaceArea(oldString, newString: String); overload;
  end;

  var DTA: TSection;

implementation

constructor TMap.Create;
begin
  mapOffset := 0;
  izonOffset := 0;
  izaxOffset := 0;
  izx2Offset := 0;
  izx3Offset := 0;
  izx4Offset := 0;
  iactOffset := 0;
  mapSize := 0;
  izonSize := 0;
  izaxSize := 0;
  izx2Size := 0;
  izx3Size := 0;
  izx4Size := 0;
  iactSize := 0;
  oieOffset := 0;
  oieCount := 0;
  SetLength(IACTS, 0);
end;


constructor TSectionMetricks.Create(dataSize, fullSize, dataOffset, startOffset: Cardinal);
begin
  Self.dataSize := dataSize;
  Self.fullSize := fullSize;
  Self.startOffset := startOffset;
  Self.dataOffset := dataOffset;
end;

function TSection.GetTileFlag(id: Word): Cardinal;
begin
  SetPosition(GetDataOffset(knownSections[4]) + id * $404);
  result := ReadLongWord;
end;

procedure TSection.SetTileFlag(id: Word; flag: Cardinal);
begin
  SetPosition(GetDataOffset(knownSections[4]) + id * $404);
  WriteLongWord(flag);
end;

procedure TSection.Clear;
var i: Word;
begin
  if sections.Count > 0 then
        for i := 0 to sections.Count - 1 do sections.Objects[i].Free;
  sections.Clear;
  if maps.Count > 0 then
        for i := 0 to maps.Count - 1 do maps.Objects[i].Free;
  maps.Clear;
  SetLength(tiles, 0);
  version := '';
  soundsCount := 0;
  tilesCount := 0;
  mapsCount := 0;
  puzzlesCount := 0;
  charsCount := 0;
  namesCount := 0;
//  if data <> nil then FreeAndNil(data);
end;

procedure TSection.AddMap(id: Word);
begin
  maps.AddObject(IntToStr(id), TMap.Create);
end;

procedure TSection.Add(section: String; dataSize, fullSize, dataOffset, startOffset: Cardinal);
begin
  sections.AddObject(section, TSectionMetricks.Create(dataSize, fullSize, dataOffset, startOffset))
end;

function TSection.GetStartOffset(section: String): Cardinal;
begin
  result := TSectionMetricks(sections.Objects[sections.IndexOf(section)]).startOffset;
end;

function TSection.GetDataOffset(section: String): Cardinal;
begin
  result := TSectionMetricks(sections.Objects[sections.IndexOf(section)]).dataOffset;
end;

function TSection.GetDataSize(section: String): Cardinal;
begin
  result := TSectionMetricks(sections.Objects[sections.IndexOf(section)]).dataSize;
end;

function TSection.GetFullSize(section: String): Cardinal;
begin
  result := TSectionMetricks(sections.Objects[sections.IndexOf(section)]).fullSize;
end;

function TSection.Have(section: String): boolean;
begin
  result := sections.IndexOf(section) <> -1;
end;


constructor TSection.Create;
begin
  sections := TStringList.Create;
  maps := TStringList.Create;
  crcs := TStringList.Create;
  crcs.LoadFromFile('./conf/crcs.cfg');
end;

destructor TSection.Destroy;
begin
  Clear;
  sections.Free;
  maps.Free;
  crcs.Free;
  FreeAndNil(data);
end;


function TSection.GetSize: Cardinal;
begin
  result := data.Size;
end;

function TSection.InBound(section: String): boolean;
begin
 result := data.Position < getDataOffset(section) + getDataSize(section)
end;


function TSection.ChunkIndex(s:string):byte;
var i: byte;
begin
 result:=0;
 for i:=1 to sizeof(knownSections) do
 if knownSections[i]=s then result:=i;
end;

procedure TSection.readDTAMetricks;
var keepReading:boolean;
s: String;
i: Integer;
begin
  SetPosition(0);
  keepReading:=true;
  while (keepReading) do
    begin
      Application.ProcessMessages;
      s := ReadString(4);
      //Log.debug(s);
      case ChunkIndex(s) of
       1: ScanVERS(s); //версия файла
       2: ScanSTUP(s); //чтение титульной картинки
       3: ScanSNDS(s); //SNDS, 4 байта размер блока, C0FF, размер названия сэмпла+$0, размер названия сэмпла+$0,... пока не надо
       4: ScanTILE(s); //тайлы
       5: ScanZONE(s); //локации
       6: ScanPUZ2(s); //ещё диалоги
       7: ScanCHAR(s); //персонажи?
       8: ScanCHWP(s); //персонажи?????
       9: ScanCAUX(s); //персонажи?????
       10: ScanTNAM(s); //названия+тайлы
       11: ScanTGEN(s); //встречается в германском образе. Что такое - не знаю.
       12: begin
         Add(s, 4, 8, data.Position, data.Position - 4);
         keepReading:=false;
         end;
      else
       begin
        showmessage('Неизвестная секция: 0x' + IntToHex(GetPosition, 4) + ': "' + s + '"');
        keepReading:=false;
       end;
      end;
  end;
  Log.NewLine;
  Log.Debug('Sections detailed:');
  Log.Debug('------------------');
  Log.NewLine;
  Log.Debug(Format('%7s %12s %11s %12s %11s', ['Section', 'Data offset', 'Data size', 'Start offset', 'Full size']));
  for i := 0 to sections.Count - 1 do
    begin
     Log.Debug(Format('%7s %12x %11d %12x %11d',
       [sections[i],
        TSectionMetricks(sections.Objects[i]).dataOffset,
        TSectionMetricks(sections.Objects[i]).dataSize,
        TSectionMetricks(sections.Objects[i]).startOffset,
        TSectionMetricks(sections.Objects[i]).fullSize
       ]));
    end;

  Log.NewLine;
  Log.Debug('Maps offsets, sizes detailed:');
  Log.Debug('------------------');
  Log.NewLine;
  Log.Debug(Format('%3s %-13s %-13s  %-16s  %-13s  %-13s  %-13s  %-13s  %-13s', ['#', 'MAP', 'IZON', 'OIE', 'IZAX', 'ISX2', 'IZX3', 'IZX4', 'IACT']));
  for i := 0 to maps.Count - 1 do
    Log.Debug(Format('%3d %-13s %-13s  %-16s  %-13s  %-13s  %-13s  %-13s  %-13s', [ i,
      IntToHex(TMap(maps.Objects[i]).mapOffset, 8) + ':' + IntToHex(TMap(maps.Objects[i]).mapSize, 4),
      IntToHex(TMap(maps.Objects[i]).izonOffset, 8) + ':' + IntToHex(TMap(maps.Objects[i]).izonSize, 4),
      IntToHex(TMap(maps.Objects[i]).oieOffset, 8) + ':' + IntToHex(TMap(maps.Objects[i]).oieSize, 4) + ':' + IntToHex(TMap(maps.Objects[i]).oieCount, 2),
      IntToHex(TMap(maps.Objects[i]).izaxOffset, 8) + ':' + IntToHex(TMap(maps.Objects[i]).izaxSize, 4),
      IntToHex(TMap(maps.Objects[i]).izx2Offset, 8) + ':' + IntToHex(TMap(maps.Objects[i]).izx2Size, 4),
      IntToHex(TMap(maps.Objects[i]).izx3Offset, 8) + ':' + IntToHex(TMap(maps.Objects[i]).izx3Size, 4),
      IntToHex(TMap(maps.Objects[i]).izx4Offset, 8) + ':' + IntToHex(TMap(maps.Objects[i]).izx4Size, 4),
      IntToHex(TMap(maps.Objects[i]).iactOffset, 8) + ':' + IntToHex(TMap(maps.Objects[i]).iactSize, 4)
      ]));
end;

procedure TSection.ScanTGEN(sectionName: String);
var sz: Longword;
begin
  sz := ReadLongWord;            //4 байта - длина блока TGEN
  //Add(sectionName, 4 + sz, index);
  Add(sectionName, sz, sz + 4 + 4, GetPosition, GetPosition - 4 - 4);
  MovePosition(sz);
  Log.Debug(sectionName + ' - what is it?...');
end;

// 246 * 26 + size(4) + 'TNAM' + $FFFF = 6406
procedure TSection.ScanTNAM(sectionName: String);
var k: Word;
sz: Longword;
count: Byte;
begin
  sz := ReadLongWord;           //4 bytes - length of section TNAM
  Add(sectionName, sz, sz + 4 + 4, GetPosition, GetPosition - 4 - 4);
  //MoveIndex(sz);
  count := 0;
  while InBound(sectionName) do
  begin
    k := ReadWord;              //2 байта - номер персонажа (тайла)
    if k = $FFFF then Break;
    ReadString(24);             //24 bytes - rest of current name length
    inc(count);
  end;
  namesCount := count;
  Log.Debug(sectionName + ': ' + InttoStr(count));
end;

// 77 * 4 = 308 + size(4) + 'CAUX' + FFFF = 318
procedure TSection.ScanCAUX(sectionName: String);
var sz: Longword;
count: Byte;
begin
  sz := ReadLongWord;           //4 bytes - length of section CAUX
  Add(sectionName, sz, sz + 4 + 4, GetPosition, GetPosition - 4 - 4);
  MovePosition(sz);
  count := (sz - 2) div 4;
  Log.Debug(sectionName + ': ' + inttostr(count));
end;

// 77 * 6 = 462 + size(4) + 'CHWP' + FFFF = 472
procedure TSection.ScanCHWP(sectionName: String);
var sz: Longword;
count: Byte;
begin
  sz := ReadLongWord;           //4 bytes - length of section CHWP
  Add(sectionName, sz, sz + 4 + 4, GetPosition, GetPosition - 4 - 4);
  MovePosition(sz);
    count := (sz - 2) div 6;
  Log.Debug(sectionName + ': ' + inttostr(count));
end;

// 78 Characters
procedure TSection.ScanCHAR(sectionName: String);
var sz, csz: Longword;
ch: Word;
begin
  sz := ReadLongWord;           //4 bytes - length of section CHAR
  Add(sectionName, sz, sz + 4 + 4, GetPosition, GetPosition - 4 - 4);
  charsCount := 0;
  while InBound(sectionName) do
   begin
    ch := ReadWord;             //2 bytes - index of character
    if ch = $FFFF then Break;
    ReadString(4);              //4 bytes - 'ICHA'
    csz := ReadLongWord;        //4 bytes - rest of current character length
    ReadString(csz);
    inc(charsCount);
   end;
  Log.Debug('Characters: ' + IntToStr(charsCount));
end;

procedure TSection.ScanPUZ2(sectionName: String);
var sz, psz: Longword;
pz: Word;
begin
  sz := ReadLongWord;           //4 bytes - length of section PUZ2
  Add(sectionName, sz, sz + 4 + 4, GetPosition, GetPosition - 4 - 4);
  puzzlesCount := 0;
  while InBound(sectionName) do
   begin
    pz := ReadWord;             //2 bytes - index of puzzle (from 0)
    if pz = $FFFF then Break;
    ReadString(4);              //4 bytes - 'IPUZ'
    psz := ReadLongWord;        //4 bytes - rest of current puzzle length
    ReadString(psz);
    inc(puzzlesCount)
   end;
  Log.Debug('Puzzles: ' + IntToStr(puzzlesCount));;
end;

procedure TSection.ScanZONE(sectionName: String);
var sz: Longword;
i: Word;
ind: Cardinal;
begin
  //Signature: String[4];       // 4 bytes: "ZONE" - уже прочитано
  ind := GetPosition;
  mapsCount := ReadWord;        // 2 bytes - maps count $0291 = 657 items
  // Next repeated data of TZone
  for i:=1 to mapsCount do
    begin
      ReadWord;                //unknown:word;          01 00 - unknown 2 bytes
      sz:=ReadLongWord;        //size:longword;         size of current map (4b)
      Log.Debug(inttostr(i-1) + ' Offset:Size: ' + IntToHex(GetPosition - 4, 4) + ':' + IntToHex(sz, 4));
      MovePosition(sz);
    end;
  //Add(sectionName, 4 + index - ind, ind);
  Add(sectionName, GetPosition - ind, GetPosition - ind + 4, ind, ind - 4);
  Log.Debug('Maps (zones): ' + IntToStr(mapsCount));
  //Log.NewLine;


  SetPosition(knownSections[5]);   // ZONE
  ReadWord;                     // 2 bytes - maps count $0291 = 657 items
//    showmessage('scan zone ok');
  for i := 0 to mapsCount - 1 do ScanIZON(i);
  //Log.NewLine;

end;

procedure TSection.ScanIZON(id: Word);
var sz,  pn, w, h, oieCount, uw: Word;
  size, unk2: Longword;
begin
  // Repeated data of TZone
  AddMap(id);
  uw := ReadWord;               // unknown:word; //01 00 // map type (desert, ...)
  if uw > $0005 then ShowMessage('ID: ' + IntToStr(id) + ' UNK: ' + IntToHex(uw, 4) + ' > ' + IntToStr($0005));
  sz := ReadLongWord;           // size:longword; size of the current map
  TMap(maps.Objects[id]).mapOffset := GetPosition;
  TMap(maps.Objects[id]).mapSize := sz + 6;
  pn := ReadWord;               // number:word; //2 bytes - serial number of the map starting with 0
  if pn <> id then ShowMessage('ID: ' + IntToStr(pn) + ' <> ' + IntToStr(id));
  ReadString(4);                // izon:string[4]; //4 bytes: "IZON"
  size := ReadLongWord;         // longword; //4 bytes - size of block IZON (include 'IZON') until object info entry count
  TMap(maps.Objects[id]).izonOffset := GetPosition;
  TMap(maps.Objects[id]).izonSize := size - 6;
  Application.ProcessMessages;

  w := ReadWord;                // width:word; //2 bytes: map width (W)
  h := ReadWord;                // height:word; //2 bytes: map height (H)
  ReadWord;                     // flags:word; //2 byte: map flags (unknown meanings)* добавил байт снизу
  unk2 := ReadLongWord;         // unused:longword; //5 bytes: unused (same values for every map)
  ReadWord;                     // planet:word; //1 byte: planet (0x01 = desert, 0x02 = snow, 0x03 = forest, 0x05 = swamp)* добавил следующий байт

  //Log.Debug('Map #' + IntToStr(pn) + ': ' + planets[p] + ' (' + IntToStr(w) + 'x' + IntToStr(h) + ')');
  //Log.Debug('Flags: ' + IntToStr(flags) + '; unknown value: $' + IntToHex(unk, 4));
  if unk2 <> $FFFF0000 then ShowMessage(IntToHex(unk2, 8));

  MovePosition(w * h * 6);

  oieCount := ReadWord;         //2 bytes: object info entry count (X)
  TMap(maps.Objects[id]).oieOffset := GetPosition;
  TMap(maps.Objects[id]).oieCount := oieCount;
  TMap(maps.Objects[id]).oieSize := oieCount * 12;
  MovePosition(oieCount * 12);     //X*12 bytes: object info data

  //Log.Debug('Object info entries count: ' + IntToStr(oieCount));

  ScanIZAX(id);
  ScanIZX2(id);
  ScanIZX3(id);
  ScanIZX4(id);
  ScanIACT(id);
end;

procedure TSection.ScanIZAX(id: Word);
var size: Longword;
begin
  ReadString(4);                //4 bytes: "IZAX"
  size := ReadLongWord;         //4 bytes: length (X)
  TMap(maps.Objects[id]).izaxOffset := GetPosition;
  TMap(maps.Objects[id]).izaxSize := size - 8;
  MovePosition(size - 8);          //X-8 bytes: IZAX data
end;

procedure TSection.ScanIZX2(id: Word);
var size: Longword;
begin
  ReadString(4);                //4 bytes: "IZX2"
  size := ReadLongWord;         //4 bytes: length (X)
  TMap(maps.Objects[id]).izx2Offset := GetPosition;
  TMap(maps.Objects[id]).izx2Size := size - 8;
  MovePosition(size - 8);          //X-8 bytes: IZX2 data
end;

procedure TSection.ScanIZX3(id: Word);
var size: Longword;
begin
  ReadString(4);                //4 bytes: "IZX3"
  size := ReadLongWord;         //4 bytes: length (X)
  TMap(maps.Objects[id]).izx3Offset := GetPosition;
  TMap(maps.Objects[id]).izx3Size := size - 8;
  MovePosition(size - 8);          //X-8 bytes: IZX3 data
end;

procedure TSection.ScanIZX4(id: Word);
begin
  ReadString(4);                //4 bytes: "IZX4"
  TMap(maps.Objects[id]).izx4Offset := GetPosition;
  TMap(maps.Objects[id]).izx4Size := 8;
  MovePosition(8);          //8 bytes: IZX4 data
end;

procedure TSection.ScanIACT(id: Word);
label l1, l2;
var title: String;
size, idx: Longword;
k: Byte;
begin
  idx := data.Position;
  TMap(maps.Objects[id]).iactOffset := GetPosition;
  //TMap(maps.Objects[id]).iactSize := size - 6;
  k := 0;
l1:
  title := ReadString(4); //4 bytes: "IACT"
  if title <> 'IACT' then goto l2;
  SetLength(TMap(maps.Objects[id]).IACTS, Length(TMap(maps.Objects[id]).IACTS) + 1);
  TMap(maps.Objects[id]).IACTS[k] := GetPosition;
  size := ReadLongWord;   //4 bytes: length (X)
  //Log.Debug(title + ' ' + inttohex(size, 4));
  MovePosition(size);
  Inc(k);
  goto l1;
l2:
  MovePosition(-4);
  TMap(maps.Objects[id]).iactSize := GetPosition - idx;
end;


procedure TSection.ScanTILE(sectionName: String);
var sz: Cardinal;
i: Word;
begin
  sz:=ReadLongWord;             //4 bytes - length of section TILE
  Add(sectionName, sz, sz + 4 + 4, GetPosition, GetPosition - 4 - 4);
  MovePosition(sz);
  tilesCount := sz div $404;
  SetLength(tiles, tilesCount);
  for i := 0 to tilesCount - 1 do tiles[i] := false;
  Log.debug('Sprites, tiles: ' + IntToStr(tilesCount));
end;


procedure TSection.ScanSNDS(sectionName: String);
var sz, msz: Word;
begin
  sz:=ReadLongWord;             //4 bytes - length of section SNDS
  Add(sectionName, sz, sz + 4 + 4, GetPosition, GetPosition - 4 - 4);
  soundsCount := 0;
  ReadWord;
  while InBound(knownSections[3]) do
   begin
    msz := ReadWord;
    ReadString(msz);
    inc(soundsCount);
   end;
  Log.Debug('Sounds, melodies: ' + IntToStr(soundsCount));
end;

procedure TSection.ScanSTUP(sectionName: String);
var sz: Longword;
begin
  sz:=ReadLongWord;             //4 bytes - length of section STUP
  Add(sectionName, sz, sz + 4 + 4, GetPosition, GetPosition - 4 - 4);
  MovePosition(sz);
  Log.Debug('Title screen: exists');
end;

procedure TSection.ScanVERS(sectionName: String);
var ver: LongWord;
begin
  Add(sectionName, 4, 4 + 4, GetPosition, GetPosition - 4);
  data.ReadBuffer(ver, SizeOf(ver));
  if ver = $0200 then version := '2.0' else version := 'x.x';
  Log.Debug('File version: ' + version);
  //showmessage('vers');
end;


function TSection.GetIZON(offset : Cardinal): Word;
var i: Word;
begin
  //Log.Debug(inttostr(maps.Count));
  //for i := 0 to 5 do
  //  Log.Debug('====izon #' + inttostr(i) + ': ' + inttohex(TMap(maps.Objects[i]).izonOffset, 4));

  for i := 0 to mapsCount - 1 do
  begin
    //Log.Debug('-izon #' + inttostr(i) + ': ' + inttohex(offset, 4) + '~' + inttohex(TMap(maps.Objects[i]).izonOffset, 4));
    if offset < TMap(maps.Objects[i]).izonOffset then Break;
  end;
  Result := i - 1;
end;

function TSection.GetIACT(offset : Cardinal): Word;
var izon, i: Word;
begin
//  Log.Debug('GetIACT. Offset: ' + inttohex(offset,4));
  izon := GetIZON(offset);
  Log.Debug('GetIZON: ' + inttostr(izon));
  for i := 0 to Length(TMap(maps.Objects[izon]).IACTS) - 1 do
  begin
//    Log.Debug(inttostr(i) + ': ' + inttohex(TMap(maps.Objects[izon]).IACTS[i], 4));
    if offset < TMap(maps.Objects[izon]).IACTS[i] then Break;
    end;
  Result := i - 1;
  Log.Debug('IZON/IACT: ' + inttostr(izon) + '/' + inttostr(i - 1));
end;


procedure TSection.LoadFileToArray(fileName: String);
begin
  Log.Debug('DTA file internal structure');
  Log.Debug('===========================');
  Log.NewLine;

  crc32 := IntToHex(GetFileCRC(fileName), 8);
  if crcs.IndexOfName(crc32) = -1
    then dtaRevision := 'Unknown'
    else dtaRevision := crcs.Values[crc32];

  if data <> nil then FreeAndNil(data);
  data := TMemoryStream.Create;
  data.LoadFromFile(fileName);

  Log.NewLine;
  Log.Debug('Sections:');
  Log.Debug('---------');
  Log.NewLine;
  Log.debug('Size: ' + inttostr(data.Size));
  Log.debug('CRC-32: ' + crc32);
  Log.debug('DTA revision: ' + dtaRevision);
  SetPosition(0);
end;

procedure TSection.SaveToFile(fileName: String);
begin
  dta.SaveToFile(fileName);
end;


procedure TSection.SetPosition(offset: Cardinal);
begin
 data.Position := offset;
end;

procedure TSection.SetPosition(section: String);
begin
 data.Position := GetDataOffset(section);
end;

procedure TSection.MovePosition(offset: integer);
begin
  data.Seek(offset, soCurrent);
end;

function TSection.GetPosition: Cardinal;
begin
  result := data.Position;
end;

procedure TSection.ReplaceArea(oldString, newString: String);
var delta: Integer;
begin
  delta := Length(oldString) - Length(newString);
  if delta > 0 then DeleteArea(delta);
  if delta < 0 then InsertArea(Abs(delta));
  WriteString(newString);
end;

procedure TSection.DeleteArea(length: Cardinal);
begin
  Move(Pointer(Cardinal(data.Memory) + data.Position + length)^,
    Pointer(Cardinal(data.Memory) + data.Position)^,
    data.Size - data.Position - length);
  data.SetSize(data.Size - length);
end;

procedure TSection.InsertArea(length: Cardinal);
begin
  data.SetSize(data.Size + length);
  Move(Pointer(Cardinal(data.Memory) + data.Position)^,
    Pointer(Cardinal(data.Memory) + data.Position + length)^,
    data.Size - data.Position - length);
end;


procedure TSection.InsertEmptyArea(length: Cardinal);
begin
  InsertArea(length);
  FillChar(Pointer(Cardinal(data.Memory) + data.Position)^, length, $00);
end;

function TSection.ReadChar: Char;
begin
  data.ReadBuffer(result, SizeOf(result));
end;

function TSection.ReadByte: Byte;
begin
  data.ReadBuffer(result, SizeOf(result));
end;

function TSection.ReadWord: Word;
begin
  data.ReadBuffer(result, SizeOf(result));
end;

function TSection.ReadLongWord: LongWord;
begin
  data.ReadBuffer(result, SizeOf(result));
end;

function TSection.ReadString(size: Cardinal): String;
begin
  SetLength(result, size);
  if size > 0 then
      data.ReadBuffer(result[1], size)
end;

procedure TSection.WriteByte(value: Byte);
begin
 data.WriteBuffer(value, SizeOf(value));
end;

procedure TSection.WriteWord(value: Word);
begin
 data.WriteBuffer(value, SizeOf(value));
end;

procedure TSection.WriteLongWord(value: LongWord);
begin
 data.WriteBuffer(value, SizeOf(value));
end;

procedure TSection.WriteString(value: String);
begin
 data.WriteBuffer(value[1], Length(value));
end;

initialization

  DTA := TSection.Create;

finalization

  DTA.Clear;
  DTA.Free;

end.
