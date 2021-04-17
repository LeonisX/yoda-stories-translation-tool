package md.leonis.extractor.model;

import org.junit.Test;

import static md.leonis.extractor.model.Palette.camineetPalette;

// TODO complete tests
public class PaletteTest {

    @Test
    public void exportAsDtm() throws Exception {
        camineetPalette.exportAsDtm("dtm.pal");
    }

    @Test
    public void exportAsJasc() throws Exception {
        camineetPalette.exportAsJasc("jasc.pal");
    }

    @Test
    public void exportAsGimp() throws Exception {
        camineetPalette.exportAsGimp("gimp.gpl");
    }

    @Test
    public void exportAsRaw() throws Exception {
        camineetPalette.exportAsRaw("meka.raw");
    }

}