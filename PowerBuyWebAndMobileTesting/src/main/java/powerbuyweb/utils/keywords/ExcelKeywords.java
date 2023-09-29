package powerbuyweb.utils.keywords;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException;
import org.slf4j.Logger;
import powerbuyweb.utils.helper.LogHelper;

public class ExcelKeywords {
  private static Logger logger = LogHelper.getLogger();

  public static Workbook getWorkbook(String filePath) {
    File xlFile = new File(filePath);
    try {
      FileInputStream fis = new FileInputStream(xlFile);
      return Files.getFileExtension(filePath).equalsIgnoreCase("xlsx")
          ? (Workbook) new XSSFWorkbook(fis) : (Workbook) new HSSFWorkbook(fis);
    } catch (IOException e) {
      logger.error(MessageFormat.format("Cannot get workbook in ''{0}''. Root cause: {1}", filePath,
          e.getMessage()));
    }
    return null;
  }

  public static Sheet getExcelSheetByName(String filePath, String sheetName) {
    Workbook workbook = getWorkbook(filePath);
    if (workbook != null) {
      return workbook.getSheet(sheetName);
    }
    return null;
  }

  public static Cell getCellByIndex(Sheet sheet, int rowIndex, int colIndex) {
    Row row = sheet.getRow(rowIndex);
    return row.getCell(colIndex);
  }

  public static Object getCellValue(Cell cell) {
    if (cell != null) {
      switch (cell.getCellType()) {
        case NUMERIC:
          try {
            if (DateUtil.isCellDateFormatted(cell)) {
              DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
              return sdf.format(cell.getDateCellValue());
            }
            if ((String.valueOf(cell.getNumericCellValue()).split("\\.")).length > 0) {
              if (String.valueOf(cell.getNumericCellValue()).split("\\.")[1].equals("0")) {
                return Integer.parseInt(
                    String.valueOf(cell.getNumericCellValue()).replace(".0", ""));
              }
              return cell.getNumericCellValue();
            }
          } catch (XmlValueDisconnectedException ignored) {
            if ((String.valueOf(cell.getNumericCellValue()).split("\\.")).length > 0) {
              if (String.valueOf(cell.getNumericCellValue()).split("\\.")[1].equals("0")) {
                return Integer.parseInt(
                    String.valueOf(cell.getNumericCellValue()).replace(".0", ""));
              }
              return cell.getNumericCellValue();
            }
          }
        case STRING:
          return cell.getStringCellValue();
        case BLANK:
          return "";
        case BOOLEAN:
          return cell.getBooleanCellValue();
        case FORMULA:
          switch (cell.getCachedFormulaResultType()) {
            case NUMERIC:
              if ((String.valueOf(cell.getNumericCellValue()).split("\\.")).length > 0) {
                if (String.valueOf(cell.getNumericCellValue()).split("\\.")[1].equals("0")) {
                  return Integer.parseInt(
                      String.valueOf(cell.getNumericCellValue()).replace(".0", ""));
                }
                return cell.getNumericCellValue();
              }
            case STRING:
              return cell.getStringCellValue();
          }
          break;
      }
    }
    return "";
  }

