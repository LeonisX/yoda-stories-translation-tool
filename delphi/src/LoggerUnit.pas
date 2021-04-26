unit LoggerUnit;

interface

uses ComCtrls, Classes, SysUtils;

type
  TLogger = class
  private
    lines: TStrings;
  public
    procedure Clear;
    procedure Info(text: String);
    procedure Debug(text: String);
    procedure Error(text: String);
    procedure SetOutput(lines: TStrings);
    procedure SaveToFile(filePath, fileName: String);
    procedure NewLine;
    constructor Create;
    destructor Destroy; override;
  end;

  var log: TLogger;

implementation

procedure TLogger.Clear;
begin
  if lines <> nil then lines.Clear;
end;

procedure TLogger.Debug(text: String);
begin
  //editor.SelAttributes.Color:=$000000;
  if lines <> nil then lines.Add(text);
end;

procedure TLogger.Info(text: String);
begin
  //editor.SelAttributes.Color:=$008800;
  if lines <> nil then lines.Add(text);
end;

procedure TLogger.Error(text: String);
begin
  //editor.SelAttributes.Color:=$0000EE;
  if lines <> nil then lines.Add(text);
end;

procedure TLogger.NewLine;
begin
  lines.Add('');
end;

procedure TLogger.SetOutput(lines: TStrings);
begin
  Self.lines.free;
  Self.lines := lines;
end;

procedure TLogger.SaveToFile(filePath, fileName: String);
begin
  if not DirectoryExists(filePath) then ForceDirectories(filePath);
  if lines <> nil then lines.SaveToFile(filePath + fileName + '.txt');
end;

constructor TLogger.Create;
begin
  Self.lines := TStringList.Create;
end;

destructor TLogger.Destroy;
begin
  //lines.Free;
end;

initialization

  log := TLogger.Create;

finalization

  log.Free;

end.
