package com.svi.object;

public class SearchInfo {
	private int resultnum;
	private String filename;
    private String sheetname;
    private int cellrow;
    private String cellcol;

	public SearchInfo(int resultnum, String filename, String sheetname, int cellrow, String cellcol) {
		this.resultnum = resultnum;
        this.filename = filename;
        this.sheetname = sheetname;
        this.cellrow = cellrow;
        this.cellcol = cellcol;
    }

	public int getResultNum() {
        return resultnum;
    }
	
	public String getFileName() {
        return filename;
    }

    public String getSheetName() {
        return sheetname;
    }
    
    public int getCellRow() {
        return cellrow;
    }

    public String getCellCol() {
        return cellcol;
    }
    
}
