unit BMPUnit;

interface

uses Windows, Classes, ExtCtrls, SysUtils, Graphics, Dialogs, DataStructureUnit;

const
GamePalette: array[0..1023] of byte = (	$FE, $00, $FE, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00,
	$00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00,
	$00, $00, $00, $00, $00, $00, $00, $00, $FF, $FF, $8B, $00, $C3, $CF, $4B, $00,
	$8B, $A3, $1B, $00, $57, $77, $00, $00, $8B, $A3, $1B, $00, $C3, $CF, $4B, $00,
	$FB, $FB, $FB, $00, $EB, $E7, $E7, $00, $DB, $D3, $D3, $00, $CB, $C3, $C3, $00,
	$BB, $B3, $B3, $00, $AB, $A3, $A3, $00, $9B, $8F, $8F, $00, $8B, $7F, $7F, $00,
	$7B, $6F, $6F, $00, $67, $5B, $5B, $00, $57, $4B, $4B, $00, $47, $3B, $3B, $00,
	$33, $2B, $2B, $00, $23, $1B, $1B, $00, $13, $0F, $0F, $00, $00, $00, $00, $00,
	$00, $C7, $43, $00, $00, $B7, $43, $00, $00, $AB, $3F, $00, $00, $9F, $3F, $00,
	$00, $93, $3F, $00, $00, $87, $3B, $00, $00, $7B, $37, $00, $00, $6F, $33, $00,
	$00, $63, $33, $00, $00, $53, $2B, $00, $00, $47, $27, $00, $00, $3B, $23, $00,
	$00, $2F, $1B, $00, $00, $23, $13, $00, $00, $17, $0F, $00, $00, $0B, $07, $00,
	$4B, $7B, $BB, $00, $43, $73, $B3, $00, $43, $6B, $AB, $00, $3B, $63, $A3, $00,
	$3B, $63, $9B, $00, $33, $5B, $93, $00, $33, $5B, $8B, $00, $2B, $53, $83, $00,
	$2B, $4B, $73, $00, $23, $4B, $6B, $00, $23, $43, $5F, $00, $1B, $3B, $53, $00,
	$1B, $37, $47, $00, $1B, $33, $43, $00, $13, $2B, $3B, $00, $0B, $23, $2B, $00,
	$D7, $FF, $FF, $00, $BB, $EF, $EF, $00, $A3, $DF, $DF, $00, $8B, $CF, $CF, $00,
	$77, $C3, $C3, $00, $63, $B3, $B3, $00, $53, $A3, $A3, $00, $43, $93, $93, $00,
	$33, $87, $87, $00, $27, $77, $77, $00, $1B, $67, $67, $00, $13, $5B, $5B, $00,
	$0B, $4B, $4B, $00, $07, $3B, $3B, $00, $00, $2B, $2B, $00, $00, $1F, $1F, $00,
	$DB, $EB, $FB, $00, $D3, $E3, $FB, $00, $C3, $DB, $FB, $00, $BB, $D3, $FB, $00, 
	$B3, $CB, $FB, $00, $A3, $C3, $FB, $00, $9B, $BB, $FB, $00, $8F, $B7, $FB, $00,
	$83, $B3, $F7, $00, $73, $A7, $FB, $00, $63, $9B, $FB, $00, $5B, $93, $F3, $00,
	$5B, $8B, $EB, $00, $53, $8B, $DB, $00, $53, $83, $D3, $00, $4B, $7B, $CB, $00,
	$9B, $C7, $FF, $00, $8F, $B7, $F7, $00, $87, $B3, $EF, $00, $7F, $A7, $F3, $00,
	$73, $9F, $EF, $00, $53, $83, $CF, $00, $3B, $6B, $B3, $00, $2F, $5B, $A3, $00,
	$23, $4F, $93, $00, $1B, $43, $83, $00, $13, $3B, $77, $00, $0B, $2F, $67, $00, 
	$07, $27, $57, $00, $00, $1B, $47, $00, $00, $13, $37, $00, $00, $0F, $2B, $00,
	$FB, $FB, $E7, $00, $F3, $F3, $D3, $00, $EB, $E7, $C7, $00, $E3, $DF, $B7, $00,
	$DB, $D7, $A7, $00, $D3, $CF, $97, $00, $CB, $C7, $8B, $00, $C3, $BB, $7F, $00, 
	$BB, $B3, $73, $00, $AF, $A7, $63, $00, $9B, $93, $47, $00, $87, $7B, $33, $00,
	$6F, $67, $1F, $00, $5B, $53, $0F, $00, $47, $43, $00, $00, $37, $33, $00, $00,
	$FF, $F7, $F7, $00, $EF, $DF, $DF, $00, $DF, $C7, $C7, $00, $CF, $B3, $B3, $00,
	$BF, $9F, $9F, $00, $B3, $8B, $8B, $00, $A3, $7B, $7B, $00, $93, $6B, $6B, $00, 
	$83, $57, $57, $00, $73, $4B, $4B, $00, $67, $3B, $3B, $00, $57, $2F, $2F, $00, 
	$47, $27, $27, $00, $37, $1B, $1B, $00, $27, $13, $13, $00, $1B, $0B, $0B, $00, 
	$F7, $B3, $37, $00, $E7, $93, $07, $00, $FB, $53, $0B, $00, $FB, $00, $00, $00,
	$CB, $00, $00, $00, $9F, $00, $00, $00, $6F, $00, $00, $00, $43, $00, $00, $00,
	$BF, $BB, $FB, $00, $8F, $8B, $FB, $00, $5F, $5B, $FB, $00, $93, $BB, $FF, $00, 
	$5F, $97, $F7, $00, $3B, $7B, $EF, $00, $23, $63, $C3, $00, $13, $53, $B3, $00, 
	$00, $00, $FF, $00, $00, $00, $EF, $00, $00, $00, $E3, $00, $00, $00, $D3, $00,
	$00, $00, $C3, $00, $00, $00, $B7, $00, $00, $00, $A7, $00, $00, $00, $9B, $00,
	$00, $00, $8B, $00, $00, $00, $7F, $00, $00, $00, $6F, $00, $00, $00, $63, $00,
	$00, $00, $53, $00, $00, $00, $47, $00, $00, $00, $37, $00, $00, $00, $2B, $00,
	$00, $FF, $FF, $00, $00, $E3, $F7, $00, $00, $CF, $F3, $00, $00, $B7, $EF, $00,
	$00, $A3, $EB, $00, $00, $8B, $E7, $00, $00, $77, $DF, $00, $00, $63, $DB, $00,
	$00, $4F, $D7, $00, $00, $3F, $D3, $00, $00, $2F, $CF, $00, $97, $FF, $FF, $00,
	$83, $DF, $EF, $00, $73, $C3, $DF, $00, $5F, $A7, $CF, $00, $53, $8B, $C3, $00, 
	$2B, $2B, $00, $00, $23, $23, $00, $00, $1B, $1B, $00, $00, $13, $13, $00, $00,
	$FF, $0B, $00, $00, $FF, $00, $4B, $00, $FF, $00, $A3, $00, $FF, $00, $FF, $00, 
	$00, $FF, $00, $00, $00, $4B, $00, $00, $FF, $FF, $00, $00, $FF, $33, $2F, $00,
	$00, $00, $FF, $00, $00, $1F, $97, $00, $DF, $00, $FF, $00, $73, $00, $77, $00,
	$6B, $7B, $C3, $00, $57, $57, $AB, $00, $57, $47, $93, $00, $53, $37, $7F, $00,
	$4F, $27, $67, $00, $47, $1B, $4F, $00, $3B, $13, $3B, $00, $27, $77, $77, $00,
	$23, $73, $73, $00, $1F, $6F, $6F, $00, $1B, $6B, $6B, $00, $1B, $67, $67, $00,
	$1B, $6B, $6B, $00, $1F, $6F, $6F, $00, $23, $73, $73, $00, $27, $77, $77, $00,
	$FF, $FF, $EF, $00, $F7, $F7, $DB, $00, $F3, $EF, $CB, $00, $EF, $EB, $BB, $00,
	$F3, $EF, $CB, $00, $E7, $93, $07, $00, $E7, $97, $0F, $00, $EB, $9F, $17, $00,
	$EF, $A3, $23, $00, $F3, $AB, $2B, $00, $F7, $B3, $37, $00, $EF, $A7, $27, $00,
	$EB, $9F, $1B, $00, $E7, $97, $0F, $00, $0B, $CB, $FB, $00, $0B, $A3, $FB, $00,
	$0B, $73, $FB, $00, $0B, $4B, $FB, $00, $0B, $23, $FB, $00, $0B, $73, $FB, $00,
	$00, $13, $93, $00, $00, $0B, $D3, $00, $00, $00, $00, $00, $00, $00, $00, $00,
	$00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00,
	$00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $00, $FF, $FF, $FF, $00);