  public static Object getCellValue(XSSFCell cell) {
    if (cell != null) {
      switch (cell.getCellType()) {
        case NUMERIC:
          try {
            if (DateUtil.isCellDateFormatted(cell)) {
              DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
              return sdf.format(cell.getDateCellValue());
            }
            if ((String.valueOf(cell.getNumericCellValue()).split("\\.")).length > 0) {
              if (String.valueOf(cell.getNumericCellValue()).split("\\.")[1].equals("0")) {
                return Integer.parseInt(
                    String.valueOf(cell.getNumericCellValue()).replace(".0", ""));
              }
              return cell.getNumericCellValue();
            }
          } catch (XmlValueDisconnectedException ignored) {
            if ((String.valueOf(cell.getNumericCellValue()).split("\\.")).length > 0) {
              if (String.valueOf(cell.getNumericCellValue()).split("\\.")[1].equals("0")) {
                return Integer.parseInt(
                    String.valueOf(cell.getNumericCellValue()).replace(".0", ""));
              }
              return cell.getNumericCellValue();
            }
          }
        case STRING:
          return cell.getStringCellValue();
        case BLANK:
          return "";
        case BOOLEAN:
          return cell.getBooleanCellValue();
        case FORMULA:
          switch (cell.getCachedFormulaResultType()) {
            case NUMERIC:
              if ((String.valueOf(cell.getNumericCellValue()).split("\\.")).length > 0) {
                if (String.valueOf(cell.getNumericCellValue()).split("\\.")[1].equals("0")) {
                  return Integer.parseInt(
                      String.valueOf(cell.getNumericCellValue()).replace(".0", ""));
                }
                return cell.getNumericCellValue();
              }
            case STRING:
              return cell.getStringCellValue();
          }
          break;
      }
    }
    return "";
  }

  public static Object getCellValueByIndex(Sheet sheet, int rowIndex, int colIndex) {
    Cell cell = getCellByIndex(sheet, rowIndex, colIndex);
    if(cell != null) {
      return getCellValue(cell);
    }
    return "";
  }

  public static Object getCellValueByIndex(XSSFSheet sheet, int rowIndex, int colIndex) {
    Cell cell = getCellByIndex(sheet, rowIndex, colIndex);
    if(cell != null) {
      return getCellValue(cell);
    }
    return "";
  }

  public static Cell getFirstCellByValue(Sheet sheet, String cellValue) {
    Cell cell = null;
    Iterator<Row> rowIterator = sheet.rowIterator();
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();
      while (cellIterator.hasNext()) {
        cell = cellIterator.next();
        if(cell.toString().equals(cellValue)) {
          return cell;
        }
      }
    }
    return null;
  }

  public static Cell getFirstCellByValue(XSSFSheet sheet, String cellValue) {
    Cell cell = null;
    Iterator<Row> rowIterator = sheet.rowIterator();
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();
      while (cellIterator.hasNext()) {
        cell = cellIterator.next();
        if(cell.toString().equals(cellValue)) {
          return cell;
        }
      }
    }
    return null;
  }

  public static Cell getLastCellByValue(Sheet sheet, String cellValue) {
    Cell cell = null;
    Iterator<Row> rowIterator = sheet.rowIterator();
    int lastRow = 0;
    int lastCell = 0;
    int count = 0;
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();
      while (cellIterator.hasNext()) {
        cell = cellIterator.next();
        if(cell.toString().equals(cellValue)) {
          count++;
          lastRow = cell.getRowIndex();
          lastCell = cell.getColumnIndex();
        }
      }
    }
    if(count > 0) {
      return sheet.getRow(lastRow).getCell(lastCell);
    }
    return null;
  }


  public static Cell getLastCellByValue(XSSFSheet sheet, String cellValue) {
    Cell cell = null;
    Iterator<Row> rowIterator = sheet.rowIterator();
    int lastRow = 0;
    int lastCell = 0;
    int count = 0;
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();
      while (cellIterator.hasNext()) {
        cell = cellIterator.next();
        if(cell.toString().equals(cellValue)) {
          count++;
          lastRow = cell.getRowIndex();
          lastCell = cell.getColumnIndex();
        }
      }
    }
    if(count > 0) {
      return sheet.getRow(lastRow).getCell(lastCell);
    }
    return null;
  }

  public static void saveWorkbook(String filePath, Workbook workbook) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(filePath);
      workbook.write(fileOutputStream);
      fileOutputStream.flush();
      fileOutputStream.close();
    } catch (IOException e) {
      logger.error(MessageFormat.format("Cannot save workbook in ''{0}''. Root cause: {1}", filePath, e.getMessage()));
    }

  }
}
