object MainForm: TMainForm
  Left = 280
  Top = 109
  Width = 1253
  Height = 903
  Caption = 'Yoda Stories Explorer'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu1
  OldCreateOrder = False
  OnCreate = FormCreate
  OnDestroy = FormDestroy
  PixelsPerInch = 96
  TextHeight = 13
  object Splitter1: TSplitter
    Left = 0
    Top = 518
    Width = 1245
    Height = 12
    Cursor = crVSplit
    Align = alBottom
    Beveled = True
  end
  object OpenDTAButton: TButton
    Left = 8
    Top = 8
    Width = 75
    Height = 25
    Caption = 'Open DTA'
    TabOrder = 0
    OnClick = OpenDTAButtonClick
  end
  object PageControl: TPageControl
    Left = 0
    Top = 0
    Width = 1245
    Height = 518
    ActivePage = TabSheet5
    Align = alClient
    MultiLine = True
    TabIndex = 4
    TabOrder = 1
    Visible = False
    object TabSheet1: TTabSheet
      Caption = 'Common information'
      object LabelCRC32: TLabel
        Left = 8
        Top = 24
        Width = 40
        Height = 13
        Caption = 'CRC-32:'
      end
      object LabelName: TLabel
        Left = 8
        Top = 40
        Width = 31
        Height = 13
        Caption = 'Name:'
      end
      object CRC32Label: TLabel
        Left = 88
        Top = 24
        Width = 3
        Height = 13
      end
      object NameLabel: TLabel
        Left = 88
        Top = 40
        Width = 3
        Height = 13
      end
      object LabelSize: TLabel
        Left = 8
        Top = 8
        Width = 23
        Height = 13
        Caption = 'Size:'
      end
      object SizeLabel: TLabel
        Left = 88
        Top = 8
        Width = 3
        Height = 13
      end
      object VersionLabel: TLabel
        Left = 88
        Top = 56
        Width = 3
        Height = 13
      end
      object LabelVersion: TLabel
        Left = 8
        Top = 56
        Width = 75
        Height = 13
        Caption = 'Internal version:'
      end
      object SectionsStringGrid: TStringGrid
        Left = 8
        Top = 80
        Width = 353
        Height = 249
        DefaultRowHeight = 18
        RowCount = 2
        TabOrder = 0
        OnSelectCell = SectionsStringGridSelectCell
      end
      object Button2: TButton
        Left = 8
        Top = 336
        Width = 113
        Height = 25
        Caption = 'Dump all sections'
        TabOrder = 1
        OnClick = Button2Click
      end
    end
    object TabSheet2: TTabSheet
      Caption = 'Title screen'
      ImageIndex = 1
      object TitleImage: TImage
        Left = 256
        Top = 8
        Width = 288
        Height = 288
      end
      object SaveSTUPButton: TButton
        Left = 6
        Top = 8
        Width = 83
        Height = 25
        Caption = 'Save to BMP'
        TabOrder = 0
        OnClick = SaveSTUPButtonClick
      end
      object Button7: TButton
        Left = 8
        Top = 40
        Width = 81
        Height = 25
        Caption = 'Load from BMP'
        TabOrder = 1
        OnClick = Button7Click
      end
    end
    object TabSheet3: TTabSheet
      Caption = 'Sounds'
      ImageIndex = 2
      object LabelSounds: TLabel
        Left = 8
        Top = 8
        Width = 69
        Height = 13
        Caption = 'Sounds count:'
      end
      object SoundsLabel: TLabel
        Left = 88
        Top = 8
        Width = 3
        Height = 13
      end
      object ListSNDSButton: TButton
        Left = 6
        Top = 32
        Width = 83
        Height = 25
        Caption = 'Save list to file'
        TabOrder = 0
        OnClick = ListSNDSButtonClick
      end
    end
    object TabSheet4: TTabSheet
      Caption = 'Tiles, sprites'
      ImageIndex = 3
      object TilesLabel: TLabel
        Left = 296
        Top = 4
        Width = 3
        Height = 13
      end
      object LabelTiles: TLabel
        Left = 224
        Top = 4
        Width = 55
        Height = 13
        Caption = 'Tiles count:'
      end
      object TilesProgressLabel: TLabel
        Left = 8
        Top = 136
        Width = 193
        Height = 13
        Alignment = taCenter
        AutoSize = False
      end
      object TileImage: TImage
        Left = 176
        Top = 8
        Width = 32
        Height = 32
      end
      object Label1: TLabel
        Left = 816
        Top = 4
        Width = 75
        Height = 13
        Caption = 'Clipboard image'
      end
      object Label2: TLabel
        Left = 8
        Top = 216
        Width = 71
        Height = 13
        Caption = 'Tiles in a row:  '
      end
      object TilesProgressBar: TProgressBar
        Left = 8
        Top = 112
        Width = 193
        Height = 17
        Min = 0
        Max = 100
        Smooth = True
        Step = 1
        TabOrder = 4
      end
      object SaveTilesButton: TButton
        Left = 6
        Top = 8
        Width = 163
        Height = 25
        Caption = 'Save tiles to files (separate)'
        TabOrder = 0
        OnClick = SaveTilesButtonClick
      end
      object DecimalCheckBox: TCheckBox
        Left = 16
        Top = 40
        Width = 145
        Height = 17
        Caption = 'Decimal filenames'
        Checked = True
        State = cbChecked
        TabOrder = 1
      end
      object HexCheckBox: TCheckBox
        Left = 16
        Top = 64
        Width = 145
        Height = 17
        Caption = 'HEX filenames'
        Checked = True
        State = cbChecked
        TabOrder = 2
      end
      object AttrCheckBox: TCheckBox
        Left = 16
        Top = 88
        Width = 153
        Height = 17
        Caption = 'Group by attributes'
        Checked = True
        State = cbChecked
        TabOrder = 3
      end
      object TilesDrawGrid: TDrawGrid
        Left = 224
        Top = 24
        Width = 585
        Height = 385
        Color = clBtnFace
        ColCount = 16
        DefaultColWidth = 32
        DefaultRowHeight = 32
        FixedCols = 0
        FixedRows = 0
        Options = [goVertLine, goHorzLine, goDrawFocusSelected]
        TabOrder = 5
        OnDragDrop = TilesDrawGridDragDrop
        OnDragOver = TilesDrawGridDragOver
        OnDrawCell = TilesDrawGridDrawCell
        OnKeyDown = TilesDrawGridKeyUp
        OnKeyUp = TilesDrawGridKeyUp
        OnMouseDown = TilesDrawGridMouseDown
        OnMouseUp = TilesDrawGridMouseUp
        OnMouseWheelDown = TilesDrawGridMouseWheelDown
        OnMouseWheelUp = TilesDrawGridMouseWheelUp
        OnSelectCell = TilesDrawGridSelectCell
      end
      object Panel4: TPanel
        Left = 814
        Top = 24
        Width = 288
        Height = 288
        BevelOuter = bvLowered
        TabOrder = 6
        object ClipboardImage: TImage
          Left = 0
          Top = 0
          Width = 288
          Height = 288
          OnDragDrop = ClipboardImageDragDrop
          OnDragOver = ClipboardImageDragOver
          OnMouseDown = ClipboardImageMouseDown
        end
      end
      object Button3: TButton
        Left = 864
        Top = 320
        Width = 57
        Height = 25
        Caption = 'Save'
        TabOrder = 7
        OnClick = Button3Click
      end
      object Button4: TButton
        Left = 928
        Top = 320
        Width = 57
        Height = 25
        Caption = 'Load'
        TabOrder = 8
        OnClick = Button4Click
      end
      object Button5: TButton
        Left = 992
        Top = 320
        Width = 57
        Height = 25
        Caption = 'Clear'
        TabOrder = 9
        OnClick = Button5Click
      end
      object Button8: TButton
        Left = 6
        Top = 184
        Width = 163
        Height = 25
        Caption = 'Save tiles to one file'
        TabOrder = 10
        OnClick = Button8Click
      end
      object Edit1: TEdit
        Left = 80
        Top = 213
        Width = 41
        Height = 21
        TabOrder = 11
        Text = '16'
      end
    end
    object TabSheet5: TTabSheet
      Caption = 'Zones (maps)'
      ImageIndex = 4
      object PageControl1: TPageControl
        Left = 0
        Top = 0
        Width = 1237
        Height = 490
        ActivePage = TabSheet11
        Align = alClient
        MultiLine = True
        Style = tsFlatButtons
        TabIndex = 0
        TabOrder = 0
        object TabSheet11: TTabSheet
          Caption = 'General information'
          object MapProgressLabel: TLabel
            Left = 16
            Top = 240
            Width = 193
            Height = 13
            Alignment = taCenter
            AutoSize = False
          end
          object LabelMaps: TLabel
            Left = 8
            Top = 8
            Width = 59
            Height = 13
            Caption = 'Maps count:'
          end
          object MapsLabel: TLabel
            Left = 88
            Top = 8
            Width = 3
            Height = 13
          end
          object MapImage: TImage
            Left = 320
            Top = 7
            Width = 288
            Height = 288
            OnDragDrop = MapImageDragDrop
            OnDragOver = MapImageDragOver
            OnMouseUp = MapImageMouseUp
          end
          object MapProgressBar: TProgressBar
            Left = 16
            Top = 216
            Width = 193
            Height = 17
            Min = 0
            Max = 100
            Smooth = True
            Step = 1
            TabOrder = 0
          end
          object CheckBox1: TCheckBox
            Left = 16
            Top = 184
            Width = 145
            Height = 17
            Caption = 'Save unused tiles'
            TabOrder = 1
          end
          object CheckBox2: TCheckBox
            Left = 16
            Top = 160
            Width = 145
            Height = 17
            Caption = 'Dump text'
            Checked = True
            State = cbChecked
            TabOrder = 2
          end
          object ActionsCheckBox: TCheckBox
            Left = 16
            Top = 136
            Width = 145
            Height = 17
            Caption = 'Dump actions'
            Checked = True
            State = cbChecked
            TabOrder = 3
          end
          object MapPlanetSaveCheckBox: TCheckBox
            Left = 16
            Top = 112
            Width = 153
            Height = 17
            Caption = 'Group by planet type'
            TabOrder = 4
          end
          object MapFlagSaveCheckBox: TCheckBox
            Left = 16
            Top = 88
            Width = 145
            Height = 17
            Caption = 'Group by flags'
            TabOrder = 5
          end
          object MapSaveCheckBox: TCheckBox
            Left = 16
            Top = 64
            Width = 145
            Height = 17
            Caption = 'Normal save'
            Checked = True
            State = cbChecked
            TabOrder = 6
          end
          object SaveMapsButton: TButton
            Left = 6
            Top = 32
            Width = 99
            Height = 25
            Caption = 'Save maps to files'
            TabOrder = 7
            OnClick = SaveMapsButtonClick
          end
          object MapsListStringGrid: TStringGrid
            Left = 224
            Top = 7
            Width = 89
            Height = 289
            ColCount = 1
            DefaultRowHeight = 18
            FixedCols = 0
            FixedRows = 0
            TabOrder = 8
            OnSelectCell = MapsListStringGridSelectCell
          end
        end
        object TabSheet12: TTabSheet
          Caption = 'HEX offsets'
          ImageIndex = 1
          object MapsStringGrid: TStringGrid
            Left = 0
            Top = 0
            Width = 1229
            Height = 459
            Align = alClient
            ColCount = 18
            DefaultColWidth = 58
            DefaultRowHeight = 18
            RowCount = 2
            TabOrder = 0
            OnSelectCell = MapsStringGridSelectCell
          end
        end
        object TabSheet18: TTabSheet
          Caption = 'Map editor'
          ImageIndex = 3
          OnHide = TabSheet18Hide
          OnShow = TabSheet18Show
          object RadioGroup1: TRadioGroup
            Left = 224
            Top = 0
            Width = 273
            Height = 33
            Caption = ' Map planes '
            Columns = 3
            ItemIndex = 1
            Items.Strings = (
              'Bottom'
              'Middle'
              'Top')
            TabOrder = 0
            OnClick = RadioGroup1Click
          end
        end
        object TabSheet13: TTabSheet
          Caption = 'Text translation'
          ImageIndex = 2
          object Panel1: TPanel
            Left = 0
            Top = 0
            Width = 1229
            Height = 41
            Align = alTop
            TabOrder = 0
            object Label3: TLabel
              Left = 558
              Top = 23
              Width = 153
              Height = 13
              Alignment = taCenter
              AutoSize = False
            end
            object Button1: TButton
              Left = 8
              Top = 8
              Width = 75
              Height = 25
              Caption = 'Get original'
              TabOrder = 0
              OnClick = Button1Click
            end
            object Button12: TButton
              Left = 88
              Top = 8
              Width = 75
              Height = 25
              Caption = 'Get translated'
              TabOrder = 1
              OnClick = Button12Click
            end
            object Button13: TButton
              Left = 192
              Top = 8
              Width = 129
              Height = 25
              Caption = 'Check phrases positions'
              TabOrder = 2
              OnClick = Button13Click
            end
            object Button14: TButton
              Left = 368
              Top = 8
              Width = 75
              Height = 25
              Caption = 'Replace text'
              TabOrder = 3
              OnClick = Button14Click
            end
            object CheckBox3: TCheckBox
              Left = 448
              Top = 12
              Width = 97
              Height = 17
              Caption = 'Trim spaces'
              TabOrder = 4
            end
            object ProgressBar1: TProgressBar
              Left = 560
              Top = 5
              Width = 150
              Height = 17
              Min = 0
              Max = 100
              Smooth = True
              TabOrder = 5
            end
          end
          object Panel2: TPanel
            Left = 0
            Top = 41
            Width = 1229
            Height = 418
            Align = alClient
            TabOrder = 1
            object StringGrid1: TStringGrid
              Left = 1
              Top = 1
              Width = 1227
              Height = 416
              Align = alClient
              ColCount = 3
              Ctl3D = False
              DefaultRowHeight = 18
              FixedCols = 0
              ParentCtl3D = False
              TabOrder = 0
              OnDrawCell = StringGrid1DrawCell
            end
          end
        end
      end
    end
    object TabSheet6: TTabSheet
      Caption = 'Puzzles'
      ImageIndex = 5
      object PageControl2: TPageControl
        Left = 0
        Top = 0
        Width = 1237
        Height = 490
        ActivePage = TabSheet14
        Align = alClient
        MultiLine = True
        Style = tsFlatButtons
        TabIndex = 0
        TabOrder = 0
        object TabSheet14: TTabSheet
          Caption = 'General information'
          object Label6: TLabel
            Left = 88
            Top = 8
            Width = 3
            Height = 13
          end
          object LabelPuzzles: TLabel
            Left = 8
            Top = 8
            Width = 69
            Height = 13
            Caption = 'Puzzles count:'
          end
          object PuzzlesLabel: TLabel
            Left = 96
            Top = 8
            Width = 3
            Height = 13
          end
          object Button9: TButton
            Left = 6
            Top = 32
            Width = 107
            Height = 25
            Caption = 'Save puzzles to files'
            TabOrder = 0
            OnClick = Button9Click
          end
        end
        object TabSheet16: TTabSheet
          Caption = 'Text translation'
          ImageIndex = 2
          object Panel3: TPanel
            Left = 0
            Top = 0
            Width = 1229
            Height = 41
            Align = alTop
            TabOrder = 0
            object Label7: TLabel
              Left = 558
              Top = 23
              Width = 153
              Height = 13
              Alignment = taCenter
              AutoSize = False
            end
            object Button16: TButton
              Left = 8
              Top = 8
              Width = 75
              Height = 25
              Caption = 'Get original'
              TabOrder = 0
              OnClick = Button16Click
            end
            object Button17: TButton
              Left = 88
              Top = 8
              Width = 75
              Height = 25
              Caption = 'Get translated'
              TabOrder = 1
              OnClick = Button17Click
            end
            object Button18: TButton
              Left = 192
              Top = 8
              Width = 129
              Height = 25
              Caption = 'Check phrases positions'
              TabOrder = 2
              OnClick = Button18Click
            end
            object Button19: TButton
              Left = 368
              Top = 8
              Width = 75
              Height = 25
              Caption = 'Replace text'
              TabOrder = 3
              OnClick = Button19Click
            end
            object CheckBox10: TCheckBox
              Left = 448
              Top = 12
              Width = 97
              Height = 17
              Caption = 'Trim spaces'
              TabOrder = 4
            end
            object ProgressBar3: TProgressBar
              Left = 560
              Top = 5
              Width = 150
              Height = 17
              Min = 0
              Max = 100
              Smooth = True
              TabOrder = 5
            end
          end
          object Panel5: TPanel
            Left = 0
            Top = 41
            Width = 1229
            Height = 418
            Align = alClient
            TabOrder = 1
            object StringGrid4: TStringGrid
              Left = 1
              Top = 1
              Width = 1227
              Height = 416
              Align = alClient
              ColCount = 3
              Ctl3D = False
              DefaultRowHeight = 18
              FixedCols = 0
              ParentCtl3D = False
              TabOrder = 0
              OnDrawCell = StringGrid4DrawCell
            end
          end
        end
      end
    end
    object TabSheet7: TTabSheet
      Caption = 'Characters'
      ImageIndex = 6
      object LabelChars: TLabel
        Left = 8
        Top = 8
        Width = 84
        Height = 13
        Caption = 'Characters count:'
      end
      object CharsLabel: TLabel
        Left = 98
        Top = 8
        Width = 3
        Height = 13
      end
      object Label4: TLabel
        Left = 8
        Top = 72
        Width = 412
        Height = 13
        Caption = 
          'WARNING!!! Characters don'#39't need to translate. Don'#39't waste your ' +
          'time :)'
        Color = clBtnFace
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clMaroon
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = [fsBold]
        ParentColor = False
        ParentFont = False
      end
      object Button10: TButton
        Left = 6
        Top = 32
        Width = 123
        Height = 25
        Caption = 'Save characters to files'
        TabOrder = 0
        OnClick = Button10Click
      end
    end
    object TabSheet10: TTabSheet
      Caption = 'Names'
      ImageIndex = 9
      object PageControl3: TPageControl
        Left = 0
        Top = 0
        Width = 1237
        Height = 490
        ActivePage = TabSheet15
        Align = alClient
        MultiLine = True
        Style = tsFlatButtons
        TabIndex = 0
        TabOrder = 0
        object TabSheet15: TTabSheet
          Caption = 'General information'
          object NamesLabel: TLabel
            Left = 88
            Top = 8
            Width = 3
            Height = 13
          end
          object LabelNames: TLabel
            Left = 8
            Top = 8
            Width = 66
            Height = 13
            Caption = 'Names count:'
          end
          object Button11: TButton
            Left = 6
            Top = 32
            Width = 123
            Height = 25
            Caption = 'Save names to files'
            TabOrder = 0
            OnClick = Button11Click
          end
        end
        object TabSheet17: TTabSheet
          Caption = 'Text translation'
          ImageIndex = 2
          object Panel6: TPanel
            Left = 0
            Top = 0
            Width = 1229
            Height = 41
            Align = alTop
            TabOrder = 0
            object Label9: TLabel
              Left = 558
              Top = 23
              Width = 153
              Height = 13
              Alignment = taCenter
              AutoSize = False
            end
            object Button20: TButton
              Left = 8
              Top = 8
              Width = 75
              Height = 25
              Caption = 'Get original'
              TabOrder = 0
              OnClick = Button20Click
            end
            object Button21: TButton
              Left = 88
              Top = 8
              Width = 75
              Height = 25
              Caption = 'Get translated'
              TabOrder = 1
              OnClick = Button21Click
            end
            object Button22: TButton
              Left = 192
              Top = 8
              Width = 129
              Height = 25
              Caption = 'Check phrases positions'
              TabOrder = 2
              OnClick = Button22Click
            end
            object Button23: TButton
              Left = 368
              Top = 8
              Width = 75
              Height = 25
              Caption = 'Replace text'
              TabOrder = 3
              OnClick = Button23Click
            end
            object ProgressBar2: TProgressBar
              Left = 560
              Top = 5
              Width = 150
              Height = 17
              Min = 0
              Max = 100
              Smooth = True
              TabOrder = 4
            end
            object CheckBox4: TCheckBox
              Left = 448
              Top = 12
              Width = 97
              Height = 17
              Caption = 'Trim spaces'
              TabOrder = 5
            end
          end
          object Panel7: TPanel
            Left = 0
            Top = 41
            Width = 1229
            Height = 418
            Align = alClient
            TabOrder = 1
            object StringGrid2: TStringGrid
              Left = 1
              Top = 1
              Width = 1227
              Height = 416
              Align = alClient
              ColCount = 3
              Ctl3D = False
              DefaultRowHeight = 18
              FixedCols = 0
              ParentCtl3D = False
              TabOrder = 0
            end
          end
        end
      end
    end
  end
  object BottomPageControl: TPageControl
    Left = 0
    Top = 530
    Width = 1245
    Height = 300
    ActivePage = TabSheet9
    Align = alBottom
    TabIndex = 1
    TabOrder = 2
    object TabSheet8: TTabSheet
      Caption = 'Log'
      object LogMemo: TMemo
        Left = 0
        Top = 0
        Width = 1103
        Height = 262
        Align = alClient
        ScrollBars = ssVertical
        TabOrder = 0
      end
    end
    object TabSheet9: TTabSheet
      Caption = 'HEX viewer'
      ImageIndex = 1
      object Hex: TMPHexEditorEx
        Left = 0
        Top = 0
        Width = 1237
        Height = 272
        Cursor = crIBeam
        BackupExtension = '.bak'
        PrintOptions.MarginLeft = 20
        PrintOptions.MarginTop = 15
        PrintOptions.MarginRight = 25
        PrintOptions.MarginBottom = 25
        PrintOptions.Flags = [pfSelectionBold, pfMonochrome]
        PrintFont.Charset = DEFAULT_CHARSET
        PrintFont.Color = clWindowText
        PrintFont.Height = -15
        PrintFont.Name = 'Courier New'
        PrintFont.Style = []
        Align = alClient
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -15
        Font.Name = 'Courier New'
        Font.Style = []
        OnKeyUp = HEXKeyUp
        OnMouseUp = HEXMouseUp
        ParentFont = False
        ParentShowHint = False
        ShowHint = False
        TabOrder = 0
        BytesPerRow = 32
        Translation = tkAsIs
        OffsetFormat = '-!10:0x|'
        Colors.Background = clWindow
        Colors.ChangedBackground = 11075583
        Colors.ChangedText = clMaroon
        Colors.CursorFrame = clNavy
        Colors.Offset = clBlack
        Colors.OddColumn = clBlue
        Colors.EvenColumn = clNavy
        Colors.CurrentOffsetBackground = clBtnShadow
        Colors.OffsetBackground = clBtnFace
        Colors.CurrentOffset = clBtnHighlight
        Colors.Grid = clBtnFace
        Colors.NonFocusCursorFrame = clAqua
        Colors.ActiveFieldBackground = clWindow
        FocusFrame = True
        DrawGridLines = False
        Version = 'september 30, 2007; '#169' markus stephany, vcl[at]mirkes[dot]de'
        ShowRuler = True
        ShowPositionIfNotFocused = True
        OnSelectionChanged = HEXSelectionChanged
      end
    end
  end
  object StatusBar: TStatusBar
    Left = 0
    Top = 830
    Width = 1245
    Height = 19
    Panels = <
      item
        Width = 50
      end>
    SimplePanel = False
  end
  object OpenDTADialog: TOpenDialog
    DefaultExt = '*.dta'
    Filter = 'Desktop Adventures|*.dta|All files|*.*'
    Left = 832
    Top = 104
  end
  object SaveClipboardDialog: TSaveDialog
    DefaultExt = '*.bmp'
    FileName = 'unnamed.bmp'
    Filter = 'Bitmaps|*.bmp'
    Left = 872
    Top = 104
  end
  object OpenClipboardDialog: TOpenDialog
    DefaultExt = '*.bmp'
    Filter = 'Bitmaps|*.bmp|All files|*.*'
    Left = 912
    Top = 104
  end
  object SaveDTADialog: TSaveDialog
    DefaultExt = '*.dta'
    Filter = 'Desktop Adventures|*.dta|All files|*.*'
    Left = 832
    Top = 144
  end
  object MainMenu1: TMainMenu
    Left = 872
    Top = 144
    object File1: TMenuItem
      Caption = 'File'
      object Open1: TMenuItem
        Caption = 'Open'
        OnClick = OpenDTAButtonClick
      end
      object Save1: TMenuItem
        Caption = 'Save'
        OnClick = Save1Click
      end
      object Save2: TMenuItem
        Caption = 'Save...'
        OnClick = Save2Click
      end
      object N1: TMenuItem
        Caption = '-'
      end
      object Exit1: TMenuItem
        Caption = 'Exit'
      end
    end
    object Settings1: TMenuItem
      Caption = 'Settings'
      object TransparentColorMenuItem: TMenuItem
        Caption = 'Transparent color'
        GroupIndex = 13
        object FuchsiaMenuItem: TMenuItem
          Tag = 16646398
          AutoCheck = True
          Bitmap.Data = {
            F6000000424DF600000000000000760000002800000010000000100000000100
            0400000000008000000000000000000000001000000000000000000000000000
            8000008000000080800080000000800080008080000080808000C0C0C0000000
            FF0000FF000000FFFF00FF000000FF00FF00FFFF0000FFFFFF00DDDDDDDDDDDD
            DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
            DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
            DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
            DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD}
          Caption = 'Fuchsia'
          Checked = True
          Default = True
          GroupIndex = 13
          ImageIndex = 1
          RadioItem = True
          OnClick = WhiteMenuItemClick
        end
        object BlackMenuItem: TMenuItem
          Tag = 65793
          AutoCheck = True
          Bitmap.Data = {
            F6000000424DF600000000000000760000002800000010000000100000000100
            0400000000008000000000000000000000001000000000000000000000000000
            8000008000000080800080000000800080008080000080808000C0C0C0000000
            FF0000FF000000FFFF00FF000000FF00FF00FFFF0000FFFFFF00000000000000
            0000000000000000000000000000000000000000000000000000000000000000
            0000000000000000000000000000000000000000000000000000000000000000
            0000000000000000000000000000000000000000000000000000000000000000
            0000000000000000000000000000000000000000000000000000}
          Caption = 'Black'
          GroupIndex = 13
          ImageIndex = 2
          RadioItem = True
          OnClick = WhiteMenuItemClick
        end
        object WhiteMenuItem: TMenuItem
          Tag = 16711422
          AutoCheck = True
          Bitmap.Data = {
            F6000000424DF600000000000000760000002800000010000000100000000100
            0400000000008000000000000000000000001000000000000000000000000000
            8000008000000080800080000000800080008080000080808000C0C0C0000000
            FF0000FF000000FFFF00FF000000FF00FF00FFFF0000FFFFFF00888888888888
            88888FFFFFFFFFFFFFF88FFFFFFFFFFFFFF88FFFFFFFFFFFFFF88FFFFFFFFFFF
            FFF88FFFFFFFFFFFFFF88FFFFFFFFFFFFFF88FFFFFFFFFFFFFF88FFFFFFFFFFF
            FFF88FFFFFFFFFFFFFF88FFFFFFFFFFFFFF88FFFFFFFFFFFFFF88FFFFFFFFFFF
            FFF88FFFFFFFFFFFFFF88FFFFFFFFFFFFFF88888888888888888}
          Caption = 'White'
          GroupIndex = 13
          RadioItem = True
          OnClick = WhiteMenuItemClick
        end
      end
    end
    object Operations1: TMenuItem
      Caption = 'Operations'
      object AddTiles: TMenuItem
        Caption = 'Add tile(s)'
        OnClick = Adddtiles1Click
      end
    end
    object Help1: TMenuItem
      Caption = 'Help'
      object Howto1: TMenuItem
        Caption = 'How to...'
      end
      object About1: TMenuItem
        Caption = 'About...'
      end
    end
  end
  object TilesPopupMenu: TPopupMenu
    Left = 872
    Top = 184
    object Setflag1: TMenuItem
      Caption = 'Set flag'
      object Bottomlayer1: TMenuItem
        Tag = 2
        AutoCheck = True
        Caption = 'Bottom layer'
        GroupIndex = 7
        RadioItem = True
        OnClick = Bottomlayer1Click
      end
      object Middlelayer1: TMenuItem
        Tag = 4
        AutoCheck = True
        Caption = 'Middle layer'
        GroupIndex = 7
        RadioItem = True
        OnClick = Bottomlayer1Click
      end
      object Middlelayertransparent1: TMenuItem
        Tag = 5
        AutoCheck = True
        Caption = 'Middle layer (transparent)'
        GroupIndex = 7
        RadioItem = True
        OnClick = Bottomlayer1Click
      end
      object Pushpullblock1: TMenuItem
        Tag = 13
        AutoCheck = True
        Caption = 'Push/pull block'
        GroupIndex = 7
        RadioItem = True
        OnClick = Bottomlayer1Click
      end
      object oplayer1: TMenuItem
        Tag = 16
        AutoCheck = True
        Caption = 'Top layer'
        GroupIndex = 7
        RadioItem = True
        OnClick = Bottomlayer1Click
      end
      object oplayertransparent1: TMenuItem
        Tag = 17
        AutoCheck = True
        Caption = 'Top layer (transparent)'
        GroupIndex = 7
        RadioItem = True
        OnClick = Bottomlayer1Click
      end
      object Weapons1: TMenuItem
        Caption = 'Weapons'
        GroupIndex = 7
        object LightBlaster1: TMenuItem
          Tag = 65601
          AutoCheck = True
          Caption = 'Light Blaster'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object HeavyBlasterThermalDetonator1: TMenuItem
          Tag = 131137
          AutoCheck = True
          Caption = 'Heavy Blaster, Thermal Detonator'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Lightsaber1: TMenuItem
          Tag = 262209
          AutoCheck = True
          Caption = 'Lightsaber'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object heForce1: TMenuItem
          Tag = 524353
          AutoCheck = True
          Caption = 'The Force'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
      end
      object Items1: TMenuItem
        Caption = 'Items'
        GroupIndex = 7
        object Keycard1: TMenuItem
          Tag = 65665
          AutoCheck = True
          Caption = 'Keycard'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Itemforuse1: TMenuItem
          Tag = 131201
          AutoCheck = True
          Caption = 'Item (for use)'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Itempartof1: TMenuItem
          Tag = 262273
          AutoCheck = True
          Caption = 'Item (part of)'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Itemtotrade1: TMenuItem
          Tag = 524417
          AutoCheck = True
          Caption = 'Item (to trade)'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Locator1: TMenuItem
          Tag = 1048705
          AutoCheck = True
          Caption = 'Locator'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Healthpack1: TMenuItem
          Tag = 4194433
          AutoCheck = True
          Caption = 'Health pack'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
      end
      object Characters1: TMenuItem
        Caption = 'Characters'
        GroupIndex = 7
        object Player1: TMenuItem
          Tag = 65793
          AutoCheck = True
          Caption = 'Player'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Enemy1: TMenuItem
          Tag = 131329
          AutoCheck = True
          Caption = 'Enemy'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Friendly1: TMenuItem
          Tag = 262401
          AutoCheck = True
          Caption = 'Friendly'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
      end
      object Doorpassageladder1: TMenuItem
        Tag = 65538
        AutoCheck = True
        Caption = 'Door, passage, ladder'
        GroupIndex = 7
        RadioItem = True
        OnClick = Bottomlayer1Click
      end
      object Minimap1: TMenuItem
        Caption = 'Mini map'
        GroupIndex = 7
        object Home1: TMenuItem
          Tag = 131104
          AutoCheck = True
          Caption = 'Home'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Puzzle1: TMenuItem
          Tag = 262176
          AutoCheck = True
          Caption = 'Puzzle'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Puzzlesolved1: TMenuItem
          Tag = 524320
          AutoCheck = True
          Caption = 'Puzzle (solved)'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Gateway1: TMenuItem
          Tag = 1048608
          AutoCheck = True
          Caption = 'Gateway'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Gatewaysolved1: TMenuItem
          Tag = 2097184
          AutoCheck = True
          Caption = 'Gateway (solved)'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Upwalllocked1: TMenuItem
          Tag = 4194336
          AutoCheck = True
          Caption = 'Up wall (locked)'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Downwalllocked1: TMenuItem
          Tag = 8388640
          AutoCheck = True
          Caption = 'Down wall (locked)'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Leftwalllocked1: TMenuItem
          Tag = 16777248
          AutoCheck = True
          Caption = 'Left wall (locked)'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Rightwalllocked1: TMenuItem
          Tag = 33554464
          AutoCheck = True
          Caption = 'Right wall (locked)'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Upwall1: TMenuItem
          Tag = 67108896
          AutoCheck = True
          Caption = 'Up wall'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Downwall1: TMenuItem
          Tag = 134217760
          AutoCheck = True
          Caption = 'Down wall'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Leftwall1: TMenuItem
          Tag = 268435488
          AutoCheck = True
          Caption = 'Left wall'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Rightwall1: TMenuItem
          Tag = 536870944
          AutoCheck = True
          Caption = 'Right wall'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Objective1: TMenuItem
          Tag = 1073741856
          AutoCheck = True
          Caption = 'Objective'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
        object Currentposition1: TMenuItem
          Tag = 2000000000
          AutoCheck = True
          Caption = 'Current position'
          GroupIndex = 7
          RadioItem = True
          OnClick = Bottomlayer1Click
        end
      end
    end
    object Adddtiles1: TMenuItem
      Caption = 'Add tile(s)'
      OnClick = Adddtiles1Click
    end
    object Deletetile1: TMenuItem
      Caption = 'Delete tile'
      OnClick = Deletetile1Click
    end
  end
  object OpenDialog1: TOpenDialog
    Filter = 'All files|*.*'
    Left = 912
    Top = 144
  end
  object MapPopupMenu: TPopupMenu
    Left = 912
    Top = 184
    object Undo1: TMenuItem
      Caption = 'Undo'
      OnClick = Undo1Click
    end
    object Empty1: TMenuItem
      Caption = 'Empty'
      OnClick = Empty1Click
    end
  end
end