type

 bmFileHeader = packed record {заголовок файла}
  Signature : word; {сигнатура }
  Size : dword; {длина файла в байтах}
  Reserved1 : word; {зарезервировано}
  Reserved2 : word; {зарезервировано}
  Offset : longint; {смещение изображения в байтах (1078)}
  end;
  
 bmInfoHeader = packed record {информационный заголовок}
  SizeOfHeader : longint; {длина заголовка в байтах (40)}
  Width : longint; {ширина изображения (в точках)}
  Height : longint; {высота изображения (в точках)}
  Planes : word; {число плоскостей (1)}
  BitC : word; {глубина цвета (бит на точку) (8)}
  Compression : longint; {тип компрессии (0 - нет)}
  Size : longint; {размер изображения в байтах}
  XppM : longint; {горизонтальное разрешение} {(точек на метр - обычно 0)}
  YppM : longint; {вертикальное разрешение} {(точек на метр - обычно 0)}
  NCoL : longint; {число цветов} {(если максимально допустимое - 0)}
  NCoI : longint; {число основных цветов}
  end;

 bmHeader = record {полный заголовок файла}
  FileHeader : bmFileHeader; {заголовок файла}
  InfoHeader : bmInfoHeader; {информационный заголовок}
  Palette : packed array[0..255,0..3]of byte; {таблица палитры}
  end;

    procedure FillInternalPalette(BM:TBitMap; color: TColor); //заполняет палитру нужного БитМапа. СТрого 256 цветов. Палитра прошита в программе
    procedure FillPalette(BM:TBitMap); //заполняет палитру нужного БитМапа. СТрого 256 цветов
    procedure ReadPicture(section: TSection; offset: Cardinal); //читает картинку из DTA файла согласно её смещению
    procedure CopyPicture(Image:TImage;x,y:word); //берёт прочитанную картинку и кидает куда нам нужно
    procedure CopyFrame(Canvas: TCanvas; x, y: Word); //копирует блочок на большой холст
    procedure LoadBMP(s:string;bmp:tbitmap);
    procedure SaveBMP(s:string;bmp:tbitmap);
    procedure InitBMP;
    procedure GetTile(section: TSection; id: Word; bmp: TBitmap);
    procedure DrawBMP(destBMP: TBitmap; x, y: Word; bmp: TBitmap);
    procedure ChangeBackground(bmp: TBitmap);
