package net.sf.reportengine.out;

public class TiffOutputFormat extends FoOutputFormat {

    public TiffOutputFormat() {
        this(PageSize.A4_PORTRAIT);
    }

    public TiffOutputFormat(PageSize pageSize) {
        super(pageSize);
    }
}
