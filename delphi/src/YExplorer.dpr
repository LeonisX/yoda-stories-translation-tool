program YExplorer;

uses
  Forms,
  MainUnit in 'MainUnit.pas' {MainForm},
  BMPUnit in 'BMPUnit.pas',
  DataStructureUnit in 'DataStructureUnit.pas',
  CRCUnit in 'CRCUnit.pas',
  LoggerUnit in 'LoggerUnit.pas',
  MPHexEditor in 'MPHexEditor.pas',
  MPHexEditorEx in 'MPHexEditorEx.pas';

{$R *.res}

begin
  Application.Initialize;
  Application.Title := 'Yoda Explorer';
  Application.CreateForm(TMainForm, MainForm);
  Application.Run;
end.