var
  BMP, BMP2: TBitMap;
  SrcFile, DestFile: File;
  Buf: array[1..2048] of Byte;
  NumRead:cardinal;
  bmh:bmHeader;
//  tz:tzone;

implementation

uses MainUnit;

procedure LoadBMP(s:string;bmp:tbitmap);
var i,j:cardinal;
P : PByteArray;
begin
  if not FileExists(s) then showmessage('File Exists: '+s);
  assignfile(DestFile, s);
  Reset(DestFile,1);
  if filesize(DestFile)<1082 then begin closefile(DestFile); exit; end; //защита, строго для 8-ми битных картинок!!!

  BlockRead(DestFile,bmh.FileHeader,sizeof(bmh.FileHeader));
  BlockRead(DestFile,bmh.InfoHeader,sizeof(bmh.InfoHeader));
  BlockRead(DestFile,bmh.Palette,sizeof(bmh.Palette));

  //тут должны быть всякие проверки
//  showmessage(inttostr(bmh.InfoHeader.Size));
  bmp.PixelFormat:=pf8bit;
  bmp.Width:=bmh.InfoHeader.Width;
  bmp.Height:=bmh.InfoHeader.Width;
  FillPalette(bmp);

  for i:=bmp.Height-1 downto 0 do
   for j:=0 to bmp.Width-1 do
    begin
     p:=bmp.ScanLine[i];
     blockread(DestFile, buf, 1, NumRead);
     p[j]:=buf[1];
    end;
  closefile(DestFile);
