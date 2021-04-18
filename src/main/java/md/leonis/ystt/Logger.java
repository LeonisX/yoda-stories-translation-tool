package md.leonis.ystt;

import java.util.ArrayList;
import java.util.List;

//TODO normal logger (from rom shingler)
public class Logger {

    private List<String> lines = new ArrayList<>();

    public void debug(String line) {
        lines.add(line);
    }

    public void info(String line) {
        lines.add(line);
    }

    public void error(String line) {
        lines.add(line);
    }

    public void newLine() {
        lines.add("");
    }

    public void setOutput(List<String> lines) {
        this.lines = lines;
    }
}
