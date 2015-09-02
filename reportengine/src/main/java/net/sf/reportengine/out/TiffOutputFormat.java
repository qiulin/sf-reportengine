package net.sf.reportengine.out;

public class TiffOutputFormat extends FoOutputFormat {

    public TiffOutputFormat() {
        this("A4");
    }

    public TiffOutputFormat(String pageSize) {
        super(pageSize);
    }
}