end;


procedure SaveBMP(s:string;bmp:tbitmap);
var i,j:cardinal;
P: PByteArray;
begin
  InitBMP;
  assignfile(DestFile, s);
  Rewrite(DestFile,1);
  BlockWrite(DestFile,bmh.FileHeader,sizeof(bmh.FileHeader));
  BlockWrite(DestFile,bmh.InfoHeader,sizeof(bmh.InfoHeader));

  for i:=0 to 255 do
   begin
    BlockWrite(DestFile,bmh.Palette[i][0],1);
    BlockWrite(DestFile,bmh.Palette[i][1],1);
    BlockWrite(DestFile,bmh.Palette[i][2],1);
    BlockWrite(DestFile,bmh.Palette[i][3],1);
   end;

  for i:=bmp.Height-1 downto 0 do
   for j:=0 to bmp.Width-1 do
    begin
     p:=bmp.ScanLine[i];
     blockwrite(DestFile, p[j], 1);
    end;
  closefile(DestFile);
end;


procedure InitBMP;
var i:cardinal;
  pal: PLogPalette;
  fBitmapPalEntries:word;
begin
  bmh.FileHeader.Signature:=$4D42; //сигнатура
  bmh.FileHeader.Size:=$0436+bmp.Width*bmp.Height;          // длина файла в байтах
  bmh.FileHeader.Reserved1:=0;     // зарезервировано
  bmh.FileHeader.Reserved2:=0;     // зарезервировано
  bmh.FileHeader.Offset:=$0436;    // смещение изображения в байтах (1078)

  bmh.InfoHeader.SizeOfHeader:=$28; {длина заголовка в байтах (40)}
  bmh.InfoHeader.Width:=bmp.Width; {ширина изображения (в точках)}
  bmh.InfoHeader.Height:=bmp.Height; {высота изображения (в точках)}
  bmh.InfoHeader.Planes:=1; {число плоскостей (1)}
  bmh.InfoHeader.BitC:=8; {глубина цвета (бит на точку) (8)}
  bmh.InfoHeader.Compression:=0; {тип компрессии (0 - нет)}
  bmh.InfoHeader.Size:=bmp.Width*bmp.Height; {размер изображения в байтах}
  bmh.InfoHeader.XppM:=0; {горизонтальное разрешение} {(точек на метр - обычно 0)}
  bmh.InfoHeader.YppM:=0; {вертикальное разрешение} {(точек на метр - обычно 0)}
  bmh.InfoHeader.NCoL:=$100; {число цветов} {(если максимально допустимое - 0)}
  bmh.InfoHeader.NCoI:=0; {число основных цветов}

  if bmp.Palette <> 0 then
    begin
      GetMem( Pal, Sizeof( TLogPalette ) + Sizeof( TPaletteEntry ) * 255 );
      Pal.palversion := $300;
      Pal.palnumentries := 256;
      // read palette data from bitmap
      fBitmapPalEntries := GetPaletteEntries(bmp.Palette, 0, 256, Pal.palPalEntry[0]);
      for i := 0 to fBitmapPalEntries - 1 do
        begin
          bmh.Palette[i][0]:=Pal.PalPalEntry[ i ].PeBlue;     //b
          bmh.Palette[i][1]:=Pal.PalPalEntry[ i ].PeGreen;     //g
          bmh.Palette[i][2]:=Pal.PalPalEntry[ i ].PeRed;     //r
          bmh.Palette[i][3]:=0;     //reserved=0
        end;
    end;
end;


procedure CopyPicture(Image:TImage;x,y:word);
begin
//  image.Picture.Bitmap.PixelFormat:=pf8bit;
  image.picture.bitmap.width:=BMP.Width;
  image.picture.bitmap.height:=BMP.Height;
  CopyFrame(Image.Canvas,x,y);
