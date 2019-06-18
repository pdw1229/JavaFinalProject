package JavaFinalHw;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    
    public ArrayList<String> getData(InputStream is) {
    ArrayList<String> values = new ArrayList<String>();
    
    try (InputStream inp = is) {
 
    Workbook wb = WorkbookFactory.create(inp);
    Sheet sheet = wb.getSheetAt(0);

    Iterator<Row> iterator = sheet.iterator();
    
    while(iterator.hasNext()) {
    	String value = "";
    	String resultValue = "";
    	
    	Row r = iterator.next();
    	
    	Iterator<Cell> cellIterator = r.iterator();
    	
    	while(cellIterator.hasNext()) {
    		Cell c = cellIterator.next();
    		
    		switch (c.getCellType()){
            case FORMULA:
                value = c.getCellFormula();
                break;
            case NUMERIC:
                value = c.getNumericCellValue()+"";
                break;
            case STRING:
                value = c.getStringCellValue()+"";
                break;
            case BLANK:
                value = "";
                break;
            case ERROR:
                value = c.getErrorCellValue()+"";
                break;
            default:
                value = new String();
                break;
            }

    		resultValue += value + "\t";

    	}
    	values.add(resultValue);

    }
    
    
    
    } catch (FileNotFoundException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }
    
    return values;
    }

}