end;

procedure CopyFrame(Canvas: TCanvas; x, y: Word);
begin
//  Image.Canvas.CopyRect(rect(0+x,0+y,BMP.Width+x,BMP.Height+y), BMP.Canvas, rect(0,0,BMP.Width,BMP.Height));
  //Canvas.Brush.Style := bsClear;
  Canvas.BrushCopy(Rect(0 + x, 0 + y, BMP.Width + x, BMP.Height + y), BMP, Rect(0, 0, BMP.Width, BMP.Height), $FE00FE);
end;

procedure ReadPicture(section: TSection; offset: Cardinal);
var i, j: Word;
p: PByteArray;
begin
  if Offset > 0 then section.SetPosition(offset);
  for i := 0 to BMP.Height - 1 do
    for j := 0 to BMP.Width - 1 do
    begin
      p := BMP.ScanLine[i];
      p[j] := section.ReadByte;
    end;
end;

procedure GetTile(section: TSection; id: Word; bmp: TBitmap);
var i, j, index: Cardinal;
  p: PByteArray;
begin
  index := section.GetPosition;
  section.SetPosition(section.GetDataOffset(knownSections[4]) + id * $404 + 4);
  for i := 0 to bmp.Height - 1 do
  begin
    p := bmp.ScanLine[i];
    for j := 0 to bmp.Width - 1 do p[j] := section.ReadByte;
  end;
  section.SetPosition(index);
end;

procedure DrawBMP(destBMP: TBitmap; x, y: Word; bmp: TBitmap);
var i, j: Cardinal;
  pb, pd: PByteArray;
begin
  for i := 0 to bmp.Height - 1 do
  begin
    pb := bmp.ScanLine[i];
    pd := destBMP.ScanLine[i + y];
    for j := 0 to bmp.Width - 1 do
      pd[j + x] := pb[j];
  end;
end;

procedure ChangeBackground(bmp: TBitmap);
var i, j: Cardinal;
  p: PByteArray;
begin
  for i := 0 to bmp.Height - 1 do
  begin
    p := bmp.ScanLine[i];
    for j := 0 to bmp.Width - 1 do
      p[j] := 0;
  end;
end;

procedure FillInternalPalette(BM:TBitMap; color: TColor);
var
  pal: PLogPalette;
  hpal: HPALETTE;
  i:integer;
begin
  pal := nil;
  try
    GetMem(pal, sizeof(TLogPalette) + sizeof(TPaletteEntry) * 255);
    pal.palVersion := $300;
    pal.palNumEntries := 256;
    pal.palPalEntry[0].peRed := color and $FF;
    pal.palPalEntry[0].peGreen := (color shr 8) and $FF;
    pal.palPalEntry[0].peBlue := (color shr 16) and $FF;
    for i := 1 to 255 do
    begin
      pal.palPalEntry[i].peRed := GamePalette[i * 4 + 2];
      pal.palPalEntry[i].peGreen := GamePalette[i * 4 + 1];
      pal.palPalEntry[i].peBlue := GamePalette[i * 4 + 0];
    end;
    hpal := CreatePalette(pal^);
    if hpal <> 0 then BM.Palette := hpal;
  finally
    FreeMem(pal);
  end;
end;

procedure FillPalette(bm: TBitMap);
var
  pal: PLogPalette;
  hpal: HPALETTE;
  i:integer;
begin
  pal := nil;
  try
    GetMem(pal, sizeof(TLogPalette) + sizeof(TPaletteEntry) * 255);
    pal.palVersion := $300;
    pal.palNumEntries := 256;
    for i := 0 to 255 do
    begin
      pal.palPalEntry[i].peRed := bmh.Palette[i,2];
      pal.palPalEntry[i].peGreen := bmh.Palette[i,1];
      pal.palPalEntry[i].peBlue := bmh.Palette[i,0];
    end;
    hpal := CreatePalette(pal^);
    if hpal <> 0 then BM.Palette := hpal;
  finally
    FreeMem(pal);
  end;
end;


end.
 